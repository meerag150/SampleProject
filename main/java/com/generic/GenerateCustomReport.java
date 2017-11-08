package com.generic;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import net.masterthought.cucumber.ReportBuilder;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Test;

/**
 * @ScriptName    : GenerateCustomeReport
 * @Description   : This class generate test execution reports using net.masterthought 
 *  
 */
public class GenerateCustomReport
{
	// Local variables
	private String reportPath;
	private String jsonFilePath;
	private String xmlFilePath;
	private String buildNumber;
	private String buildProjectName;
	private String pluginURLPath;
	private Boolean skippedFails;
	private Boolean undefinedFails;
	private Boolean flashCharts;
	private Boolean runWithJenkins;
	private Boolean artificatsEnabled;
	private String artifactsConfig;
	private boolean highCharts;
	private DateTimeFormatter dateFormat;
	private Utilities objUtilities = new Utilities(); 

	@Test
	public void generateCustomeReport()
	{
		try
		{
			dateFormat = DateTimeFormat.forPattern("yyyy-MM-dd_HHmmss");
			String currentTimeStamp = dateFormat.print(new DateTime());
			reportPath = System.getProperty("user.dir") + "/src/test/java/testResult/custom-report/cucumber-html-reports_" + currentTimeStamp;
			jsonFilePath = System.getProperty("user.dir") + "/src/test/java/testResult/cucumber-report/cucumber.json";
			xmlFilePath = System.getProperty("user.dir") + "/src/test/java/testResult/cucumber-report/cucumber.xml";

			List<String> jsonReportFiles = new ArrayList<String>();
			jsonReportFiles.add(jsonFilePath);

			buildNumber = "1";
			buildProjectName = "Mobile Automation";
			pluginURLPath = "";
			skippedFails = true;
			undefinedFails = true;
			flashCharts = true;
			runWithJenkins = false;
			artificatsEnabled = false;
			artifactsConfig = "";
			highCharts = true;

			ReportBuilder reportBuilder = new ReportBuilder(jsonReportFiles,
					new File(reportPath), pluginURLPath, buildNumber,
					buildProjectName, skippedFails, undefinedFails, flashCharts,
					runWithJenkins, artificatsEnabled, artifactsConfig, highCharts);
			reportBuilder.generateReports();

			objUtilities.copyFileUsingStream(jsonFilePath, reportPath + "/cucumber.json");
			objUtilities.copyFileUsingStream(xmlFilePath, reportPath + "/cucumber.xml");
		}
		catch (Exception exception)
		{
			exception.printStackTrace();
		}
	}
}
