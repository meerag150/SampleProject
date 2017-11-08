package com.hooks;
import java.util.HashMap;
import java.util.Map;

import com.generic.ItemDetails;
import com.generic.StepBase;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;

/**
 * @ScriptName    : Hooks
 * @Description   : This class contains   
 *  
 */
public class Hooks 
{
	public Scenario currentScenario;
	//StepBase objStepBase = new StepBase();
	public static Map<String, ItemDetails> itemMap;
	//StepBase objStepBase = new StepBase();
	private ItemDetails objItemDetails;
	private StepBase objStepBase;
	public Hooks(StepBase objStepBase, ItemDetails itemDetails)
	{
		//currentScenario = scenario;
		this.objStepBase = objStepBase;
		this.objItemDetails = itemDetails;
	}
	@Before
	public void applyHook(Scenario scenario)
	{
		//currentScenario = scenario;
		//objStepBase = new StepBase(currentScenario);
		//objItemDetails = new ItemDetails();
		itemMap = new HashMap<String, ItemDetails>();
		currentScenario = scenario;
		this.objStepBase.initializeWebEnvironment(currentScenario);
	}

	@After
	public void removeHook(Scenario scenario) throws InterruptedException
	{
		this.objStepBase.tearDownWebEnvironment(scenario);
	}	
}
