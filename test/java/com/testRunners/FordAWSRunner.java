package com.testRunners;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(
		//dryRun = true,
		strict = true,
		features = {"src/test/resources/features"},
		glue = {"com.hooks", "com.stepdefinition"},
		//glue = {"com.stepdefinition"},
		tags = {"@gmailtest"},
				//tags = {"@LoginTest","@101.10.510_Creating_PMEI_Connections_Through_Recipe"},
				
				//tags={"@LoginTest"},
						//tags = {"@SampleTest"},
			
		monochrome = true,
		plugin = {
				"pretty",
				"html:src/test/java/testResult/cucumber-report",
				"junit:src/test/java/testResult/cucumber-report/cucumber.xml",
				"json:src/test/java/testResult/cucumber-report/cucumber.json"
		}
		)
/**
 * @ScriptName    : TestRunner
 * @Description   : This class contains   
 *  
 */
public class FordAWSRunner
{ 	
	
}