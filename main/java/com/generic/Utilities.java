package com.generic;

import java.io.File;
import java.util.Scanner;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
import java.awt.event.ActionEvent;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.junit.Assert;

import com.hooks.Hooks;
/**
 * @ScriptName    : Utilities
 * @Description   : This class contains utilities function
 *  
 */
public class Utilities 
{
	
	/**
	 * @Method		: getRequiredDate
	 * @Description	: This method will give require date
	 * @param		: incrfementDateByDays Number by which user want increase date 
	 * @param		: sExpectedDateFormat - User expected date format
	 * 					eg. 9 april 2014 --- dd/MM/yyyy -> 09/04/2015, dd-MM-yyyy -> 09-04-2015
	 * @param		: timeZoneId - Time Zone
	 * @author		:   (SQS) 
	 */
	public String getRequiredDate(String incrementDays, String expectedDateFormat, String timeZoneId)
	{
		try 
		{
			DateFormat dateFormat;
			Calendar calendar = Calendar.getInstance();
			dateFormat = new SimpleDateFormat(expectedDateFormat);
			if(timeZoneId != null && !timeZoneId.equals(""))
				dateFormat.setTimeZone(TimeZone.getTimeZone(timeZoneId));
			if(incrementDays != null && !incrementDays.equals(""))
				calendar.add(Calendar.DAY_OF_MONTH, Integer.parseInt(incrementDays));
			Date date = calendar.getTime();
			String formattedDate = dateFormat.format(date);
			return formattedDate;
		}
		catch (Exception exception) 
		{
			exception.printStackTrace();
			return null;
		}
	}

	/**
	 * @Method		: getRequiredTime
	 * @Description	: This method will give require time
	 * @param		: incrementMin - increment in time by minute 
	 * @param		: sExpectedDateFormat - User expected date format
	 * 					eg. 9 april 2014 --- dd/MM/yyyy -> 09/04/2015, dd-MM-yyyy -> 09-04-2015
	 * @param		: timeZoneId - Time Zone
	 * @author		:   (SQS) 
	 */
	public String getRequiredTime(String incrementMin, String expectedDateFormat, String timeZoneId)
	{
		try 
		{
			DateFormat dateFormat;
			Calendar calendar = Calendar.getInstance();
			dateFormat = new SimpleDateFormat(expectedDateFormat);
			if(timeZoneId != null && ! timeZoneId.equals(""))
				dateFormat.setTimeZone(TimeZone.getTimeZone(timeZoneId));
			if(incrementMin != null && ! incrementMin.equals(""))
				calendar.add(Calendar.MINUTE, Integer.parseInt(incrementMin));
			Date time = calendar.getTime();
			String formattedTime = dateFormat.format(time);
			return formattedTime;
		}
		catch (Exception exception) 
		{
			exception.printStackTrace();
			return null;
		}
	}

	/**
	 * @Method		: getRequiredTime
	 * @Description	: This method will give require time
	 * @param		: incrementMin - increment in time by minute 
	 * @param		: sExpectedDateFormat - User expected date format
	 * 					eg. 9 april 2014 --- dd/MM/yyyy -> 09/04/2015, dd-MM-yyyy -> 09-04-2015
	 * @param		: timeZoneId - Time Zone
	 * @author		:   (SQS) 
	 */
	public String getCurrentTimeZone()
	{
		try 
		{
			Calendar calendar = Calendar.getInstance();
			long milliDiff = calendar.get(Calendar.ZONE_OFFSET);
			// Got local offset, now loop through available timezone id(s).
			String [] ids = TimeZone.getAvailableIDs();
			String timeZone = null;
			for (String id : ids) {
				TimeZone tz = TimeZone.getTimeZone(id);
				if (tz.getRawOffset() == milliDiff) {
					// Found a match.
					timeZone = id;
					break;
				}
			}
			return timeZone;
		}
		catch (Exception exception) 
		{
			exception.printStackTrace();
			return null;
		}
	}

	/**
	 * @Method		: getFormatedDate
	 * @Description	: This method will converted date into excepted date format
	 * @author		: (SQS) 
	 */
	public String getFormatedDate(String date, String originalDateFormat, String expectedDateFormat)
	{
		try 
		{
			DateFormat inputFormatter = new SimpleDateFormat(originalDateFormat);
			Date originalDate = inputFormatter.parse(date);
			DateFormat outputFormatter = new SimpleDateFormat(expectedDateFormat);
			String expectedDate = outputFormatter.format(originalDate);
			return expectedDate;
		}
		catch (Exception exception) 
		{
			exception.printStackTrace();
			return null;
		}
	}

	/**
	 * @Method		: copyFileUsingStream
	 * @Description	: copy files
	 * @param		: Soure file path
	 * @param		: destination file path
	 * @author		:  (SQS) 
	 */
	public void copyFileUsingStream(String sourceFilePath, String destinationFilePath)
	{
		InputStream inputStream = null;
		OutputStream outputStream = null;
		try 
		{
			inputStream = new FileInputStream(new File(sourceFilePath));
			outputStream = new FileOutputStream(new File(destinationFilePath));
			byte[] buffer = new byte[1024];
			int length;
			while ((length = inputStream.read(buffer)) > 0) 
			{
				outputStream.write(buffer, 0, length);
			}
		}
		catch(Exception exception)
		{
			exception.printStackTrace();

		} 
		finally 
		{
			try 
			{
				inputStream.close();
				outputStream.close();
			} 
			catch (IOException iOException) 
			{
				iOException.printStackTrace();
			}
		}
	}

	/**
	 * @Method      : logReporter
	 * @Description	: Reporter method 
	 * @param       : Step - Step description, resultLog - result log pass/fail (true/false), includeMobile - result for mobile(true/false)
	 *  
	 */
	public void logReporter(String step, boolean resultLog)
	{
		String strLog = step;
		this.addAssertTakeScreenShot(step, strLog, "", "", "", resultLog);
	}

	/**
	 * @Method      : logReporter
	 * @Description	: Reporter method 
	 * @param       : Step - Step description, inputValue - Input value, resultLog - result log pass/fail (true/false), includeMobile - result for mobile(true/false)
	 *  
	 */
	public void logReporter(String step, String inputValue, boolean resultLog)
	{
		String strLog = step + "|| Input Value : " + inputValue;
		this.addAssertTakeScreenShot(step, strLog, inputValue, "", "", resultLog);
	} 

	/**
	 * @Method      : logReporter
	 * @Description	: Reporter method 
	 * @param       : Step - Step description, expectedValue - verification point expected value, actualValue - verification point actual value, resultLog - result log pass/fail (true/false), includeMobile - result for mobile(true/false)
	 *  
	 */
	public void logReporter(String step, String expectedValue, String actualValue, boolean resultLog)
	{
		String strLog = step + " || Expected Result : " + expectedValue + " || Actual Result : " + actualValue;
		this.addAssertTakeScreenShot(step, strLog, "", expectedValue, actualValue, resultLog);
	} 


	/**
	 * @Method      : addAssertTakeScreenShot
	 *  
	 */
	public void addAssertTakeScreenShot(String step, String strLog, String inputValue, String expectedValue, String actualValue, boolean resultLog)
	{
		System.out.println("****Step-" + step);
		if(resultLog) {
			//Reporter.log("Step >> " + strLog);
			Assert.assertTrue(true);
	 	} 
		else  {
			//Reporter.log("Step >> " + strLog);
			Assert.assertFalse(false);
		}
	}
	
	/**
	 * @Method : decryptPassword
	 * @Description : This method is used to decrypt password
	 * @param sPassword
	 * @return Decrypted password as a string
	 *  
	 */
	public String decryptPassword(String sPassword){
		 String sKey = "ZYXWVUTSRQPONMLKJIHGFEDCBA1234567890abcdefghijklmnopqrstuvwxyz";
	    try {
	      if (sPassword==null || sKey==null ) return null;          
	      BASE64Decoder objDecoder = new BASE64Decoder();
	      char[] aKeys=sKey.toCharArray();
	      char[] aMesg=new String(objDecoder.decodeBuffer(sPassword)).toCharArray();
	      int iMsgLength=aMesg.length;
	      int iKeyLength=aKeys.length;
	      char[] aNewMsg=new char[iMsgLength];
	      for (int iCounter=0; iCounter<iMsgLength; iCounter++){
	    	  aNewMsg[iCounter]=(char)(aMesg[iCounter]^aKeys[iCounter%iKeyLength]);
	      }
	      aMesg=null; aKeys=null;
	      return new String(aNewMsg);
	    }
	    catch ( Exception e ) {
	      return null;
	        }  
	      
	}
	
	/**
	* 
	 * @Description this method accepts item name as a parameter and returns item id from hashmap
	* @param ItemName
	* @return
	*  
	*/
	public String getItemIdByNameFromHashMap(String ItemName){
		String itemId=null;
		ItemDetails objItemDetails = new ItemDetails();
		if(ItemName.contains("ES"))
		{
			String sItemDtls [] = ItemName.split("_");
			objItemDetails = Hooks.itemMap.get(sItemDtls[0]);
		}
		else
			
			objItemDetails = Hooks.itemMap.get(ItemName);

		itemId=objItemDetails.getItemId();
		
		return itemId;
		
		
	}
	
	/**
	 * @Method : getHashMapItemNameFromItemDetails
	 * @Description : This method splits item details passed by user and based on item name retrieved returns original name(with random number) from hashmap
	 * @param sItemDetails : These are details passed by user
	 * @return String : Actual item name with random number
	 *  
	 */
	public String getHashMapItemNameFromItemDetails(String sItemDetails)
	{
		String sItemName = "";
		String arrItemProperty[] = sItemDetails.split(",");
		ItemDetails objItemDetails;
        
        for (String sItemProperty : arrItemProperty)
        {
        	String[] arrItemPropertyValue = sItemProperty.split(":");
        	if(arrItemPropertyValue[0].contains("Name"))
        	{
        		sItemName = arrItemPropertyValue[1];
        		break;
        	}
        	else
        	{
        		if(arrItemPropertyValue[0].equalsIgnoreCase("Verb"))
        		{
        			if(sItemName.trim() == "")
        				sItemName = arrItemPropertyValue[1];
        		}
        		if(arrItemPropertyValue[0].equalsIgnoreCase("Noun"))
        		{
        			sItemName = sItemName + " " + arrItemPropertyValue[1];
					break;
        		}
        		if (arrItemPropertyValue[0].equalsIgnoreCase("requirement title"))
        		{
        			if(sItemName.trim() == "")
					{
        				sItemName = arrItemPropertyValue[1];
						break;
					}
        		}
        	}
        }
		//MGUPTA_30Aug2017
        if(sItemName.equalsIgnoreCase("Function") || (sItemName.equalsIgnoreCase("FN1")))
		{
			return sItemName;
        }
		else
        { 
			objItemDetails = Hooks.itemMap.get(sItemName);
            return objItemDetails.getItemName();
           // return sItemName;
        }

	}
	
	/**
	 * @Method : getItemNameFromItemDetails
	 * @Description : This method splits item details passed by user and returns Item name
	 * @param sItemDetails : These are details passed by user
	 * @return String : Item name passed by user
	 *  
	 */
	public String getItemNameFromItemDetails(String sItemDetails)
	{
		String sItemName = "";
		String arrItemProperty[] = sItemDetails.split(",");
        
        for (String sItemProperty : arrItemProperty)
        {
        	String[] arrItemPropertyValue = sItemProperty.split(":");
        	if(arrItemPropertyValue[0].contains("Name"))
        	{
        		sItemName = arrItemPropertyValue[1];
        		break;
        	}
        	else
        	{
        		if(arrItemPropertyValue[0].equalsIgnoreCase("Verb"))
        		{
        			if(sItemName.trim() == "")
        				sItemName = arrItemPropertyValue[1];
        		}
        		if(arrItemPropertyValue[0].equalsIgnoreCase("Noun"))
        		{
        			sItemName = sItemName + " " + arrItemPropertyValue[1];
					break;
        		}
        	}
        }
        return sItemName;
	}
}
