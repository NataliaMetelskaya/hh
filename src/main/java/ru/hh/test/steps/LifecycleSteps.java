package ru.hh.test.steps;

import org.jbehave.core.annotations.*;
import org.jbehave.web.selenium.DelegatingWebDriverProvider.DelegateWebDriverNotFound;
import org.jbehave.web.selenium.WebDriverProvider;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ru.hh.test.ui.price.Pages;

import java.awt.*;

/**
 * @author dzianis_shkindzerau
 */
public class LifecycleSteps {

	private static final Logger LOG = LoggerFactory.getLogger(LifecycleSteps.class);
	
    private final WebDriverProvider webDriverProvider;
    
    public LifecycleSteps(WebDriverProvider webDriverProvider) {
        this.webDriverProvider = webDriverProvider;
    }

    @Autowired
    private Pages pages;

    @BeforeStory
    public void beforeStory(@Named("testType") String testType) {

        pages.resetPages();

    	LOG.info("TestType: " + testType);
    	
    	WebDriver webDriver = null;
    	// init ui tests
    	if("ui".equalsIgnoreCase(testType)) {
    		
    		webDriverProvider.initialize();
    		
    		// setup web driver
    		try {
    			webDriver = webDriverProvider.get();
    		} catch (DelegateWebDriverNotFound e) {
    	    	LOG.error("WebDriver was not got from the WebDriverProvider. \n", e);
    		}
    	    try {   
            	webDriver.manage().deleteAllCookies();
            } catch (WebDriverException e) {
            	LOG.error("Error deleting Cokies. \n", e);
            }    
    	    
			// Browsers full screen mode
    	    LOG.info("Browser: [" + webDriver + "] full screen mode started....");
    	    webDriver.manage().window().maximize();
			LOG.info("Browser: [" + webDriver + "] was opened in full screen mode.");
/*    	    if(webDriver instanceof EventFiringWebDriver) {	
    	    }
    	    if(webDriver instanceof RemoteWebDriver) {   	    	
    	    }
			if(webDriver instanceof FirefoxDriver || webDriver instanceof InternetExplorerDriver) {
				webDriver.manage().window().maximize();
				LOG.info("Browser: [" + webDriver + "] was opened in full screen mode.");
			}
			if(webDriver instanceof ChromeDriver) {
				webDriver.manage().window().setSize(getDimension());
				LOG.info("Browser: [" + webDriver + "] window resolution sets to: [" + getDimension().width + "x" + getDimension().height + "] size.");
			}*/
    	}

    	LOG.info("Preparing WebDriver: [" + webDriver + "] was ended!");
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
    		LOG.info("WebDriver is closing.");
    	}
    	
    	// init service tests
    	if("service".equalsIgnoreCase(testType)) {    		
    	}	
    }
    
    @BeforeStories
    public void beforeStories() {
    	LOG.info("===============================================================================================================================");
    	LOG.info("===============================================================================================================================");
    	LOG.info("Startup properties: ");
    	LOG.info("	- Browser type: [" + System.getProperty("browser", "firefox").toUpperCase() + "]");
    	LOG.info("===============================================================================================================================");
    	LOG.info("===============================================================================================================================");
    }
    
    @AfterStories
    public void afterStories() {
    	LOG.info("===============================================================================================================================");
    	LOG.info("===============================================================================================================================");
    	LOG.info("End. ");
    	LOG.info("===============================================================================================================================");
    	LOG.info("===============================================================================================================================");
    }
}
