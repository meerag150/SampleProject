package com.generic;

import java.util.Map;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedCondition;



public class AngularHandle {
       
       private StepBase objStepBase;
       public AngularHandle(StepBase objStepBase){
              //super();
              this.objStepBase = objStepBase;
       }

       private JavascriptExecutor jsExecutor;
       //Wait Until Angular and JS Ready
    public void waitUntilAngularReady() {
       jsExecutor = (JavascriptExecutor) objStepBase.getDriver();
    //First check that ANGULAR is defined on the page. If it is, then wait ANGULAR
        Boolean angularUnDefined = (Boolean) jsExecutor.executeScript("return window.angular === undefined");
         if (!angularUnDefined) {
          Map<Object,Object> angulardefinded  = (Map<Object, Object>)jsExecutor.executeScript("return window.angular");
             Boolean angularInjectorUnDefined = (Boolean) jsExecutor.executeScript("return angular.element(document).injector() === undefined");
            if(!angularInjectorUnDefined) {
            Map<Object,Object>  angularinjectordefinded =(Map<Object, Object>) jsExecutor.executeScript("return angular.element(document).injector()");
                //Wait Angular Load
                waitForAngularLoad();
                //Wait JS Load
                waitUntilJSReady();
              } else {
                System.out.println("Angular injector is not defined on this site!");
            }
        }  else {
            System.out.println("Angular is not defined on this site!");
        }
    }
    
    //Wait Until JS Ready
    public  void waitUntilJSReady() {
     jsExecutor = (JavascriptExecutor) objStepBase.getDriver();
         //Wait for Javascript to load
        ExpectedCondition<Boolean> jsLoad = driver -> ((JavascriptExecutor) objStepBase.getDriver())
                .executeScript("return document.readyState").toString().equals("complete");
        //Get JS is Ready
        boolean jsReady =  (Boolean) jsExecutor.executeScript("return document.readyState").toString().equals("complete");
        //Wait Javascript until it is Ready!
        if(!jsReady) {
            System.out.println("JS in NOT Ready!");
            objStepBase.getDriverWait().until(jsLoad);
        } else {
           // System.out.println("JS is Ready!");
        }
    }

    //Wait for Angular Load
    public void waitForAngularLoad() {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) objStepBase.getDriver();
        String angularReadyScript = "return angular.element(document).injector().get('$http').pendingRequests.length === 0";
        //Wait for ANGULAR to load
        ExpectedCondition<Boolean> angularLoad = driver -> Boolean.valueOf(((JavascriptExecutor) driver)
                .executeScript(angularReadyScript).toString());
       //Get Angular is Ready
        boolean angularReady = Boolean.valueOf(jsExecutor.executeScript(angularReadyScript).toString());
       //Wait ANGULAR until it is Ready!
        if(!angularReady) {
            System.out.println("ANGULAR is NOT Ready!");
            //Wait for Angular to load
            int counter=0;
            do {
            objStepBase.getDriverWait().until(angularLoad);
            angularReady = Boolean.valueOf(jsExecutor.executeScript(angularReadyScript).toString());
            if(angularReady) 
             break;
            
            counter++;
            }while(counter<5);
            if(!angularReady) {
            System.out.println("ANGULAR is NOT Ready after counter=" + counter);
            }
            else {
            System.out.println("ANGULAR is Ready after counter=" + counter);
            }
        } else {
            //System.out.println("ANGULAR is Ready!");
        }
    }
   
    //Wait Until JQuery and JS Ready
    public void waitUntilJQueryReady() {
     jsExecutor = (JavascriptExecutor) objStepBase.getDriver();
    //First check that JQuery is defined on the page. If it is, then wait AJAX
        Boolean jQueryDefined = (Boolean) jsExecutor.executeScript("return typeof jQuery != 'undefined'");
        if (jQueryDefined == true) {
              //Wait JQuery Load
            waitForJQueryLoad();
            waitUntilJSReady();
       }  else {
            System.out.println("jQuery is not defined on this site!");
        }
    }
    
    //Wait for JQuery Load
    public void waitForJQueryLoad() {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) objStepBase.getDriver();
        ExpectedCondition<Boolean> jQueryLoad = driver -> ((Long)jsExecutor.executeScript("return jQuery.active") == 0);
        //Get JQuery is Ready
        boolean jqueryReady = (Boolean) jsExecutor.executeScript("return jQuery.active==0");
        //Wait JQuery until it is Ready!
        if(!jqueryReady) {
            System.out.println("JQuery is NOT Ready!");
            //Wait for jQuery to load
            objStepBase.getDriverWait().until(jQueryLoad);
        } else {
           // System.out.println("JQuery is Ready!");
        }
    }

}

