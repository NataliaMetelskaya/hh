package ru.hh.test.driver;

import org.apache.commons.io.FileUtils;
import org.jbehave.web.selenium.DelegatingWebDriverProvider;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

import static java.lang.Boolean.parseBoolean;

/**
 * 
 * @author Dzianis_Shkindzirau
 * 
 */
public class CustomWebDriverProvider extends DelegatingWebDriverProvider {
	
	private static Logger log = LoggerFactory.getLogger(CustomWebDriverProvider.class);
	
	private SupportedBrowser browser;
	
	public enum SupportedBrowser {
		 CHROME, FIREFOX
    }

	public synchronized String getBrowserName() {
		return browser.name();
	}
	
	@Override
	public  WebDriver get() {
		log.info("Reseiving WebDriver: [" + browser + "] started................");
		WebDriver wd = super.get();
		log.info("WebDriver: [" + wd + "] got.");
		return wd;
	}
	
	@Override
	public  void end() {
		log.info("Closing WebDriver: [" + browser + "] started................");
		super.end();
		log.info("WebDriver: [" + browser + "] closed.");
	}
	
	@Override
	public  boolean saveScreenshotTo(String path) {
    	log.info("Start save screenshoot....");
    	WebDriver driver = get();
    	if(driver instanceof RemoteWebDriver/*EventFiringWebDriver*/) {
    		driver = new Augmenter().augment(driver);
    		File f = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			try {
	            File file = new File(path);
	            file.getParentFile().mkdirs();		
				FileUtils.copyFile(f, file);
			} catch (IOException e) {
				log.error("Screenshot was not copied", e);
				return false;
			}
    	}
    	boolean s = super.saveScreenshotTo(path);
    	log.info("Screenshot saved to: ["+ path +"].");
		return s;
    	
	}
	
    public  void initialize() {
    	browser = SupportedBrowser.valueOf(SupportedBrowser.class, System.getProperty("browser", "firefox").toUpperCase(usingLocale()));
    	log.info("Initialize WebDriver: [{}] started.................", browser);
    	try {
			delegate.set(createDriver(browser));
			log.info("WebDriver: [" + browser + "] with hash code: [" + delegate.get().hashCode() + "] is initialized.");
		} catch (Exception e) {
			log.error("WebDriver: [" + browser + "] was not initialized.\n", e);
			throw new RuntimeException(String.format("WebDriver: [%s] was not initialized.\n%s", browser, e));
		}
    }

    private synchronized WebDriver createDriver(SupportedBrowser browser) throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
    	String remote = System.getProperty("remote", "false");
    	log.info("Start tests on RemoteWebDriver: [" + remote + "].");
        switch (browser) {
        case CHROME:
            return createChromeDriver();
        case FIREFOX:
            return createFirefoxDriver();
        default:
            return createHtmlUnitDriver();
        }
    }
    
    private WebDriver createChromeDriver() throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
    	WebDriver driver = new ChromeDriver();
        return createEventFiringWebDriverWithListener(driver);
    }

    private WebDriver createFirefoxDriver() {
    	FirefoxBinary binary = new FirefoxBinary();
		FirefoxProfile profile = new FirefoxProfile();
		profile.setEnableNativeEvents(false);
		FirefoxDriver driver = new FirefoxDriver(binary, profile);
		return createEventFiringWebDriverWithListener(driver);
    }

    private WebDriver createHtmlUnitDriver() {
        HtmlUnitDriver driver = new HtmlUnitDriver();
        boolean javascriptEnabled = parseBoolean(System.getProperty("webdriver.htmlunit.javascriptEnabled", "true"));
        driver.setJavascriptEnabled(javascriptEnabled);
        return createEventFiringWebDriverWithListener(driver);
	}

    private WebDriver createEventFiringWebDriverWithListener(WebDriver driver) {
    	EventFiringWebDriver efWebDriver = new EventFiringWebDriver(driver);
    	CustomWebDriverEventListener eventListener = new CustomWebDriverEventListener(driver);
    	efWebDriver.register(eventListener);
    	return efWebDriver;
    }
    
    private Locale usingLocale() {
        return Locale.getDefault();
    }

}




