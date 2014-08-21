package ru.hh.price.ui;

import org.openqa.selenium.*;
import org.openqa.selenium.internal.WrapsElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


/**
 *
 * @author Dzianis_Shkindzirau
 *
 */
public class WebElementWrapper implements WebElement, WrapsElement {

    private Logger log = LoggerFactory.getLogger(WebElementWrapper.class);
    private WebElement wrapped;
    private String webElementFieldName;
    private FindBy findBy;
    private By wrapBy;

    public WebElementWrapper(WebElement webElement, By by) {
        super();
        this.wrapBy = by;
        this.wrapped = webElement;
        this.webElementFieldName = null;
        this.findBy = null;  
    }
    
    public WebElementWrapper(WebElement wrapped, String webElementFieldName, FindBy findBy) {
        super();
        this.wrapBy = null;
        this.wrapped = wrapped;
        this.webElementFieldName = webElementFieldName;
        this.findBy = findBy;
    }
    
    public void click() {
        try {      
            log.info("Click on the web element with field name: [" + webElementFieldName + "] and selector: [" + getBy() + "].");
            wrapped.click();
        } catch (WebDriverException e) {
        	log.error("No clicking on the web element with field name: [" + webElementFieldName + "] and selector: [" + getBy() + "]. \n Exception: " + e);
        	throw new WebDriverPageException("No clicking on the web element with field name: [" + webElementFieldName + "] and selector: [" + getBy() + "]. \n" + e);
        }
    }

    public void submit() {
        try {
        	log.info("Submit on the web element with field name: [" + webElementFieldName + "] and selector: [" + getBy() + "].");
            wrapped.submit();       
        } catch (WebDriverException e) {
        	log.error("No submit on the web element with field name: [" + webElementFieldName + "] and selector: [" + getBy() + "]. \n Exception: " + e);
            throw new WebDriverPageException("No submit on the web element with field name: [" + webElementFieldName + "] and selector: [" + getBy() + "]. \n" + e);
        }
    }

    public void sendKeys(CharSequence... keysToSend) {
        try {
        	log.info("Send keys char sequence: [" + keysToSend.toString() + "] on the web element with field name: [" + webElementFieldName + "] and selector: [" + getBy() + "].");
            wrapped.sendKeys(keysToSend);
        } catch (WebDriverException e) {
        	log.error("No send keys char sequence: [" + keysToSend.toString() + "] on the web element with field name: [" + webElementFieldName + "] and selector: [" + getBy() + "]. \n Exception: " + e);
            throw new WebDriverPageException("No send keys char sequence: [" + keysToSend.toString() + "] on the web element with field name: [" + webElementFieldName + "] and selector: [" + getBy() + "]. \n" + e);
        }
    }

    public void clear() {
        try {
        	log.info("Web element with field name: [" + webElementFieldName + "] and selector: [" + getBy() + "] is cleaned.");
            wrapped.clear();
        } catch (WebDriverException e) {
        	log.error("Web element with field name: [" + webElementFieldName + "] and selector: [" + getBy() + "] was not cleaned. \n Exception: " + e);
            throw new WebDriverPageException("No clear on the web element with field name: [" + webElementFieldName + "] and selector: [" + getBy() + "]. \n" + e);
        }
    }

    public String getTagName() {
        try {
        	log.info("Getting tag name on the web element with field name: [" + webElementFieldName + "] and selector: [" + getBy() + "]...");
        	String tn = wrapped.getTagName();
        	log.info("Tag name: [" + tn + "] was got.");
            return tn;
        } catch (WebDriverException e) {
        	log.error("Tag name on the web element with field name: [" + webElementFieldName + "] and selector: [" + getBy() + "] was not got. \n Exception: " + e);
            throw new WebDriverPageException("No get tag name on the web element with field name: [" + webElementFieldName + "] and selector: [" + getBy() + "]. \n" + e);
        }
    }

    public String getAttribute(String name) {
        try {
        	log.info("Getting attribute: [" + name + "] value on the web element with field name: [" + webElementFieldName + "] and selector: [" + getBy() + "].");
        	String atr = wrapped.getAttribute(name);
        	log.info("Attribute value: [" + atr + "] was got.");
            return atr;
        } catch (WebDriverException e) {
        	log.error("Attribute: [" + name + "] on the web element with field name: [" + webElementFieldName + "] and selector: [" + getBy() + "] was not got. \n Exception: " + e);
            throw new WebDriverPageException("No get attribute: [" + name + "] on the web element with field name: [" + webElementFieldName + "] and selector: [" + getBy() + "]. \n" + e);
        }
    }

    public boolean isSelected() {
        try {
        	log.info("Web element with field name: [" + webElementFieldName + "] and selector: [" + getBy() + "] is selected ?");
        	boolean is = wrapped.isSelected();
        	log.info("Is selected: [" + is + "].");
            return is;
        } catch (WebDriverException e) {
            log.error("Web element with field name: [" + webElementFieldName + "] and selector: [" + getBy() + "] was not selected. \n", e);
            return false;
        }
    }

    public boolean isEnabled() {
        try {
        	log.info("Web element with field name: [" + webElementFieldName + "] and selector: [" + getBy() + "] is enabled ?");
        	boolean is = wrapped.isEnabled();
        	log.info("Is enabled: [" + is + "].");
            return is;
        } catch (WebDriverException e) {
            log.error("Web element with field name: [" + webElementFieldName + "] and selector: [" + getBy() + "] was not enabled. \n", e);
            return false;
        }
    }
    
    public boolean isDisplayed() {
        try {
        	log.info("Web element with field name: [" + webElementFieldName + "] and selector: [" + getBy() + "] is displayed ?");
        	boolean is = wrapped.isDisplayed();
        	log.info("Is displayed: [" + is + "].");
            return is;
        } catch (WebDriverException e) {
            log.error("No displayed on the web element with field name: [" + webElementFieldName + "] and selector: [" + getBy() + "]. \n", e);
            return false;
        }
    }

    public String getText() {
        try {
        	log.info("Getting text from the web element with field name: [" + webElementFieldName + "] and selector: [" + getBy() + "].");
        	String text = wrapped.getText();
        	log.info("Text: [" + text + "].");
            return text;
        } catch (WebDriverException e) {
        	log.error("Text from the web element with field name: [" + webElementFieldName + "] and selector: [" + getBy() + "] was not got. \n Exception: " + e);
            throw new WebDriverPageException("Text from the web element with field name: [" + webElementFieldName + "] and selector: [" + getBy() + "] was not got. \n" + e);
        }
    }

    public List<WebElement> findElements(By by) {
        try {
        	log.info("Find list of elements with seletor: [" + by + "] on the web element with field name: [" + webElementFieldName + "] and selector: [" + getBy() + "].");
        	List<WebElement> list = wrapped.findElements(by);
        	log.info("Found list of elements: [" + list + "]");	 
            return list;
        } catch (WebDriverException e) {
        	log.error("No find list of elements with seletor: [" + by + "] on the web element with field name: [" + webElementFieldName + "] and selector: [" + getBy() + "]. \n Exception: " + e);
            throw new WebDriverPageException("No find list of elements with seletor: [" + by + "] on the web element with field name: [" + webElementFieldName + "] and selector: [" + getBy() + "]. \n" + e);
        }
    }

    public WebElement findElement(By by) {
        try {
        	log.info("Find element with seletor: [" + by + "] on the web element with field name: [" + webElementFieldName + "] and selector: [" + getBy() + "].");
        	WebElement el = wrapped.findElement(by);
            log.info("Found element: [" + el + "]");	 
            return el;
        } catch (WebDriverException e) {
        	log.error("No find element with seletor: [" + by + "] on the web element with field name: [" + webElementFieldName + "] and selector: [" + getBy() + "]. \n Exception: " + e);
            throw new WebDriverPageException("No find element with seletor: [" + by + "] on the web element with field name: [" + webElementFieldName + "] and selector: [" + getBy() + "]. \n" + e);
        }
    }

    public Point getLocation() {
        try {
        	log.info("Getting Location of the web element with field name: [" + webElementFieldName + "] and selector: [" + getBy() + "].");
        	Point point = wrapped.getLocation();
        	log.info("Location: [" + point + "]");
            return point;
        } catch (WebDriverException e) {
        	log.error("No get Location of the web element with field name: [" + webElementFieldName + "] and selector: [" + getBy() + "]. \n Exception: " + e);
            throw new WebDriverPageException("No get Location of the web element with field name: [" + webElementFieldName + "] and selector: [" + getBy() + "]. \n" + e);
        }
    }

    public Dimension getSize() {
        try {
        	log.info("Getting size of the web element with field name: [" + webElementFieldName + "] and selector: [" + getBy() + "].");
        	Dimension dim = wrapped.getSize();
        	log.info("Size: [" + dim + "].");
            return dim;
        } catch (WebDriverException e) {
        	log.error("No get size of the web element with field name: [" + webElementFieldName + "] and selector: [" + getBy() + "]. \n Exception: " + e);
            throw new WebDriverPageException("No get size of the web element with field name: [" + webElementFieldName + "] and selector: [" + getBy() + "]. \n" + e);
        }
    }

    public String getCssValue(String propertyName) {
        try {
        	log.info("Getting css value property name: [" + propertyName + "] of the web element with field name: [" + webElementFieldName + "] and selector: [" + getBy() + "].");
        	String valCss = wrapped.getCssValue(propertyName);
        	log.info("Css value property name: [" + valCss + "].");
            return valCss;
        } catch (WebDriverException e) {
        	log.error("No get css value property name: [" + propertyName + "] of the web element with field name: [" + webElementFieldName + "] and selector: [" + getBy() + "]. \n Exception: " + e);
            throw new WebDriverPageException("No get css value property name: [" + propertyName + "] of the web element with field name: [" + webElementFieldName + "] and selector: [" + getBy() + "]. \n" + e);
        }
    }

    public By getBy() {
        if (findBy != null) {
            switch (findBy.how()) {
                case NAME:
                    return By.name(findBy.using());
                case XPATH:
                    return By.xpath(findBy.using());
                case CSS:
                    return By.cssSelector(findBy.using());
                case CLASS_NAME:
                    return By.className(findBy.using());
                case ID:
                    return By.id(findBy.using());
                case ID_OR_NAME:
                    return By.id(findBy.using());
                case LINK_TEXT:
                    return By.linkText(findBy.using());
                case PARTIAL_LINK_TEXT:
                    return By.partialLinkText(findBy.using());
                case TAG_NAME:
                    return By.tagName(findBy.using());
                default:
                    new EnumConstantNotPresentException(How.class, findBy.how().name());
            }
        }
        return wrapBy;
    }

    public FindBy getAnnotation() {
        return findBy;
    }

    public WebElement getWrappedElement() {
        return wrapped;
    }

    public String getWebElementFieldName() {
        return webElementFieldName;
    }
}
