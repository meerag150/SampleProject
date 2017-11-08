package com.generic;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.RollingFileAppender;
import org.apache.log4j.SimpleLayout;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;

import cucumber.api.Scenario;

/**
 * @ScriptName    : StepBase
 * @Description   : This class contains generic functionalities like setup/teardown test environment  
 *  
 */
public class StepBase 
{
	// Local Variables
	WebDriver webDriver = null;
	WebDriverWait webDriverWait;
	Scenario objScenario1; 
	Properties objConfig;
    String sScenarioName;
    Format objDateTimeFormatter = new SimpleDateFormat("YYYY-MM-dd_hh-mm-ss");
	Format objDateFormatter = new SimpleDateFormat("YYYY-MM-dd");
    String imagePath;
    //String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Timestamp(0));
    Logger logger = Logger.getLogger(StepBase.class);
    Date objDate = new Date();
    public static String sLineSeparator  = System.getProperty("line.separator");
    public static SimpleDateFormat objTimeFormatter = new SimpleDateFormat("hh:mm:ss.SSS");
    public long lStartTime = System.currentTimeMillis();
    public long lEndTime = System.currentTimeMillis();
   
	By btnLogout = By.cssSelector("button#LogoutCommand");
	public StepBase()
	{
		
	}
	
	/**
	 * @Method : createLogFile
	 * @Description : This method creates log file at specified path
	 *  
	 * @throws IOException 
	 */
	public void createLogFile(Scenario objScenario)
	{
		//RPHADKE_06Sept2017 - Added code to create date folder for logs if it does not exist
		try
		{
			/*String sDate = objDateFormatter.format(objDate);
			String sFolderPath = "\\src\\test\\java\\testResult\\log4jReports\\" + sDate;
			File objDateFolder = new File(sFolderPath);
			if(!objDateFolder.exists())
				objDateFolder.mkdir();*/
			String sFolderPath = System.getProperty("user.dir") + "/build/reports/log4jReports";
			File objDateFolder = new File(sFolderPath);
			if(!objDateFolder.exists())
				objDateFolder.mkdir();
			sScenarioName = getFeatureFileNameFromScenarioId(objScenario);
			sScenarioName = sScenarioName.replace(" ", "_");
	
			sScenarioName = sScenarioName + "_" + objDateTimeFormatter.format(objDate);
	
			// sScenarioName.replaceAll("\\s+", "");
			
			SimpleLayout layout = new SimpleLayout();
			RollingFileAppender appender = new RollingFileAppender(layout,
					//System.getenv("AutomationDir") + "\\src\\test\\java\\testResult\\log4jReports" + "\\" + sScenarioName + ".log", false);
					//System.getenv("AutomationDir") + sFolderPath + "\\" + sScenarioName + ".log", false);
					sFolderPath + "/" + sScenarioName + ".log", false);
			appender.setMaxFileSize("500MB");
			logger.addAppender(appender);
		}
		catch(Exception exception)
		{
			exception.printStackTrace();
		}
	}

	/**
	 * @Method: setUp
	 *  
	 * @throws IOException 
	 */
	public void initializeWebEnvironment(Scenario objScenario)
	{
		objScenario1 = objScenario;
		this.createLogFile(objScenario);
		this.loadConfigProperties();
		String sScenarioName1 =getFeatureFileNameFromScenarioId(objScenario);
		String sLineSeparator = System.getProperty("line.separator");
		
        
		logger.info("Setup Details :" + sLineSeparator +"TeamCenter Release" + "	-	"+ " Active Workspace Release 3.2.2" +sLineSeparator+ "------------------------------------------------------");
		
		logger.info("Test Case Information:" +sLineSeparator + "SCENARIO NAME      " +"	-	"+ sScenarioName1 + sLineSeparator + "------------------------------------------------------"+ sLineSeparator+"Start" + "[" +objTimeFormatter.format(lStartTime) +"]" + sLineSeparator +"------------------------------------------------------" + sLineSeparator);

		logger.info(""+sScenarioName1);

		try 
		{
			String sBrowser = objConfig.getProperty("web.browser").trim().toLowerCase();
			switch (sBrowser)
			{
			case "ie": // If specified sBrowser is Internet Explorer
				System.setProperty("webdriver.ie.driver", System.getProperty("user.dir") + objConfig.getProperty("webdriver.ie.driver").trim());
				webDriver = new InternetExplorerDriver();
				break;

			case "firefox": // If specified sBrowser is Firefox 
				webDriver = new FirefoxDriver();
				break;

			case "chrome": // If specified sBrowser is Chrome 
				System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + objConfig.getProperty("webdriver.chrome.driver").trim());
				//Code to take desired capabilities of chrome
				ChromeOptions objChromeOption = new ChromeOptions();
				objChromeOption.setBinary(System.getProperty("user.dir") + "/Chrome_Local/Application/chrome.exe");
				DesiredCapabilities objDesiredCapabilities = new DesiredCapabilities();
				objDesiredCapabilities.setCapability(ChromeOptions.CAPABILITY, objChromeOption);
				webDriver = new ChromeDriver(objDesiredCapabilities);
				break;

			default:
				webDriver = new FirefoxDriver();
			}

			// Maximize sBrowser
			webDriver.manage().window().maximize();
			webDriver.manage().timeouts().implicitlyWait(Integer.parseInt(objConfig.getProperty("driver.implicitlyWait")),TimeUnit.SECONDS);
			webDriver.manage().timeouts().pageLoadTimeout(Integer.parseInt(objConfig.getProperty("driver.pageLoadTimeout")), TimeUnit.SECONDS);

			webDriverWait = new WebDriverWait(webDriver, Integer.parseInt(objConfig.getProperty("driver.WebDriverWait").trim()));
			this.loadObjectRepository();
		}
		catch(Exception exception)
		{
			exception.printStackTrace();	
		}

	}

	/**
	 * @Method		: 	loadConfigProperties
	 * @Description	: 	load config properties 
	 * @author		:	(SQS)
	 */
	public void loadConfigProperties()
	{
		try
		{
			objConfig = new Properties();
			objConfig.load(new FileInputStream(System.getProperty("user.dir") + "/src/test/resources/config.properties"));
		}
		catch(Exception exception)
		{
			exception.printStackTrace();
		}
	}

	/**
	 * @Method		: tearDownEnvironment
	 * @Description	: quit webdriver  
	 * @author		: (SQS) 
	 * @throws InterruptedException 
	 */
	public void tearDownWebEnvironment(Scenario objScenario) throws InterruptedException
	{	
		try 
		{ 
			lEndTime = System.currentTimeMillis();

			long difference = lEndTime - lStartTime;
			String sTotalExecutionTime = String.format("%02d min, %02d sec", 
				    TimeUnit.MILLISECONDS.toMinutes(difference),
				    TimeUnit.MILLISECONDS.toSeconds(difference) - 
				    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(difference))
				);

			if (objScenario.isFailed()) 
			{
				this.embedScreenshot();
				webDriver.findElement(btnLogout).click();
				logger.info(sLineSeparator + "------------------------------------------------------"+ sLineSeparator+"End" + "[" +objTimeFormatter.format(lEndTime) +"]" + sLineSeparator + "------------------------------------------------------"+sLineSeparator+"		Result"+ "[FAIL]"  + sLineSeparator+ "------------------------------------------------------"+sLineSeparator + "Total Execution Time" +"[" +sTotalExecutionTime +"]" + sLineSeparator+"------------------------------------------------------" + sLineSeparator);
			}
			else
				logger.info(sLineSeparator + "------------------------------------------------------"+ sLineSeparator+"End" + "[" +objTimeFormatter.format(lEndTime) +"]" + sLineSeparator + "------------------------------------------------------"+sLineSeparator+"		Result"+ "[PASS]"  + sLineSeparator+ "------------------------------------------------------"+sLineSeparator + "Total Execution Time" +"[" +sTotalExecutionTime +"]" + sLineSeparator+"------------------------------------------------------" + sLineSeparator);
			logger.removeAllAppenders();
			webDriver.quit();
		}
		catch (Exception exception)
		{
			exception.printStackTrace();
			this.killBrowserAndDriver(objConfig);
			//if(System.getProperty("web.sBrowser").trim().equalsIgnoreCase("IE") || System.getProperty("web.sBrowser").trim().equalsIgnoreCase("Chrome"))
				//this.killBrowserAndDriver(objConfig);
		}
		finally
		{
			webDriver = null;
		}
	}
	
	/**
	 * @Method		: killBrowserAndDriver
	 * @Description	: this method close the web sBrowser    
	 * @author		: (SQS)  
	 */
	protected void killBrowserAndDriver(Properties config) 
	{
		String sBrowser = objConfig.getProperty("web.browser").trim();
		String sBrowserProcess = "";
		String driverProcess = ""; 

		if (!sBrowser.equals("") && sBrowser.equalsIgnoreCase("IE")) 
		{
			sBrowserProcess = "iexplore";
			driverProcess = "IEDriverServer.exe";
		}
		else if (!sBrowser.equals("") && sBrowser.equalsIgnoreCase("Chrome")) 
		{
			sBrowserProcess = sBrowser;
			driverProcess = "chromedriver.exe";
		}
		try 
		{
			Process procDriver = Runtime.getRuntime().exec("taskkill /F /T /IM "+ driverProcess);
			Process procIE = Runtime.getRuntime().exec("taskkill /F /T /IM "+ sBrowserProcess + ".exe");
			procDriver.waitFor();
			procIE.waitFor();
		}
		catch (Exception exception) 
		{
			exception.printStackTrace();
		}
	}

	/** returns WebDriver instance */
	public WebDriver getDriver()
	{
		return webDriver;
	}

	/** returns WebDriverWait instance */
	public WebDriverWait getDriverWait()
	{
		return webDriverWait;
	}

	/** returns WebDriverWait instance */
	public Properties getConfig()
	{
		return objConfig;
	}

	/** attach screenshot to Scenario */
	public void embedScreenshot()
	{
		final byte[] screenshot = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.BYTES);
		objScenario1.embed(screenshot, "image/png"); //stick it in the report
	}
	
	/**
	 * @Method : getFeatureFileNameFromScenarioId
	 * @Description : This method returns feature file name from scenario passed
	 * @param objScenario
	 * @return
	 *  
	 */
	private String getFeatureFileNameFromScenarioId(Scenario objScenario) 
	{
	    String featureName = "Feature ";
	    String rawFeatureName = objScenario.getId().split(";")[0].replace("-"," ");
	    featureName = featureName + rawFeatureName.substring(0, 1).toUpperCase() + rawFeatureName.substring(1);

	    return featureName.trim();

	}
	
	/**
	 * @Method:loadObjectRepository
	 * @Description: This method loads Object repositoty
	 *  
	 */
	public void loadObjectRepository()
	{
		/*String sObjectRepositoryFileName = System.getProperty("user.dir") + "/src/test/resources/ObjectRepository/"+"ObjectRepository.xml";
		objReposiroryMap = this.getPageObjectRepository(sObjectRepositoryFileName);
		if(objReposiroryMap == null)
		{
			this.logger.error("Object Repository is null");
		}*/
		String sObjectRepositoryFilesPath = System.getProperty("user.dir") + "/src/test/resources/ObjectRepository/";
		File folder = new File(sObjectRepositoryFilesPath);
		ArrayList<File> fObjectRepofiles = new ArrayList<File>(Arrays.asList(folder.listFiles()));
		
		//return objReposiroryMap;
	}
	
	
	/**
	 * @Method:getPageObjectRepository
	 * @Description:Used to get Object repository
	 * @param sFileName: Object repository file name
	 * @return Map<String,List<Controls>>: Map with key as PageName and value as controlsList
	 *  
	 */
	/*public Map<String,List<Controls>> getPageObjectRepository(String sFileName) 
	{
		
		try {
			objControlsList = new ArrayList<Controls>();
			Document doc = null;
     
			  try{
				    File file = new File(sFileName);
				    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
				    DocumentBuilder db = dbf.newDocumentBuilder();
				    doc = db.parse(file);
				    doc.getDocumentElement().normalize();
				    //System.out.println("The node name is: "+doc.getDocumentElement().getNodeName());   

				    NodeList objPageNodeList=doc.getElementsByTagName("PageObject");
				    //System.out.println("The length is: "+nList.getLength());
				    for(int i=0; i<objPageNodeList.getLength(); i++){
				        Node objPageNode = objPageNodeList.item(i);
				        
				        if(objPageNode.getNodeType()==Node.ELEMENT_NODE){
				          Element pageElement=(Element) objPageNode;
				         // System.out.println("NODE: " +pageElement.getNodeName());
				          NodeList pageObjectNode = pageElement.getChildNodes();
				          System.out.println("pageNode: " +pageObjectNode.getLength());
				          for(int j=i; j<pageObjectNode.getLength(); j++)
				          {
				        	  Node objPageNameNode = pageObjectNode.item(j);
				        	  if(objPageNameNode.getNodeType()==Node.ELEMENT_NODE){
						          Element pageNameElement = (Element) objPageNameNode;
						         // System.out.println("pageNode name: " +pageNameElement.getNodeName());
						         
						        	  String sElementName = pageNameElement.getNodeName();
						        	  if(pageNameElement.getNodeName().equals(sElementName))
						        	  {
						        		  NodeList controlList = pageNameElement.getElementsByTagName("Control");
								        	 //Controls objControl = new Controls();
								        	 objControlsList = new ArrayList<Controls>();
								        	 for(int iControl=0; iControl<controlList.getLength(); iControl++)
								        	 {
								        		 String sName = null;
								        		 	Controls objControl = new Controls();
											        Node controlNode = controlList.item(iControl);
											        if(controlNode.getNodeType()==Node.ELEMENT_NODE)
											        {
											          Element eleControl = (Element) controlNode;
											         // System.out.println("XML: "+doc.getElementsByTagName(sElementName).item(i).getChildNodes().getLength());
											          //System.out.println("Control NODE: " +controlNode.toString());
											          
											         NamedNodeMap attributeMap = controlNode.getAttributes();
											         //System.out.println("attributeMap: " + attributeMap.getLength());
											         //Node sItem = attributeMap.item(1);
											         //String sNodeName = sItem.getNodeName();
											         //System.out.println("sNodeName: " + sNodeName);
											          //String sName = attributeMap.getNamedItem("name").getNodeValue();
											         sName = attributeMap.getNamedItem("name").getNodeValue();
											         //System.out.println("sName: " +sName);
											         objControl.setName(sName);
											         //objControl.setLocatorType(attributeMap.item(0).getNodeName());
											      if(attributeMap.item(i).getNodeName().equals("cssSelector"))
												    {
												    	  
											    	  objControl.setCssSelector(sItem.getNodeValue());
												    }
											      else if(attributeMap.item(i).getNodeName().equals("xpath"))
											      {
											    	  objControl.setXpath(sItem.getNodeValue());
											      }
											      else if(attributeMap.item(i).getNodeName().equals("String"))
											      {
											    	  
											      }
											         for(int iControlAtt=0; iControlAtt<attributeMap.getLength(); iControlAtt++)
											         {
											        	 
											        	 switch (attributeMap.item(iControlAtt).getNodeName())
												         {
															case "cssSelector":
																objControl.setCssSelector(attributeMap.item(iControlAtt).getNodeValue());
																objControl.setLocatorType(attributeMap.item(iControlAtt).getNodeName());
																
																break;
															case "xpath":
																objControl.setXpath(attributeMap.item(iControlAtt).getNodeValue());
																objControl.setLocatorType(attributeMap.item(iControlAtt).getNodeName());											
																break;
															case "id":
																objControl.setId(attributeMap.item(iControlAtt).getNodeValue());
																objControl.setLocatorType(attributeMap.item(iControlAtt).getNodeName());
																break;
															case "type": 
															{
																objControl.setType(attributeMap.item(iControlAtt).getNodeValue());														
																
															}
															case "sPattern":
															{
																objControl.setsPattern(attributeMap.getNamedItem("sPattern").getNodeValue());
															}
																
															default:
																break;
															}
											         }
											         objControlsList.add(objControl);
											         
											   }
											        //objControlsList.add(objControl);
											        objReposiroryMap.put(sElementName, objControlsList);
											       
											        }
								        	 //objReposiroryMap.put(sElementName, objControlsList);
						        	  }    	 
						         				          
				        	  }
				        	 
				          }
				          }
				       
				    }

				    }catch(Exception e){
				        e.printStackTrace();
				    }
			return objReposiroryMap;
		} 
		catch (Exception e)
		{
			throw e;
			
		}
}*/
	
	 public void getscreenshot() throws Exception 
     {
           File scrFile = ((TakesScreenshot)webDriver).getScreenshotAs(OutputType.FILE);
           imagePath = System.getProperty("user.dir")+objConfig.getProperty("screenshot.dir")+"/screenshot.png";
          
          // File destFile = new File(objConfig.getProperty("screenshot.dir")+"/screenshot.jpg");
           File destFile = new File(imagePath);
        		   //new File (objConfig.getProperty("screenshot.dir")+"/screenshot.png");
           //The below method will save the screen shot in d drive with name "screenshot.png"
           FileUtils.copyFile(scrFile,destFile);
     }	
	 
	 /** returns WebDriverWait instance */
	public Logger getLog4jLogger()
	{
		return logger;
	}
}
