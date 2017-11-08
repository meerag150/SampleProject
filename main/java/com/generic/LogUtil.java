package com.generic;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Assert;

//import org.apache.log4j.Logger;

import com.generic.StepBase;

public class LogUtil 
{

	StepBase objStepBase;
	private String passString = "PASS";
	private String failString = "FAIL";
	Format formatter = new SimpleDateFormat("YYYY-MM-dd_hh-mm-ss");
	Date objDate = new Date();
	
	public LogUtil(StepBase stepbase)
	{
		objStepBase = stepbase;
	}
	
	/**
	 * @Method : printLog
	 * @Description : This method prints log using logger
	 * @param sStep : Statement to print as logger
	 * @param bStepResult : This is boolean value based on which logger info or logger error get printed
	 *  
	 */
	public void printLog(String sStep, boolean bStepResult)
	{
		objDate = new Date();
		if(bStepResult == true)
		{
			objStepBase.getLog4jLogger().info(formatter.format(objDate) + " - PASS - " + sStep);
		}
		else
		{
			objStepBase.getLog4jLogger().error(formatter.format(objDate) + " - FAIL - " + sStep);
			Assert.fail();
		}
	}
	
	/**
	 * @Method : printLog
	 * @Description : This method prints log using logger
	 * @param sStep : Statement to print as logger
	 * @param bStepResult : This is boolean value based on which logger info or logger error get printed
	 * @param bPrint : If this value is true then log is printed in to log file.
	 *  
	 */
	public void printLog(String sStep, boolean bStepResult, String bPrint)
	{
		objDate = new Date();
		if(bPrint.trim().equalsIgnoreCase("true"))
		{
			if(bStepResult == true)
			{
				objStepBase.getLog4jLogger().info(formatter.format(objDate) + " - PASS - " + sStep);
			}
			else
			{
				objStepBase.getLog4jLogger().error(formatter.format(objDate) + " - FAIL - " + sStep);
				Assert.fail();
			}
		}
		else if(bStepResult == false)
		{
			objStepBase.getLog4jLogger().error(formatter.format(objDate) + " - FAIL - " + sStep);
			Assert.fail();
		}
	}
	
	/**
	 * Print log info in case of success
	 * @param objLogger: Logger Object
	 * @param sMessage: Message to be print
	 *  
	 */
	/*public void printLoginfo(Logger objLogger, String sMessage)
	{
		sMessage = passString+" :" +sMessage;
		objLogger.info(sMessage);
		
	}*/
	
	/**
	 * Print log info in case of error
	 * @param objLogger: Logger Object
	 * @param sMessage: Message to be print
	 *  
	 */
	/*public void printLogError(Logger objLogger, String sMessage)
	{
		sMessage = failString+" :" +sMessage;
		objLogger.error(sMessage);
		
	}*/
	
	
}
