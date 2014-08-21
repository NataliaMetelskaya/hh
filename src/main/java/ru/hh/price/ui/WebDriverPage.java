package ru.hh.price.ui;

import org.jbehave.web.selenium.WebDriverProvider;
import org.openqa.selenium.*;

import java.util.List;
import java.util.Set;

/**
 * Abstract base class for pages that use the WebDriver API. It contains common
 * page methods, with a view to implement the <a
 * href="http://code.google.com/p/selenium/wiki/PageObjects">Page Objects</a>
 * pattern.
 */
public abstract class WebDriverPage implements WebDriver, JavascriptExecutor, HasCapabilities {

    private final WebDriverProvider driverProvider;

    public WebDriverPage(WebDriverProvider driverProvider) {
        this.driverProvider = driverProvider;
    }


    protected WebDriverProvider getDriverProvider() {
        return driverProvider;
    }

    public void get(String url) {
        driverProvider.get().get(url);
    }

    public String getCurrentUrl() {
        return driverProvider.get().getCurrentUrl();
    }

    public String getTitle() {
        return driverProvider.get().getTitle();
    }

    public List<WebElement> findElements(By by) {
        
        return driverProvider.get().findElements(by);
    }

    public WebElement findElement(By by) {
        
        return driverProvider.get().findElement(by);
    }

    public String getPageSource() {
        
        return driverProvider.get().getPageSource();
    }

    public void close() {
        
        driverProvider.get().close();
    }

    public void quit() {
        
        driverProvider.get().quit();
    }

    public Set<String> getWindowHandles() {
        
        return driverProvider.get().getWindowHandles();
    }

    public String getWindowHandle() {
        
        return driverProvider.get().getWindowHandle();
    }

    public TargetLocator switchTo() {
        return driverProvider.get().switchTo();
    }

    public Navigation navigate() {
        return driverProvider.get().navigate();
    }

    public Options manage() {
        return driverProvider.get().manage();
    }

    // From JavascriptExecutor

    public Object executeScript(String s, Object... args) {
        return ((JavascriptExecutor) driverProvider.get()).executeScript(s, args);
    }

    public Object executeAsyncScript(String s, Object... args) {
        return ((JavascriptExecutor) driverProvider.get()).executeAsyncScript(s, args);
    }

    // From HasCapabilities

    public Capabilities getCapabilities() {
        return ((HasCapabilities) driverProvider.get()).getCapabilities();
    }

}

