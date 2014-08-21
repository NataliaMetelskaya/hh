package ru.hh.price.steps;

import org.jbehave.core.annotations.*;
import org.jbehave.web.selenium.DelegatingWebDriverProvider.DelegateWebDriverNotFound;
import org.jbehave.web.selenium.WebDriverProvider;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;

/**
 * 
 * @author Dzianis_Shkindzirau
 * 
 */
public class LifecycleSteps {

	private static Logger log = LoggerFactory.getLogger(LifecycleSteps.class);
	
    private final WebDriverProvider webDriverProvider;
    
    public LifecycleSteps(WebDriverProvider webDriverProvider) {
        this.webDriverProvider = webDriverProvider;
    }

    @BeforeStory
    public void beforeStory(@Named("testType") String testType) {
    	
    	log.info("TestType: " + testType);
    	
    	WebDriver webDriver = null;
    	// init ui tests
    	if("ui".equalsIgnoreCase(testType)) {
    		
    		webDriverProvider.initialize();
    		
    		// setup web driver
    		try {
    			webDriver = webDriverProvider.get();
    		} catch (DelegateWebDriverNotFound e) {
    	    	log.error("WebDriver was not got from the WebDriverProvider. \n", e);
    		}
    	    try {   
            	webDriver.manage().deleteAllCookies();
            } catch (WebDriverException e) {
            	log.error("Error deleting Cokies. \n", e);
            }    
    	    
			// Browsers full screen mode
    	    log.info("Browser: [" + webDriver + "] full screen mode started....");
    	    webDriver.manage().window().maximize();
			log.info("Browser: [" + webDriver + "] was opened in full screen mode.");
/*    	    if(webDriver instanceof EventFiringWebDriver) {	
    	    }
    	    if(webDriver instanceof RemoteWebDriver) {   	    	
    	    }
			if(webDriver instanceof FirefoxDriver || webDriver instanceof InternetExplorerDriver) {
				webDriver.manage().window().maximize();
				log.info("Browser: [" + webDriver + "] was opened in full screen mode.");
			}
			if(webDriver instanceof ChromeDriver) {
				webDriver.manage().window().setSize(getDimension());
				log.info("Browser: [" + webDriver + "] window resolution sets to: [" + getDimension().width + "x" + getDimension().height + "] size.");
			}*/
    	}
    	
    	// init service tests
    	if("service".equalsIgnoreCase(testType)) {    		
    	}
    	log.info("Preparing WebDriver: [" + webDriver + "] was ended!");
    }
    
	private Dimension getDimension() {
		Dimension dimension = new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height);
		return dimension;
	}
    
    @AfterStory
    public void afterStory(@Named("testType") String testType) {
    	
    	// init ui tests
    	if("ui".equalsIgnoreCase(testType)) {
    		webDriverProvider.end();
    		log.info("WebDriver is closing.");
    	}
    	
    	// init service tests
    	if("service".equalsIgnoreCase(testType)) {    		
    	}	
    }
    
    @BeforeStories
    public void beforeStories() {
    	log.info("===============================================================================================================================");
    	log.info("===============================================================================================================================");
    	log.info("Startup properties: ");
    	//log.info("	- Instance base url: [" + Config.getValue("url.base") + "]");
    	//log.info("	- Package stories: [" + Config.getValue("run.story.path") + "]");
    	log.info("	- Browser type: [" + System.getProperty("browser", "firefox").toUpperCase() + "]");
    	log.info("===============================================================================================================================");
    	log.info("===============================================================================================================================");
    }
    
    @AfterStories
    public void afterStories() {
    	log.info("===============================================================================================================================");
    	log.info("===============================================================================================================================");
    	log.info("End. ");
    	log.info("===============================================================================================================================");
    	log.info("===============================================================================================================================");
    }
}
