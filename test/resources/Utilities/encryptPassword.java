package Utilities;

import java.util.Scanner;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.generic.*;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
public class encryptPassword {
	
	public static String encryptXOR(String sMessage, String sKey)
	{        
		try {
		        if (sMessage==null || sKey==null ) return null;        
		        
		        char[] aKeys=sKey.toCharArray();
		        char[] aMessage=sMessage.toCharArray();            
		        int iMsgLen=aMessage.length;
		        int iKeyLen=aKeys.length;
		        char[] aNewmsg=new char[iMsgLen];            
		        for (int iCounter=0; iCounter<iMsgLen; iCounter++)
		        {
		        	aNewmsg[iCounter]=(char)(aMessage[iCounter]^aKeys[iCounter%iKeyLen]);
		        }
		        aMessage=null; 
		        aKeys=null;
		        return new String(new BASE64Encoder().encodeBuffer(new String(aNewmsg).getBytes()));
		} catch (Exception e) {
		        e.printStackTrace();
		        return null;
		}
	}

		public static String decryptXOR(String sMessage, String sKey){
		    try {
		      if (sMessage==null || sKey==null ) return null;          
		      BASE64Decoder decoder = new BASE64Decoder();
		      char[] aKeys=sKey.toCharArray();
		      char[] aMesg=new String(decoder.decodeBuffer(sMessage)).toCharArray();
		      int iMsgLen=aMesg.length;
		      int iKeylen=aKeys.length;
		      char[] aNewMsg=new char[iMsgLen];
		      for (int iCounte=0; iCounte<iMsgLen; iCounte++){
		    	  aNewMsg[iCounte]=(char)(aMesg[iCounte]^aKeys[iCounte%iKeylen]);
		      }
		      aMesg=null; aKeys=null;
		      return new String(aNewMsg);
		    }
		    catch ( Exception e ) {
		      return null;
		        }  
		      
		}
		public static String updateExcelOperation(String sAction, String sFilePath, String sUserID,String sPassword)
		{
			String sReturnValue = null;
			boolean bFlag =false;
			try 
			{
				// Specify the path of file
				File objFile=new File(sFilePath);			
				// load file in InputStream mode
				FileInputStream objFileInputStream;
				objFileInputStream = new FileInputStream(objFile);			
				// Load workbook
				XSSFWorkbook objWorkBook=new XSSFWorkbook(objFileInputStream);
				// Load sheet- Here we are loading first sheet only
				XSSFSheet objSheet= objWorkBook.getSheetAt(0);
				int iRowCount = objSheet.getLastRowNum();				
				switch (sAction.toLowerCase())
					{
					case "updatepassword":
						bFlag =false;
						for(int iCounter=1;iCounter<iRowCount;iCounter++)
						{	
							// getRow() specify which row we want to read.
							// and getCell() specify which column to read.
							// getStringCellValue() specify that we are reading String data.
							// setCellValue() specify that we are Modify the excel cell with String data.
							if( sUserID.equalsIgnoreCase(objSheet.getRow(iCounter).getCell(1).getStringCellValue()))
							{
								objSheet.getRow(iCounter).getCell(2).setCellValue(sPassword);
								bFlag =true;
								//break;
							}
						}	
						//close file input stream
						objFileInputStream.close();
						// load file in OutputStream mode
						FileOutputStream objFileOutputStream =new FileOutputStream(new File(sFilePath));
						//write in  workbook
						objWorkBook.write(objFileOutputStream);
						objWorkBook.close();
						//close file output stream
						objFileOutputStream.close();
						if (bFlag ==true)
						{
							sReturnValue = "Password is updated for user ["+sUserID +"].";
						}
						else
						{
							sReturnValue = "User Name ["+sUserID + "] not found in [C3PTestUserDetails.xlsx] excel file..";
						}
						break;
					default:
							sReturnValue = "Invalid action";
							break;
						}
				} 
			catch (Exception e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return sReturnValue;
		}
    public static void main(String[] args) {
    	String sOption;
		do
    	{

        	Scanner objScan = new Scanner(System.in); 
    	
	    	 System.out.println("Enter UserName whose password you want to encrypt:"); 
	    	 String sUsername = objScan.next();	    	
	        
	    	  System.out.println("Enter password to be encrypted:");  
	    	
	        String sStringToEncode =objScan.next();
	        String sKey = "ZYXWVUTSRQPONMLKJIHGFEDCBA1234567890abcdefghijklmnopqrstuvwxyz";
	        //String key = "Enter UserName whose password you want to encrypt";
	       
	        encryptPassword objEncrPass = null;
			String sEncryptedString = encryptPassword.encryptXOR(sStringToEncode, sKey);
	        System.out.println("Encrypted password: " + sEncryptedString);
	        //String decodedPwd = encryptPassword.decryptXOR(sEncryptedString, sKey);
	        String sAutomationDir = System.getenv("AutomationDir");
			String sUserDetails = objEncrPass.updateExcelOperation("updatepassword", sAutomationDir + "\\src\\test\\resources\\testData\\C3PTestUserDetails.xlsx", sUsername, sEncryptedString);
	        System.out.println(sUserDetails);
	        System.out.println("");
	        System.out.println("Would you like to encrypt password for another user?(Y/N)");
	        sOption = objScan.next();
	        System.out.println("-------------------------------------------------------------------)");

    	}while(sOption.equalsIgnoreCase("y"));

    }    


}
