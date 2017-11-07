package com.stepdefinition;

import java.util.Map;

import org.junit.Assert;
import org.openqa.selenium.By;

import com.generic.StepBase;
import com.generic.Utilities;
import com.generic.WrapperFunctions;

import cucumber.api.java.en.Then;

public class gmailLoginStepDef 
{
	
	private Utilities objUtilities;
	private StepBase objStepBase ;
	private WrapperFunctions objWrappers;
	String sUrl = "https://accounts.google.com/signin/v2/identifier?service=mail&passive=true&rm=false&continue=https%3A%2F%2Fmail.google.com%2Fmail%2F&ss=1&scc=1&ltmpl=default&ltmplcache=2&emr=1&osid=1&flowName=GlifWebSignIn&flowEntry=ServiceLogin";
	By sLblSignIn = By.xpath("//a[text()='Sign In']");
	By txtUserName = By.xpath("//a[text()='Sign In']");
	By sBtnNext = By.xpath("//div[@id='identifierNext']");
	boolean bResult = false;
	
	public gmailLoginStepDef(Utilities utilities,StepBase objStepBase,WrapperFunctions objWrappers) 
	{
		
		this.objUtilities = utilities;
		this.objStepBase = objStepBase;
		this.objWrappers = objWrappers;
	}

	@Then("^gmail user launches (.*) site$")
	public void verifyGoogleSite(String sInput)
	{
		objStepBase.getDriver().get(sUrl);
		//objWrappers.click(sLblSignIn);
		//objWrappers.setText(txtUserName, sUserName);
	}

	@Then("^User enters different values for username$")
	public void enterUserNamePass(Map<String, String> dataTable)
	{
		try {
		if(dataTable != null && dataTable.size() == 1 &&  dataTable.get("username") != null)
		{
			
			By sTxtUserName = By.xpath("//input[@type='email']");
			bResult = objWrappers.setText(sTxtUserName, dataTable.get("username"));
			Assert.assertTrue("FAIL : Fail to put username '"+sTxtUserName+"'",bResult );
				objWrappers.waitFor(5);
				bResult = objWrappers.click(sBtnNext);
				Assert.assertTrue("FAIL : Fail click on button '"+sBtnNext+"'",bResult );
		}
		
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		
	}
	@Then("^User enters different values for password$")
	public void enterP(Map<String, String> dataTable)
	{
		try {
		
		if(dataTable != null && dataTable.size() == 1 &&  dataTable.get("password") != null)
		{
			By sTxtPassword = By.xpath("//input[@type='password']");
			String sDecryptedPassword = objUtilities.decryptPassword(dataTable.get("password"));
			bResult = objWrappers.setText(sTxtPassword, sDecryptedPassword);
			Assert.assertTrue("FAIL : Fail to put username '"+sTxtPassword+"'",bResult );
				objWrappers.waitFor(5);
				objWrappers.click(sBtnNext);
				Assert.assertTrue("FAIL : Fail click on button '"+sBtnNext+"'",bResult );
		}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		
	}
	
}
