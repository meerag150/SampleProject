package com.generic;
import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.internal.Coordinates;
import org.openqa.selenium.internal.Locatable;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;

/**
 * @ScriptName    : WrapperFunctions
 * @Description   : Core wrapper function required for framework
 *  
 */
public class WrapperFunctions_Chrome
{
	private StepBase objStepBase;
	private AngularHandle objAngularHandle;
	public WrapperFunctions_Chrome(StepBase objStepBase, AngularHandle objAngularHandle)
	{
		this.objAngularHandle = objAngularHandle;
		this.objStepBase = objStepBase;
	}


	//private AngularHandle objAngularHandle =new AngularHandle();

	/**
	 * @Method : waitForElementPresence
	 * @Description	: This is wrapper method waits for objElement presence located by objLocator
	 * @param		: objLocator - By identification of objElement
	 *  
	*/
	public void waitForElementPresence(By objLocator) throws NotFoundException
	{
		try
		{
		//objAngularHandle.waitUntilAngularReady();
		//objAngularHandle.waitUntilJQueryReady();
			objStepBase.getDriverWait().until(ExpectedConditions.presenceOfElementLocated(objLocator));
		}
		catch(Exception objException)
		{
			throw objException;
		}
	} 

	/**
	 * @Method : waitForElementToBeClickable
	 * @Description	: This is wrapper method wait for objElement to be clickable
	 * @param		: objLocator - By identification of objElement
	 *  
	 */
	public void waitForElementToBeClickable(By objLocator) throws NotFoundException
	{
		try
		{
			objStepBase.getDriverWait().until(ExpectedConditions.elementToBeClickable(objLocator));
		}
		catch(Exception objException)
		{
			throw objException;
		}
		
	}
	
	/**
	 * @Method : waitForWebElementToBeClickable
	 * @Description : This is wrapper method waits for webelement to be clickable
	 * @param		: objWebElement - This is web objElement under consideration
	 *  
	 */
	 //RPHADKE_16Aug2017 - Code which previously using expected condition wait is now commented as it was not working consistently. Now using fluent waits
	/*public void waitForWebElementToBeClickable(WebElement objWebElement) throws NotFoundException
	{
		objStepBase.getDriverWait().until(ExpectedConditions.elementToBeClickable(objWebElement));
	}*/
	public void waitForWebElementToBeClickable(WebElement objElement, int maxWait, int incrementor) 
	{ 
		try
		{

			/* This incrementor will be loaded from properties */ 
	
			for (int i = 0; i <= incrementor; i++) 
			{ 
	
				/* 
				* FluentWait implementation here Polling time is also loaded 
				* from the properties 
			   */ 
				Wait<WebDriver> wait = new FluentWait<WebDriver>(objStepBase.getDriver()) 
				.withTimeout(maxWait, TimeUnit.SECONDS) 
				.pollingEvery(2, TimeUnit.SECONDS) 
				.ignoring(NoSuchElementException.class, 
				StaleElementReferenceException.class); 
				wait.until(ExpectedConditions.elementToBeClickable((objElement))); 
				if(objElement.isDisplayed())
					break;
				//break; 
			}
		}
		catch(Exception objException)
		{
			throw objException;
		}
	}


	/**
	 * @Method : waitForElementVisibilityLocated
	 * @Description	: This is wrapper method wait for visibility of objElement located
	 * @param		: objLocator - By identification of objElement
	 *  
	 */
	public void waitForElementVisibilityLocated(By objLocator) throws NotFoundException
	{
		try
		{
			objStepBase.getDriverWait().until(ExpectedConditions.visibilityOfElementLocated(objLocator));
		}
		catch(Exception objException)
		{
			throw objException;
		}
	}

	/**
	 * @Method		: waitFor
	 * @Description	: Waits for the specified amount of [timeInMilliseconds].
	 * @param		: timeUnitSeconds - wait time seconds
	 *  
	 * @throws Exception 
	 */
	public void waitFor(int timeUnitSeconds) throws Exception
	{
		try 
		{
			Thread.sleep(TimeUnit.MILLISECONDS.convert(timeUnitSeconds, TimeUnit.SECONDS));
		}
		catch(Exception objException)
		{
			throw objException;
		}
	}

	/**
	 * @Method checkElementDisplayed
	 * @Description	: This is wrapper method to check the web objElement is dispalyed on the page
	 * @param		: objLocator - By identification of objElement
	 * @return		: - true if objElement present 
	 *  
	 */
	public boolean checkElementDisplayed(By objLocator)
	{
		try
		{
			//this.waitForElementVisibilityLocated(objLocator);
			fluentWaitForVisibility(objLocator, Integer.parseInt(objStepBase.getConfig().getProperty("MID_WAIT")));
			return objStepBase.getDriver().findElement(objLocator).isDisplayed();
		}
		catch(Exception objException)
		{
			throw objException;
		}
	}

	/**
	 * @Method : checkElementEnabled
	 * @Description	: This is wrapper method to check the web objElement is enabled on the page
	 * @param		: objLocator - By identification of objElement
	 * @return		: - true if objElement present 
	 *  
	 */
	public boolean checkElementEnabled(By objLocator)
	{
		try
		{
			fluentWaitForVisibility(objLocator, Integer.parseInt(objStepBase.getConfig().getProperty("MID_WAIT")));
			return objStepBase.getDriver().findElement(objLocator).isEnabled();
		}
		catch(Exception objException)
		{
			throw objException;
		}
	}


	/**
	 * @Method		: click
	 * @Description	: This is wrapper method to click on web objElement 
	 * @param		: objLocator - By identification of objElement
	 * @return		: - true if click successful
	 *  
	 */
	public boolean click(By objLocator) 
	{
		try
		{
			//waitForElementPresence(objLocator);
			//waitForElementToBeClickable(objLocator);
			fluentWaitForPresence(objLocator, Integer.parseInt(objStepBase.getConfig().getProperty("MAX_WAIT")));
			//fluentWaitForClickable(objLocator, Integer.parseInt(objStepBase.getConfig().getProperty("MAX_WAIT")));
			WebElement objWebElement = objStepBase.getDriver().findElement(objLocator);
			//waitForWebElementToBeClickable(objWebElement, Integer.parseInt(objStepBase.getConfig().getProperty("MAX_WAIT")), Integer.parseInt(objStepBase.getConfig().getProperty("incrementor")));
			objWebElement.click();
			return true;

		}
		catch(Exception objException)
		{
			try
			{
				fluentWaitForPresence(objLocator, Integer.parseInt(objStepBase.getConfig().getProperty("MAX_WAIT")));
				jsScrollTillDisplayed(objLocator);
				return click(objLocator);
			}
			catch(Exception objException1)
			{
				throw objException1;
			}
			
		}
	}
	
	/**
	 * @Method		: click
	 * @Description	: This is wrapper method to click on web objElement without applying wait
	 * @param		: objLocator - By identification of objElement
	 * @return		: - true if click successful
	 *  
	 */
	public boolean clickWithoutWait(By objLocator) 
	{
		try
		{
			WebElement objWebElement = objStepBase.getDriver().findElement(objLocator);
			objWebElement.click();
			return true;
		}
		catch(Exception objException)
		{
			throw objException;
		}
	}
	
	/**
	 * @Method		: rightClick
	 * @Description	: This is wrapper method used to right Click on objElement
	 * @param		: objLocator - By identification of objElement
	 * @return		: - true if right click successful
	 * @author		: RPHADKE_13Jul2017   
	 */
	public boolean rightClick(By objLocator)
	{
		try
		{
			fluentWaitForPresence(objLocator, Integer.parseInt(objStepBase.getConfig().getProperty("MAX_WAIT")));
			//fluentWaitForClickable(objLocator, Integer.parseInt(objStepBase.getConfig().getProperty("MAX_WAIT")));
			WebElement objWebElement = objStepBase.getDriver().findElement(objLocator);
			Actions objAction = new Actions(objStepBase.getDriver());
			objAction.moveToElement(objWebElement);
			objAction.contextClick(objWebElement).build().perform();
			return true;
		}
		catch(Exception objException)
		{
			throw objException;
		}
	}
	
	/**
	 * @Method : clickOnWebelement
	 * @Description : This method clicks on webelement passed by user
	 * @param objWebElement
	 * @return boolean sValue based whether click is successful or not
	 *  
	 */
	public boolean clickOnWebelement(WebElement objWebElement)
	{
		try
		{
			//waitForWebElementToBeClickable(objWebElement, Integer.parseInt(objStepBase.getConfig().getProperty("MAX_WAIT")), Integer.parseInt(objStepBase.getConfig().getProperty("incrementor")));
			objWebElement.click();
			return true;
		}
		catch(Exception objException)
		{
			objWebElement.click();
			throw objException;
		}
	}

	/**
	 * @Method		: doubleClick
	 * @Description	: This is wrapper method used for doubleClick on objElement
	 * @param		: objLocator - By identification of objElement
	 * @return		: - true if double click successful
	 * @author		: (SQS)   
	 */
	public boolean doubleClick(By objLocator)
	{
		try
		{
			fluentWaitForPresence(objLocator, Integer.parseInt(objStepBase.getConfig().getProperty("MAX_WAIT")));
			//fluentWaitForClickable(objLocator, Integer.parseInt(objStepBase.getConfig().getProperty("MAX_WAIT")));
			WebElement objWebElement = objStepBase.getDriver().findElement(objLocator);
			Actions objAction = new Actions(objStepBase.getDriver());
			objAction.doubleClick(objWebElement).build().perform();
			return true;
		}
		catch(Exception objException)
		{
			throw objException;
		}
	}

	/**
	 * @Method		: setText
	 * @Description	: This is wrapper method to set text for input objElement
	 * @param		: objLocator - By identification of objElement
	 * @param		: sFieldValue - field sValue as string 
	 * @return		: - true if text entered successfully
	 * @author		: (SQS) 
	 */
	public boolean setText(By objLocator, String sFieldValue) 
	{
		try
		{
			//RPHADKE_16Aug2017 - commented wait till expected condition is true for presence and visibility
			//waitForElementPresence(objLocator);
			//waitForElementVisibilityLocated(objLocator);
			WebElement objWebElement = objStepBase.getDriver().findElement(objLocator);
			objWebElement.clear();
			objWebElement.sendKeys(sFieldValue);
			return true;
		}
		catch(Exception objException)
		{
			throw objException;
		}
	}

	/**
	 * @Method		: sendKeyBoardKeys
	 * @Description	: This is wrapper method is used to send keyboard keys
	 * @param		: objLocator - By identification of objElement
	 * @param		: sKey - sKey name
	 * @return		: - true if text entered successfully
	 * @author		: (SQS) 
	 */
	public boolean sendKeyBoardKeys(By objLocator, String sKey) 
	{
		try
		{
			waitForElementPresence(objLocator);
			WebElement objWebElement = objStepBase.getDriver().findElement(objLocator);
			if(sKey.equalsIgnoreCase("enter"))
				objWebElement.sendKeys(Keys.ENTER);
			if(sKey.equalsIgnoreCase("shift"))
				objWebElement.sendKeys(Keys.SHIFT);
			if(sKey.equalsIgnoreCase("tab"))
				objWebElement.sendKeys(Keys.TAB);
			return true;
		}
		catch(Exception objException)
		{
			throw objException;
		}
	}


	/**
	 * @Method		: getText
	 * @Description	: This is wrapper method to get text form input elements
	 * @param		: objLocator - By identification of objElement
	 * @param		: sTextBy - get text by sValue attribute (set sTextBy as sValue)/ by visible text (set sTextBy as text)
	 * @return		: - text as string
	 * @author		: (SQS) 
	 */
	public String getText(By objLocator, String sTextBy) 
	{
		try
		{
			fluentWaitForPresence(objLocator, Integer.parseInt(objStepBase.getConfig().getProperty("MAX_WAIT")));
			//fluentWaitForClickable(objLocator, Integer.parseInt(objStepBase.getConfig().getProperty("MAX_WAIT")));
			WebElement objWebElement = objStepBase.getDriver().findElement(objLocator);
			String sText = "";
			if(sTextBy.equalsIgnoreCase("sValue"))
				sText = objWebElement.getAttribute("sValue");
			else if(sTextBy.equalsIgnoreCase("text"))
				sText = objWebElement.getText();
			return sText; 
		}
		catch(Exception objException)
		{
			throw objException;
		}
	}

	/**
	 * @Method		: selectCheckBox
	 * @Description	: This is wrapper method select/deselect checkbox
	 * @param		: objLocator - By identification of objElement
	 * @param		: bStatus - select/deselect 
	 * @author		: (SQS) 
	 */
	public boolean selectCheckBox(By objLocator, boolean bStatus) 
	{
		try
		{
			fluentWaitForPresence(objLocator, Integer.parseInt(objStepBase.getConfig().getProperty("MAX_WAIT")));
			//fluentWaitForClickable(objLocator, Integer.parseInt(objStepBase.getConfig().getProperty("MAX_WAIT")));
			WebElement objWebElement = objStepBase.getDriver().findElement(objLocator);
			if(objWebElement.getAttribute("type").equals("checkbox"))   
			{
				if((objWebElement.isSelected() && !bStatus) || (!objWebElement.isSelected() && bStatus))
					objWebElement.click();
				return true;
			}
			else
				return false;
		}
		catch(Exception objException)
		{
			throw objException;
		}
	}

	/**
	 * @Method		: isCheckBoxSelected
	 * @Description	: This is wrapper checkbox is selected or not
	 * @param		: objLocator - By identification of objElement
	 * @author		: (SQS)  
	 */
	public boolean isCheckBoxSelected(By objLocator, boolean bStatus) 
	{
		boolean bState = false;
		try
		{
			fluentWaitForPresence(objLocator, Integer.parseInt(objStepBase.getConfig().getProperty("MAX_WAIT")));
			//fluentWaitForClickable(objLocator, Integer.parseInt(objStepBase.getConfig().getProperty("MAX_WAIT")));
			WebElement objWebElement = objStepBase.getDriver().findElement(objLocator);
			if(objWebElement.getAttribute("type").equals("checkbox"))   
				bState = objWebElement.isSelected();
			return bState;
		}
		catch(Exception objException)
		{
			throw objException;
		}
	}

	/**
	 * @Method		: selectRadioButton
	 * @Description	: This is wrapper method select/deselect radio button
	 * @param		: objLocator - By identification of objElement
	 * @param		: bStatus - select/deselect 
	 * @author		: (SQS)
	 */
	public boolean selectRadioButton(By objLocator, boolean bStatus)
	{
		try
		{
			fluentWaitForPresence(objLocator, Integer.parseInt(objStepBase.getConfig().getProperty("MAX_WAIT")));
			//fluentWaitForClickable(objLocator, Integer.parseInt(objStepBase.getConfig().getProperty("MAX_WAIT")));
			WebElement objWebElement = objStepBase.getDriver().findElement(objLocator);
			if(objWebElement.getAttribute("type").equals("radio"))   
			{
				if((objWebElement.isSelected() && !bStatus) || (!objWebElement.isSelected() && bStatus))
					objWebElement.click();
				return true;
			}
			else
				return false;
		}
		catch(Exception objException)
		{
			throw objException;
		}
	}

	/**
	 * @Method		: isRadioButtonSelected
	 * @Description	: This is wrapper RadioButton is selected or not
	 * @param		: objLocator - By identification of objElement
	 * @author		: (SQS)  
	 */
	public boolean isRadioButtonSelected(By objLocator)	//, boolean bStatus) 
	{
		boolean bState = false;
		try
		{
			fluentWaitForPresence(objLocator, Integer.parseInt(objStepBase.getConfig().getProperty("MAX_WAIT")));
			WebElement objWebElement = objStepBase.getDriver().findElement(objLocator);
			if(objWebElement.getAttribute("type").equals("radio"))   
				bState = objWebElement.isSelected();

			return bState;
		}
		catch(Exception objException)
		{
			throw objException;
		}
	}

	/**
	 * @Method		: mouseHover
	 * @Description	: This is wrapper method used for Mouse Hovering to the objElement
	 * @param		: objLocator - By identification of objElement
	 * @author		: (SQS)
	 */
	public boolean mouseHover(By objLocator)
	{
		try
		{
			fluentWaitForPresence(objLocator, Integer.parseInt(objStepBase.getConfig().getProperty("MAX_WAIT")));
			WebElement objWebElement = objStepBase.getDriver().findElement(objLocator);
			Actions objAction = new Actions(objStepBase.getDriver());
			objAction.moveToElement(objWebElement).build().perform();
			return true;
		}
		catch(Exception objException)
		{
			throw objException;
		}
	}

	/**
	 * @Method		: switchToWindowUsingTitle
	 * @Description	: This is wrapper method used switch to sWindow using the given title
	 * @param		: objLocator - Window title
	 * @author		: (SQS)  
	 */
	public boolean switchToWindowUsingTitle(String sWindowTitle)
	{
		try
		{
			String sMainWindowHandle = objStepBase.getDriver().getWindowHandle();
			Set<String> sOpenWindows = objStepBase.getDriver().getWindowHandles();

			if (!sOpenWindows.isEmpty()) 
			{
				for (String sWindows : sOpenWindows) 
				{
					String sWindow = objStepBase.getDriver().switchTo().window(sWindows).getTitle();
					if (sWindowTitle.equals(sWindow)) 
						return true;
					else 
						objStepBase.getDriver().switchTo().window(sMainWindowHandle);
				}
			}
			return false;
		}
		catch(Exception objException)
		{
			throw objException;
		}
	}

	/**
	 * @Method		: saveWindowHandle
	 * @Description	: This is wrapper method used save current sWindow handle
	 * @param		: objLocator - Window title
	 * @retun		: sWindowHandle 
	 * @author		: (SQS)  
	 */
	public String saveWindowHandle()
	{
		try
		{
			String sMainWindowHandle = objStepBase.getDriver().getWindowHandle();
			return sMainWindowHandle;
		}
		catch(Exception objException)
		{
			throw objException;
		}
	}

	/**
	 * @Method		: switchToWindowUsingURL
	 * @Description	: This is wrapper method used switch to sWindow using the given URL
	 * @param		: objLocator - Window title
	 * @author		: (SQS)  
	 */
	public boolean switchToWindowUsingURL(String sWindowURL)
	{
		try
		{
			String sMainWindowHandle = objStepBase.getDriver().getWindowHandle();
			Set<String> sOpenWindows = objStepBase.getDriver().getWindowHandles();

			if (!sOpenWindows.isEmpty()) 
			{
				for (String sWindows : sOpenWindows) 
				{
					String sCurrentWindowURL = objStepBase.getDriver().switchTo().window(sWindows).getCurrentUrl();
					if (sWindowURL.equals(sCurrentWindowURL)) 
						return true;
					else 
						objStepBase.getDriver().switchTo().window(sMainWindowHandle);
				}
			}
			return false;
		}
		catch(Exception objException)
		{
			throw objException;
		}
	}

	/**
	 * @Method		: switchToWindowUsingURL
	 * @Description	: This is wrapper method used switch to sWindow using the given URL
	 * @param		: objLocator - Window title
	 * @author		: (SQS)  
	 */
	public boolean switchToWindowContainsURL(String sWindowURL)
	{
		try
		{
			String sMainWindowHandle = objStepBase.getDriver().getWindowHandle();
			Set<String> sOpenWindows = objStepBase.getDriver().getWindowHandles();

			if (!sOpenWindows.isEmpty()) 
			{
				for (String sWindows : sOpenWindows) 
				{
					String sCurrentWindowURL = objStepBase.getDriver().switchTo().window(sWindows).getCurrentUrl();
					if (sWindowURL.contains(sCurrentWindowURL)) 
						return true;
					else 
						objStepBase.getDriver().switchTo().window(sMainWindowHandle);
				}
			}
			return false;
		}
		catch(Exception objException)
		{
			throw objException;
		}
	}

	/**
	 * @Method		: switchToWindowUsingHandle
	 * @Description	: This is wrapper method used switch to sWindow using the given Handle
	 * @param		: objLocator - Window title
	 * @author		: (SQS)  
	 */
	public boolean switchToWindowUsingHandle(String sWindowHandle)
	{
		try
		{
			String sMainWindowHandle = objStepBase.getDriver().getWindowHandle();
			Set<String> sOpenWindows = objStepBase.getDriver().getWindowHandles();

			if (!sOpenWindows.isEmpty()) 
			{
				for (String sWindows : sOpenWindows) 
				{
					objStepBase.getDriver().switchTo().window(sWindows);
					if (sWindows.equals(sWindowHandle)) 
						return true;
					else 
						objStepBase.getDriver().switchTo().window(sMainWindowHandle);
				}
			}
			return false;
		}
		catch(Exception objException)
		{
			throw objException;
		}
	}

	/**
	 * @Method		: switchToWindowUsingHandle
	 * @Description	: This is wrapper method used switch to sWindow using the given Handle
	 * @param		: objLocator - Window title
	 * @author		: (SQS)  
	 */
	public boolean closeCurrentWindowAndSwitchToNewWindowUsingHandle(String sWindowHandle)
	{
		try
		{
			String sMainWindowHandle = objStepBase.getDriver().getWindowHandle();
			Set<String> sOpenWindows = objStepBase.getDriver().getWindowHandles();

			if (!sOpenWindows.isEmpty()) 
			{
				objStepBase.getDriver().switchTo().window(sMainWindowHandle).close();
				for (String sWindows : sOpenWindows) 
				{
					objStepBase.getDriver().switchTo().window(sWindows);
					if (sWindows.equals(sWindowHandle)) 
						return true;
				}
			}
			return false;
		} 
		catch (Exception exception)
		{
			//exception.printStackTrace();
			return false;
		}
	}

	/**
	 * @Method		: switchTabToRight
	 * @Description	: switch to tab to right (work only if new tab get opened to right from main current sWindow)
	 * @param		:  
	 * @author		: (SQS)  
	 */
	public boolean switchTabToRight()
	{
		try
		{
			String sMainWindowHandle = objStepBase.getDriver().getWindowHandle();
			Set<String> sOpenWindows = objStepBase.getDriver().getWindowHandles();

			if (!sOpenWindows.isEmpty()) 
			{
				for (String sWindows : sOpenWindows) 
				{
					if(!sWindows.equals(sMainWindowHandle)){
						objStepBase.getDriver().switchTo().window(sWindows);
						return true;
					}
				}
			}
			return false;
		}
		catch(Exception objException)
		{
			throw objException;
		}
	}

	/**
	 * @Method		: switchToFrameUsingIframe_Element
	 * @Description	: This method  will switch you to the Frame by Frame name
	 * @param		: objLocator - The most common one. You locate your iframe like other elements, 
	 * 				  then pass it into the method
	 * 				  eg.driver.switchTo().frame(driver.findElement(By.xpath(".//iframe[@title='Fill Quote']")))
	 * @author		: (SQS)  
	 */
	public boolean switchToFrameUsingIframeElement(By objLocator)
	{
		try
		{
			objStepBase.getDriver().switchTo().defaultContent();
			objStepBase.getDriver().switchTo().frame(objStepBase.getDriver().findElement(objLocator));
			return true;
		}
		catch(Exception objException)
		{
			throw objException;
		}
	}

	/**
	 * @Method		: switchToFrameUsingNameOrId
	 * @Description	: This method  will switch you to the Frame by Frame name
	 * @param		: sFrameName - Name/Id of  frame you want to switch
	 * @author		: (SQS)  
	 */
	public boolean switchToFrameUsingNameOrId(String sFrameName)
	{
		try
		{
			objStepBase.getDriver().switchTo().defaultContent();
			objStepBase.getDriver().switchTo().frame(sFrameName);
			return true;
		}
		catch(Exception objException)
		{
			throw objException;
		}
	}

	/**
	 * @Method		: switchToFrameUsingIndex
	 * @Description	: This method  will switch you to the Frame by Frame name
	 * @param		: iIndex - Index on page
	 * @author		: (SQS)   
	 */
	public boolean switchToFrameUsingIndex(int iIndex)
	{
		try
		{
			objStepBase.getDriver().switchTo().defaultContent();
			objStepBase.getDriver().switchTo().frame(iIndex);
			return true;
		}
		catch(Exception objException)
		{
			throw objException;
		}
	}

	/**
	 * @Method		: switchToDefaultContent
	 * @Description	: This method  will switch you to the default Window
	 * @author		: Harshvardhan Yadav (SQS)  
	 */
	public void switchToDefaultContent()
	{
		try
		{
			objStepBase.getDriver().switchTo().defaultContent();
		}
		catch(Exception objException)
		{
			throw objException;
		}
	}

	/**
	 * @Method		: selectDropDownOption
	 * @Description	: This is wrapper method select drop down objElement
	 * @param		: objLocator - By identification of objElement
	 * @param		: sOption - drop down objElement (user may specify text/sValue/Index)
	 * @param		: sSelectType - select dorp down objElement by Text/Value/Index
	 * @author		: (SQS) 
	 */
	public boolean selectDropDownOption(By objLocator, String sOption, String... sSelectType) 
	{
		try
		{
			fluentWaitForPresence(objLocator, Integer.parseInt(objStepBase.getConfig().getProperty("MAX_WAIT")));
			//fluentWaitForClickable(objLocator, Integer.parseInt(objStepBase.getConfig().getProperty("MAX_WAIT")));
			WebElement objWebElement = objStepBase.getDriver().findElement(objLocator);
			Select sltDropDown = new Select(objWebElement);

			if(sSelectType.length > 0 && !sSelectType[0].equals(""))
			{
				if(sSelectType[0].equalsIgnoreCase("Value"))
					sltDropDown.selectByValue(sOption);
				else if(sSelectType[0].equalsIgnoreCase("Text"))
					sltDropDown.selectByVisibleText(sOption);
				else if(sSelectType[0].equalsIgnoreCase("Index"))
					sltDropDown.selectByIndex(Integer.parseInt(sOption));

				return true;
			}
			else
			{
				// Web elements from dropdown list 
				List<WebElement> objOptions = sltDropDown.getOptions();
				boolean bOptionAvailable = false;
				int iIndex = 0;
				for(WebElement weOptions : objOptions)  
				{  
					if (weOptions.getText().trim().equals(sOption))
					{
						sltDropDown.selectByIndex(iIndex);
						bOptionAvailable = true;
						break;
					}
					else
						iIndex++;
				}
				return bOptionAvailable;
			}
		}
		catch(Exception objException)
		{
			throw objException;
		}
	}

	/**
	 * @Method		: verifyDropDownOptionValues
	 * @Description	: This is wrapper method select drop down objElement
	 * @param		: objLocator - By identification of objElement
	 * @param		: sOption - if want to verify more then one sOption pass values separated by ';'
	 * @author		: (SQS)
	 */
	public boolean verifyDropDownOptionValues(By objLocator, String sOption) 
	{
		try
		{
			fluentWaitForPresence(objLocator, Integer.parseInt(objStepBase.getConfig().getProperty("MAX_WAIT")));
			WebElement objWebElement = objStepBase.getDriver().findElement(objLocator);
			Select sltDropDown = new Select(objWebElement);

			// Web elements from dropdown list 
			List<WebElement> objOptions = sltDropDown.getOptions();
			boolean bOptionAvailable = false;
			ArrayList<String> optionsList;

			if(sOption.contains(";"))
				optionsList = new ArrayList<String>(Arrays.asList(sOption.trim().split(";")));
			else
			{
				optionsList =  new ArrayList<String>();
				optionsList.add(sOption);
			}

			for(WebElement weOptions : objOptions)  
			{  
				String optionValue = weOptions.getText().trim();
				if(optionsList.contains(optionValue))
				{
					bOptionAvailable = true;
					optionsList.remove(optionValue);
					if(optionsList.isEmpty())
						break;
				}
			}
			return bOptionAvailable;
		}
		catch(Exception objException)
		{
			throw objException;
		}
	}

	/**
	 * @Method		: getSelectedValueFromDropDown
	 * @Description	: This is wrapper method select drop down objElement
	 * @param		: objLocator - By identification of objElement
	 * @author		: (SQS)  
	 */
	public String getSelectedValueFromDropDown(By objLocator) 
	{
		try
		{
			fluentWaitForPresence(objLocator, Integer.parseInt(objStepBase.getConfig().getProperty("MAX_WAIT")));
			Select selectDorpDown = new Select(objStepBase.getDriver().findElement(objLocator));
			String selectedDorpDownValue = selectDorpDown.getFirstSelectedOption().getText();
			return selectedDorpDownValue;
		}
		catch(Exception objException)
		{
			throw objException;
		}
	}

	/**
	 * @Method		: selectValueFromUnorderedList
	 * @Description	: This is wrapper method select drop down objElement 
	 * 					from UnorderedList eg. <ul><li></li><li></li></ul>
	 * @param		: objLocator - By identification of main list
	 * @param		: sValue - Value to select
	 * @author		: (SQS)  
	 */
	public boolean selectValueFromUnorderedList(By objLocator, String sValue) 
	{
		try
		{
			boolean returnValue = false;

			fluentWaitForPresence(objLocator, Integer.parseInt(objStepBase.getConfig().getProperty("MAX_WAIT")));
			fluentWaitForClickable(objLocator, Integer.parseInt(objStepBase.getConfig().getProperty("MAX_WAIT")));
			WebElement unorderedList = objStepBase.getDriver().findElement(objLocator);
			//unorderedList.click();

			List<WebElement> objOptions = unorderedList.findElements(By.tagName("li"));

			for (WebElement sOption : objOptions) {
				if (sValue.equals(sOption.getText())) {
					sOption.click();
					returnValue = true;
					break;
				}
			}
			return returnValue;
		}
		catch(Exception objException)
		{
			throw objException;
		}
	}

	/**
	 * @Method		: getAttribute
	 * @Description	: This function return objLocator attribute
	 * @param		: objLocator - By identification of objElement
	 * @author		: (SQS)  
	 */	
	public String getAttribute(By objLocator, String sElementAttribute)
	{
		try
		{
			fluentWaitForPresence(objLocator, Integer.parseInt(objStepBase.getConfig().getProperty("MAX_WAIT")));
			WebElement objWebElement = objStepBase.getDriver().findElement(objLocator);
			return objWebElement.getAttribute(sElementAttribute);
		}
		catch(Exception objException)
		{
			throw objException;
		}
	}

	/**
	 * 
	 * @param objWebElement
	 * @param sElementAttribute
	 * @return
	 *  
	 */
	public String getWebElementAttribute(WebElement objWebElement, String sElementAttribute)
	{
		try
		{
			//waitForWebElementToBeClickable(objWebElement, Integer.parseInt(objStepBase.getConfig().getProperty("MAX_WAIT")), Integer.parseInt(objStepBase.getConfig().getProperty("incrementor")));
			return objWebElement.getAttribute(sElementAttribute);
		}
		catch(Exception objException)
		{
			throw objException;
		}
	}

	/**
	 * @Method					: selectCheckBoxForSpecificColumn
	 * @Description				: This is wrapper method to select chechbox from table with respect to particular column content
	 * @param					: objLocator - By identification of objElement (table with all rows)
	 * @param					: sColumnContent - String column content
	 * @param					: bStatus true - to check the checkbox
	 *                                  false - to uncheck the checkbox
	 * @columnNumberForRadio	: integer column number for radio button
	 * @author					: (SQS) 
	 */
	public boolean selectCheckBoxForSpecificColumn(By objLocator, String sColumnContent, int iColumnNumberForCheckbox, boolean bStatus) 
	{
		try
		{
			WebElement weResultTable = objStepBase.getDriver().findElement(objLocator);
			List<WebElement> weResultRows = weResultTable.findElements(By.xpath(".//tbody/tr"));
			for(WebElement weRow : weResultRows)
			{
				List<WebElement> objColumns = weRow.findElements(By.xpath(".//td"));
				for(WebElement weColumn : objColumns)
				{
					if(weColumn.getText().trim().equals(sColumnContent))
					{
						By tempLocator = By.xpath(".//td['" + iColumnNumberForCheckbox + "']/span/input[@type='checkbox']");
						WebElement objCheckbox = weRow.findElement(tempLocator);
						if((objCheckbox.isSelected() && !bStatus) || (!objCheckbox.isSelected() && bStatus))
						{
							objCheckbox.click();
							return true;
						}
					}
				}
			}
			return false;
		}
		catch(Exception objException)
		{
			throw objException;
		}
	}


	/**
	 * @Method			: verifyTableContent
	 * @Description		: it will check given data in whole table
	 * @param			: objLocator - By identification of objElement (table with all rows)
	 * @param			: sColumnHeader - String column header
	 * @param			: sContentToVerify - String Content to be verifyed from excel 
	 * @author			: (SQS)
	 */
	public boolean verifyTableContent(By objLocator, String sColumnHeader, String sContentToVerify ) 
	{
		Hashtable<String , String> dataColumnHeader = new Hashtable<String, String>();
		int intColumnNumber = 1;
		boolean bVerify = false;
		try
		{
			fluentWaitForPresence(objLocator, Integer.parseInt(objStepBase.getConfig().getProperty("MAX_WAIT")));
			WebElement weResultTable = objStepBase.getDriver().findElement(objLocator);

			List<WebElement> weColumnsHeaders = weResultTable.findElements(By.xpath(".//thead/tr/th"));
			for(WebElement weColumnHeader : weColumnsHeaders)
			{
				String strHeader = weColumnHeader.getText().trim();
				if(!strHeader.equals(""))
					dataColumnHeader.put(strHeader, String.valueOf(intColumnNumber));
				intColumnNumber ++;
			}

			List<WebElement> weRows = weResultTable.findElements(By.xpath(".//tbody/tr"));
			for(WebElement weRow : weRows)
			{
				WebElement weExceptedClm = weRow.findElement(By.xpath(".//td[" + dataColumnHeader.get(sColumnHeader) + "]"));
				if(weExceptedClm.getText().trim().equals(sContentToVerify))
				{
					bVerify = true;
					return bVerify;
				}
			}
			return bVerify;
		}
		catch(Exception objException)
		{
			throw objException;
		}
	}

	/**
	 * @method 	: 	getAttributeValue
	 * @param 	: 	objLocator :  By identification of objElement
	 * @param 	:	AttributeName : Name of attribute Whose sValue we want
	 *  
	 */
	public String getAttributeValue(By objLocator, String attributeName)
	{
		try
		{
			fluentWaitForPresence(objLocator, Integer.parseInt(objStepBase.getConfig().getProperty("MAX_WAIT")));
			WebElement objWebElement =	objStepBase.getDriver().findElement(objLocator);
			return objWebElement.getAttribute(attributeName);
		}
		catch(Exception objException)
		{
			throw objException;
		}
	} 

	/**
	 * @method 	: 	getTagName
	 * @param 	:	objLocator
	 * @return 	:	TageName for given objLocator
	 *  
	 * 
	 */
	public String getTagName(By objLocator)
	{
		try
		{
			fluentWaitForPresence(objLocator, Integer.parseInt(objStepBase.getConfig().getProperty("MAX_WAIT")));
			WebElement objWebElement = objStepBase.getDriver().findElement(objLocator);
			return objWebElement.getTagName();
		}
		catch(Exception objException)
		{
			throw objException;
		}
	} 

	/**
	 * @Method		: elementHighlight
	 * @Description	: Highlight objElement 
	 * @param		: objLocator - By identification of objElement 
	 *  
	 */
	public void elementHighlight(By objLocator)
	{
		try
		{
			WebElement objElement = objStepBase.getDriver().findElement(objLocator);
			for (int i = 0; i < 2; i++) 
			{
				JavascriptExecutor js = (JavascriptExecutor) objStepBase.getDriver();
				js.executeScript(
						"arguments[0].setAttribute('style', arguments[1]);",
						objElement, "color: red; border: 3px solid red;");
				js.executeScript(
						"arguments[0].setAttribute('style', arguments[1]);",
						objElement, "");
			}
		}
		catch(Exception objException)
		{
			throw objException;
		}
	} 
	
	/**
	 * @Method		: pageRefresh
	 * @Description	: This method is to refresh page  
	 *  
	 */
	public void pageRefresh()
	{
		try
		{
			objStepBase.getDriver().navigate().refresh();
		}
		catch(Exception objException)
		{
			throw objException;
		}
	}
	
	/**
	 * @Method		: getWebElementList
	 * @Description	: This method return webelement list based on locator passed 
	 * @param		: objLocator - By identification of objElement 
	 *  
	 */
	public List<WebElement> getWebElementList(By objLocator)
	{
		try
		{
			List<WebElement> webElementList = new ArrayList<WebElement>();
			fluentWaitForPresence(objLocator, Integer.parseInt(objStepBase.getConfig().getProperty("MAX_WAIT")));
			//fluentWaitForClickable(objLocator, Integer.parseInt(objStepBase.getConfig().getProperty("MAX_WAIT")));
			webElementList = objStepBase.getDriver().findElements(objLocator);
			return webElementList;
		}
		catch(Exception objException)
		{
			return null;
		}
	}
	
	/**
	 * @Method		: checkElementIsPresent
	 * @Description	: This method checks presence of an objElement passed as a locator 
	 * @param		: objLocator - By identification of objElement 
	 *  
	 */
	public boolean checkElementIsPresent(By objLocator) 
	{

		try 
		{
			//objAngularHandle.waitUntilAngularReady();
			//objAngularHandle.waitUntilJQueryReady();
			fluentWaitForPresence(objLocator, Integer.parseInt(objStepBase.getConfig().getProperty("MID_WAIT")));
			WebElement objElement=objStepBase.getDriver().findElement(objLocator);
			return true;
		}
		catch(Exception objException)
		{
			return false;
		}
		
	}
	
	/**
	 * @Method		: JSclick
	 * @Description	: This method clicks on element passed as locator using javascript 
	 * @param		: objLocator - By identification of objElement 
	 *  
	 */
	public boolean JSclick(By objLocator) 
	{
		try
		{
			fluentWaitForPresence(objLocator, Integer.parseInt(objStepBase.getConfig().getProperty("MAX_WAIT")));
			//fluentWaitForClickable(objLocator, Integer.parseInt(objStepBase.getConfig().getProperty("MAX_WAIT")));
			//waitForElementToBeClickable(objLocator);
			WebElement objWebElement = objStepBase.getDriver().findElement(objLocator);
			//objWebElement.click();
			
			((JavascriptExecutor) objStepBase.getDriver()).executeScript("arguments[0].click();",objWebElement);
			return true;
		}
		catch(Exception objException)
		{
			throw objException;
		}
	}

	/**
	 * @Method		: jsScrollAndClick
	 * @Description	: This method scrolls scrollbar till element is displayed and then clicks on element passed as locator using javascript 
	 * @param		: objLocator - By identification of objElement 
	 *  
	 */
	public boolean jsScrollAndClick(By objLocator) 
	{
		try
		{
			//waitForElementPresence(objLocator);
			//waitForElementToBeClickable(objLocator);
			WebElement objWebElement = objStepBase.getDriver().findElement(objLocator);
			((JavascriptExecutor) objStepBase.getDriver()).executeScript("arguments[0].scrollIntoView(true);",objWebElement);
			((JavascriptExecutor) objStepBase.getDriver()).executeScript("arguments[0].click();",objWebElement);
			return true;
		}
		catch(Exception objException)
		{
			throw objException;
		}
	}
	/**
	 * @Method : jsScrollTillDisplayed
	 * @Description : This method scrolls scroll bar till element passed as locator is displayed
	 * @param objLocator
	 * @return
	 *  
	 */
	public boolean jsScrollTillDisplayed(By objLocator) 
	{
		try
		{
			//waitForElementPresence(objLocator);
			//waitForElementToBeClickable(objLocator);
			WebElement objWebElement = objStepBase.getDriver().findElement(objLocator);
			((JavascriptExecutor) objStepBase.getDriver()).executeScript("arguments[0].scrollIntoView(true);",objWebElement);
			//((JavascriptExecutor) objStepBase.getDriver()).executeScript("arguments[0].isDisplayed();",objWebElement);
			return true;
		}
		catch(Exception objException)
		{
			throw objException;
		}
	}
	
	/**
	 * @Method : dragDrop
	 * @Description : This method drags objSource element to objDestination element
	 * @param objLocator
	 * @return
	 *  
	 */
	public boolean dragDrop(By objSource, By objDestination) 
	{
		/*try 
		{
			Robot objRobot;

			objRobot = new Robot();
			WebElement webElement1 = objStepBase.getDriver().findElement(objSource);
			WebElement webElement2 = objStepBase.getDriver().findElement(objDestination);
			Actions objAction = new Actions(objStepBase.getDriver());
			Coordinates c = ((Locatable) webElement1).getCoordinates();
			Coordinates c2 = ((Locatable) webElement2).getCoordinates();
			int x = c.inViewPort().getX();
			int y = c.inViewPort().getY();
			int targetx = c2.inViewPort().getX();
			int targety = c2.inViewPort().getY();
			objRobot.mouseMove(x, y + 120);
			this.waitFor(5000);
			objRobot.mousePress(InputEvent.BUTTON1_MASK);
			this.waitFor(5000);
			objRobot.mouseMove(targetx, targety + 120);
			this.waitFor(5000);
			objRobot.mouseRelease(InputEvent.BUTTON1_MASK);
			this.waitFor(5000);
			return true;
		} 
		catch (AWTException e) 
		{
			// TODO Auto-generated catch block
			//e.printStackTrace();
			return false;
		}*/
		try
		{
			WebElement webElement1 = objStepBase.getDriver().findElement(objSource);
			WebElement webElement2 = objStepBase.getDriver().findElement(objDestination);
			Actions act = new Actions(objStepBase.getDriver());
			//objAction.dragAndDrop(webElement1, webElement2);
			//((JavascriptExecutor)objStepBase.getDriver()).executeScript(JS_DnD, webElement1, webElement2)
			act.clickAndHold(webElement1);
			act.moveToElement(webElement2);
			act.release();
			act.build().perform();
			return true;
			
		}
		catch(Exception objException)
		{
			throw objException;
		}
		
	}

	/** 
	 * @Method : checkWebElementDisplayed
	 * @Description : This method checks that webelement is displayed
	 * @param objWebElement
	 * @returns boolean value based on object displayed
	 *  
	 */
	public boolean checkWebElementDisplayed(WebElement objWebElement)
    {
		//MLAWATE_16Aug2017 - removed wait for element to be clickable line
		try
		{
			return objWebElement.isDisplayed();
		}
		catch(Exception objException)
		{
			throw objException;
		}
    }
	
	/**
	 * @Method : mouseOverWebElement
	 * @Description	: This is wrapper performs mouse over action on webelement
	 * @param		: objWebElement - This is webelement on which mouse over action requires
	 *  
	 */
	public void mouseOverWebElement(WebElement objWebElement) throws NotFoundException
	{
		Actions objAction = new Actions(objStepBase.getDriver());
		objAction.moveToElement(objWebElement).build().perform();
	}

	/**
	* @Method : checkElementIsNotDisplayed
	* @Description     : This is wrapper method to check the web objElement is not dispalyed on the page
	* @param           : objLocator - By identification of objElement
	* @return          : true/false if objElement present 
	*  
	*/
	public boolean checkElementIsNotDisplayed(By objLocator)
	{
		try
		{
			return objStepBase.getDriver().findElement(objLocator).isDisplayed();
		}
		catch(Exception objException)
		{
			return false;
		}
	}

	/**
	 * @Method : checkPageIsReady
	 * @Description : This method checks whether page is ready. Used for scroll logic
	 *  
	 */
	public void checkPageIsReady() 
	{
		try
		{
	        JavascriptExecutor js = (JavascriptExecutor)objStepBase.getDriver();
	        //Initially bellow given if condition will check ready state of page.
	        if (js.executeScript("return document.readyState").toString().equals("complete"))
	        	return; 
	
	        //This loop will rotate for 25 times to check If page Is ready after every 1 second.
	        //You can replace your value with 25 If you wants to Increase or decrease wait time.
	        for (int i=0; i<25; i++)
	        { 
	        	try 
	        	{
	        		this.waitFor(2);
	        	}
	        	catch (Exception e) 
	        	{
	        		
	        	} 
	         //To check page ready state.
	        	if (js.executeScript("return document.readyState").toString().equals("complete"))
	        		break; 
	        }
		}
		catch(Exception objException)
		{
			throw objException;
		}
	}
	
	/**
	 * @Method : fluentWaitForVisibility
	 * @Description : This method fluently waits for given amount of time and checks visibility of locator passed
	 *  
	 */
	public void fluentWaitForVisibility(By objLocator, int iMaxWait)
	{
		try
		{
			Wait<WebDriver> wait = new FluentWait<WebDriver>(objStepBase.getDriver()) 
			.withTimeout(iMaxWait, TimeUnit.SECONDS) 
			.pollingEvery(2, TimeUnit.SECONDS) 
			.ignoring(NoSuchElementException.class, 
			StaleElementReferenceException.class); 
			wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(objLocator));
		}
		catch(Exception objException)
		{
			throw objException;
		}
	}
	
	/**
	 * @Method : fluentWaitForPresence
	 * @Description : This method fluently waits for given amount of time and checks presence of locator passed
	 *  
	 */
	public void fluentWaitForPresence(By objLocator, int iMaxWait)
	{
		int iCounter = 0;
		int iMaxCount = 3;
		while(iCounter < iMaxCount)
		{
			try
			{
				Wait<WebDriver> wait = new FluentWait<WebDriver>(objStepBase.getDriver()) 
				.withTimeout(iMaxWait, TimeUnit.SECONDS) 
				.pollingEvery(2, TimeUnit.SECONDS); 
				wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(objLocator));
				iCounter = iMaxCount;
			}
			catch(StaleElementReferenceException objStaleElementException)
			{
				iCounter++;
				if(iCounter < iMaxCount)
				{
					Wait<WebDriver> wait = new FluentWait<WebDriver>(objStepBase.getDriver()) 
					.withTimeout(iMaxWait, TimeUnit.SECONDS) 
					.pollingEvery(2, TimeUnit.SECONDS);
					wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(objLocator));	
					
				}
				else
					throw objStaleElementException;
			}
			catch(NoSuchElementException objNoSuchElementException)
			{
				iCounter++;
				if(iCounter < iMaxCount)
				{
					Wait<WebDriver> wait = new FluentWait<WebDriver>(objStepBase.getDriver()) 
					.withTimeout(iMaxWait, TimeUnit.SECONDS) 
					.pollingEvery(2, TimeUnit.SECONDS);
					wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(objLocator));
				}
				else
					throw objNoSuchElementException;
			}
			catch(Exception objException)
			{
				iCounter = iMaxCount;
				throw objException;
			}
		}
	}
	
	/**
	 * @Method : fluentWaitForClickable
	 * @Description : This method fluently waits for given amount of time and checks clickability of locator passed
	 *  
	 */
	public void fluentWaitForClickable(By objLocator, int iMaxWait)
	{
		try
		{
			Wait<WebDriver> wait = new FluentWait<WebDriver>(objStepBase.getDriver()) 
			.withTimeout(iMaxWait, TimeUnit.SECONDS) 
			.pollingEvery(2, TimeUnit.SECONDS) 
			.ignoring(NoSuchElementException.class, 
			StaleElementReferenceException.class); 
			wait.until(ExpectedConditions.elementToBeClickable(objLocator));
		}
		catch(Exception objException)
		{
			throw objException;
		}
	}
	
	/**
	* @Method focusOnElement
	* @Description     : This is wrapper method to click on users group option with focus
	* @param           : objLocator - By identification of objElement
	* @return          : - true if objElement present 
	*  
	*/
	public boolean focusOnElement(By objLocator)
	{
		try
		{
			WebElement objWebElement = objStepBase.getDriver().findElement(objLocator);
			waitForWebElementToBeClickable(objWebElement, Integer.parseInt(objStepBase.getConfig().getProperty("MAX_WAIT")), Integer.parseInt(objStepBase.getConfig().getProperty("incrementor")));
			Actions action = new Actions(objStepBase.getDriver());
			action.moveToElement(objWebElement).build().perform();
			//objWebElement.click();
			return true;

		}
		catch(Exception objException)
		{
			throw objException;
		}
		  
	}

	/**
	 * @Method		: checkElementIsPresentWithWaitTime
	 * @Description	: This method checks presence of an objElement passed as a locator 
	 * @param		: objLocator - By identification of objElement 
	 *  
	 */
	public boolean checkElementIsPresentWithWaitTime(By objLocator, int iWait) 
	{

		try 
		{
			//objAngularHandle.waitUntilAngularReady();
			//objAngularHandle.waitUntilJQueryReady();
			fluentWaitForPresence(objLocator, iWait);
			WebElement objElement=objStepBase.getDriver().findElement(objLocator);
			return true;
		}
		catch(Exception objException)
		{
			return false;
		}
		
	}

}