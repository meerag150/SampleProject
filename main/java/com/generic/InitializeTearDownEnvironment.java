package com.generic;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

public class InitializeTearDownEnvironment
{
	private WebDriver driver;

	/**
	 * @Method		: 	setUpDesktopEnvironment
	 * @Description	:	This method initialize web driver for web application 
	 * 					by opening the browser and URL specified in config.properties file
	 * @author		: 	Harshvardhan Yadav (SQS)
	 */
	public WebDriver initializeWebEnvironment(Properties config)
	{
		try 
		{
			String browser = config.getProperty("web.browser").trim().toLowerCase();
			switch (browser)
			{
			case "ie": // If specified browser is Internet Explorer
				DesiredCapabilities caps = DesiredCapabilities.internetExplorer(); caps.setCapability("ignoreZoomSetting", true);
				caps.setCapability("nativeEvents",false);
				//WebDriver driver = new  InternetExplorerDriver(caps);

				System.setProperty("webdriver.ie.driver", System.getProperty("user.dir") + config.getProperty("webdriver.ie.driver").trim());
				driver = new InternetExplorerDriver(caps);
				break;

			case "firefox": // If specified browser is Firefox 
				driver = new FirefoxDriver();
				break;

			case "chrome": // If specified browser is Chrome 
				System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + config.getProperty("webdriver.chrome.driver").trim());
				driver = new ChromeDriver();
				break;

			default:
				driver = new FirefoxDriver();
			}

			// Maximize browser
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(Integer.parseInt(config.getProperty("driver.implicitlyWait")),TimeUnit.SECONDS);
			driver.manage().timeouts().pageLoadTimeout(Integer.parseInt(config.getProperty("driver.pageLoadTimeout")), TimeUnit.SECONDS);

			driver.get(config.getProperty("web.Url"));
			return driver;
		}
		catch (Exception exception) 
		{
			exception.printStackTrace();
			return null;
		}
	}

	/**
	 * @Method		: tearDownWebEnvironment
	 * @Description	: quit webdriver  
	 * @author		: Harshvardhan Yadav (SQS) 
	 */
	public void tearDownWebEnvironment(Properties config, WebDriver driver)
	{
	    try
		{ 
			driver.quit();
		}
		catch (Exception exception)
		{
			exception.printStackTrace();
			if(System.getProperty("web.browser").trim().equalsIgnoreCase("IE") || System.getProperty("web.browser").trim().equalsIgnoreCase("Chrome"))
				killBrowserAndDriver(config);
		}
	}
	/**
	 * @Method		: killBrowserAndDriver
	 * @Description	: this method close the web browser    
	 * @author		: Harshvardhan Yadav (SQS)  
	 */
	protected void killBrowserAndDriver(Properties config) 
	{
		String browser = System.getProperty("web.browser").trim();
		String browserProcess = "";
		String driverProcess = ""; 

		if (!browser.equals("") && browser.equalsIgnoreCase("IE")) 
		{
			browserProcess = "iexplore";
			driverProcess = "IEDriverServer.exe";
		}
		else if (!browser.equals("") && browser.equalsIgnoreCase("Chrome")) 
		{
			browserProcess = browser;
			driverProcess = "chromedriver.exe";
		}
		try 
		{
			Process procDriver = Runtime.getRuntime().exec("taskkill /F /T /IM "+ driverProcess);
			Process procIE = Runtime.getRuntime().exec("taskkill /F /T /IM "+ browserProcess + ".exe");
			procDriver.waitFor();
			procIE.waitFor();
		}
		catch (Exception exception) 
		{
			exception.printStackTrace();
		}
	}
}
