package ru.hh.price.driver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Dzianis_Shkindzirau
 * 
 */
public class CustomWebDriverEventListener implements WebDriverEventListener {
	
	private static Logger log = LoggerFactory.getLogger(CustomWebDriverEventListener.class);
	
	private WebDriver webDriver;
	
	List<String> ignoreElements = new ArrayList<String>();

	public CustomWebDriverEventListener(/*WebDriverProvider webDriverProvider*/WebDriver driver) {   //EventFiringWebDriver
		ignoreElements.add("");
		this.webDriver = driver;
	}

	public void afterChangeValueOf(WebElement arg0, WebDriver arg1) {
		log.info("****wd="+webDriver+"****(afterChangeValueOf). Parameters:[webElement="+arg0+", webDriver="+arg1+"].");
	}

	public void afterClickOn(WebElement arg0, WebDriver arg1) {
		log.info("****wd="+webDriver+"****(afterClickOn). Parameters:[webElement="+arg0+", webDriver="+arg1+"].");
	}

	public void afterFindBy(By by, WebElement element, WebDriver driver) {
		log.info("****wd="+webDriver+"****(afterFindBy). Parameters:[webElement="+element+", webDriver="+driver+"].");
	}

	public void afterNavigateBack(WebDriver arg0) {
		log.info("****wd="+webDriver+"****(afterNavigateBack). Parameters:[webDriver="+arg0+"].");
	}

	public void afterNavigateForward(WebDriver arg0) {
		log.info("****wd="+webDriver+"****(afterNavigateForward). Parameters:[webDriver="+arg0+"].");
	}

	public void afterNavigateTo(String arg0, WebDriver arg1) {
		log.info("****wd="+webDriver+"****(afterNavigateTo). Parameters:[string="+arg0+", webDriver="+arg1+"].");
	}

	public void afterScript(String arg0, WebDriver arg1) {
		log.info("****wd="+webDriver+"****(afterScript). Parameters:[string="+arg0+", webDriver="+arg1+"].");
	}

	public void beforeChangeValueOf(WebElement arg0, WebDriver arg1) {
		log.info("****wd="+webDriver+"****(beforeChangeValueOf). Parameters:[webElement="+arg0+", webDriver="+arg1+"].");
	}

	public void beforeClickOn(WebElement arg0, WebDriver arg1) {
		log.info("****wd="+webDriver+"****(beforeClickOn). Parameters:[webElement="+arg0+", webDriver="+arg1+"].");
	}

	public void beforeFindBy(By by, WebElement element, WebDriver driver) {
		log.info("****wd="+webDriver+"****(beforeFindBy). Parameters:[by="+by+", webElement="+element+", webDriver="+driver+"].");
/*		if (!ignoreElements.contains(by.toString())) {
			new WebDriverWait(driver, 30, 500).until(ExpectedConditions.visibilityOfElementLocated(by));
		}*/
	}

	public void beforeNavigateBack(WebDriver arg0) {
		log.info("****wd="+webDriver+"****(beforeNavigateBack). Parameters:[webDriver="+arg0+"].");
	}

	public void beforeNavigateForward(WebDriver arg0) {
		log.info("****wd="+webDriver+"****(beforeNavigateForward). Parameters:[webDriver="+arg0+"].");
	}

	public void beforeNavigateTo(String arg0, WebDriver arg1) {
		log.info("****wd="+webDriver+"****(beforeNavigateTo). Parameters:[string="+arg0+", webDriver="+arg1+"].");
	}

	public void beforeScript(String arg0, WebDriver arg1) {
		log.info("****wd="+webDriver+"****(beforeScript). Parameters:[string="+arg0+", webDriver="+arg1+"].");
	}

	public void onException(Throwable er, WebDriver driver) {
		log.info("****wd="+webDriver+"****(onException). Parameters:[Throwable="+er+", webDriver="+driver+"].");
	}
}
