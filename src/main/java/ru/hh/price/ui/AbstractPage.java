package ru.hh.price.ui;

import org.jbehave.web.selenium.WebDriverProvider;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.hh.price.constants.TimePeriod;
import ru.hh.price.utils.MathUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * 
 * @author Dzianis_Shkindzirau
 * 
 */
public abstract class AbstractPage extends WebDriverPage {

	private static Logger log = LoggerFactory.getLogger(AbstractPage.class);
	private ResourceBundle rb = ResourceBundle.getBundle("wd");
	
	public AbstractPage(WebDriverProvider driverProvider) {
		super(driverProvider);
		log.info("Page/section: [" + this.getClass().getName() + "] was created.");
	}

	public WebDriver _getWebDriver() {
		return getDriverProvider().get();
	}

	public String _getBrowserName() {
		return System.getProperty("browser").toUpperCase();
	}
	
	public <T extends AbstractPage> T _initPage() {
		log.info("Inint page: [" + this.getClass().getName() + "]");
		Integer timeout = Integer.valueOf(rb.getString("time.implicit.value"));
		PageFactory.initElements(new AjaxElementLocatorFactory(_getWebDriver(), timeout), this);
		_initWrapperElements();
        return (T) this;
	}
	
	private void _initWrapperElements() {
		Class<?> proxyIn = this.getClass();
		while (proxyIn != Object.class) {
			_decorateWebElements(proxyIn);
			proxyIn = proxyIn.getSuperclass();
		}
	}

	private void _decorateWebElements(Class<?> proxyIn) {
		WebElement webElement;
		for (Field field : proxyIn.getDeclaredFields()) {
			field.setAccessible(true);
			Object fieldValue = null;
			try {
				fieldValue = field.get(this);
			} catch (Exception e) {
				log.error("Field [" + field.getName() + "] isn`t get value (wrapper)! Exception: " + e);
				throw new WebDriverPageException("Field [" + field.getName() + "] isn`t GET value (wrapper)! \n" + e);
			}
			if (fieldValue != null && fieldValue instanceof WebElement) {
				FindBy annotation = field.getAnnotation(FindBy.class);
				webElement = new WebElementWrapper((WebElement) fieldValue, field.getName(), annotation);
				try {
					field.set(this, webElement);
				} catch (Exception e) {
					log.error("Field [" + field.getName() + "] isn`t set value (wrapper)! Exception: " + e);
					throw new WebDriverPageException("Field [" + field.getName() + "] isn`t SET value (wrapper)! \n" + e);
				}
			}
		}
	}
	
	public boolean _isTitlePresent(String title) {
		Integer timeout = Integer.valueOf(rb.getString("time.wait.open.page"));
		for (int i = 0; i < timeout; i++) {
			try {
				if (getTitle().contains(title)) {
					return true;
				}
			} catch (Exception e) {
				log.warn("Attempt get Title page. \n" + e);
			}
			_sleep(1000);
		}
		return false;
	}
	
	public boolean _isUrlOpened(String url) {
		Integer timeout = Integer.valueOf(rb.getString("time.wait.open.page"));
		for (int i = 0; i < timeout; i++) {
			try {
				if (_getWebDriver().getCurrentUrl().contains(url)) {
					return true;
				}
			} catch (Exception e) {
				log.warn("Attempt get URL page. \n" + e);
			}
			_sleep(1000);
		}
		return false;
	}

	public void _sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (Exception e) {
			log.error("Sleep error: " + e);
		}
	}
	
	//***********************************************************************************
	//****************************** WebElements
	//***********************************************************************************
	private long _getTime(TimePeriod timePeriod) {
		long time = 0;
		switch (timePeriod) {
			case SECONDS:
				time = 1;
				break;
			case MINUTES:
				time = 60;
				break;
			case HOURS:
				time = 60 * 60;
				break;
			default:
				new EnumConstantNotPresentException(TimePeriod.class, timePeriod.name());
		}
		return time;
	}
	
	
	// present
	
	public boolean _isWebElementPresent(final By selector, long timeout, TimePeriod timePeriod, final boolean needRefresh) {
		log.info("Waiting selector: [" + selector + "]. Timeout: [" + timeout + " " + timePeriod.name() + "].");
		for (int i = 0; i < timeout; i++) {
			try {
				(new WebDriverWait(_getWebDriver(), _getTime(timePeriod))).until(new ExpectedCondition<WebElement>() {
					public WebElement apply(WebDriver d) {
						return d.findElement(selector);
					}
				});
				log.info("Web element found!");
				return true;
			} catch (Exception e) {
				log.warn("Waiting Web Element [" + (i + 1) + " " + timePeriod.name() + "]. /n" + e);
			}
			if (needRefresh) {
				_getWebDriver().navigate().refresh();
			}
		}
		return false;
	}
	
	public boolean _isWebElementPresent(By selector, long timeoutSec) {
		return _isWebElementPresent(selector, timeoutSec, TimePeriod.SECONDS, false);
	}
	
	public boolean _isWebElementPresent(WebElement webElement, long timeout, TimePeriod timePeriod, final boolean needRefresh) {
		By selector = ((WebElementWrapper) webElement).getBy();
		return _isWebElementPresent(selector, timeout, timePeriod, needRefresh);
	}
	
	public boolean _isWebElementPresent(WebElement webElement, long timeoutSec) {
		By selector = ((WebElementWrapper) webElement).getBy();
		return _isWebElementPresent(selector, timeoutSec, TimePeriod.SECONDS, false);
	}
	
	
	// wait
	
	public WebElement _waitWebElement(final By selector, long timeout, TimePeriod timeoutPeriod, final boolean needRefresh) {
		if (_isWebElementPresent(selector, timeout, timeoutPeriod, needRefresh)) {
			return _getWebDriver().findElement(selector);
		}	
		log.error("Web Element with selector: [" + selector.toString() + "] isn`t found. Timeout: [" + timeout + " " + timeoutPeriod.name() + "].");
		throw new WebDriverPageException("Web Element with selector: [" + selector.toString() + "] isn`t found. Timeout: [" + timeout + " " + timeoutPeriod.name() + "].");
	}
	
//	public WebElement _waitWebElement(final By selector, long timeoutSec) {
//		return _waitWebElement(selector, timeoutSec, TimePeriod.SECONDS, false);
//	}
	
	public boolean _waitWebElement(WebElement webElement, long timeout, TimePeriod timeoutPeriod, boolean needRefresh) {
		By selector = ((WebElementWrapper) webElement).getBy();
		if (_isWebElementPresent(selector, timeout, timeoutPeriod, needRefresh)) {
			return true;
		}
		log.error("Web Element with field name: [" + ((WebElementWrapper) webElement).getWebElementFieldName() + "] and selector: [" + selector.toString() + "] isn`t found. Timeout: [" + timeout + " " + timeoutPeriod.name() + "].");
		throw new WebDriverPageException("Web Element with field name: [" + ((WebElementWrapper) webElement).getWebElementFieldName() + "] and selector: [" + selector.toString() + "] isn`t found. Timeout: [" + timeout + " " + timeoutPeriod.name() + "].");
	}
	
	public boolean _waitWebElement(WebElement webElement, long timeoutSec) {
		return _waitWebElement(webElement, timeoutSec, TimePeriod.SECONDS, false);
	}
	
	public boolean _waitWebElement(final By selector, final long timeoutSec) {
		if (_isWebElementPresent(selector, timeoutSec, TimePeriod.SECONDS,false)) {
			return true;
		}
		throw new WebDriverPageException("Web Element with selector: [" + selector.toString() + "] isn`t found. Timeout: [" + timeoutSec + " SECONDS].");
	}

	// dissapear
	
	public boolean _isWebElementDissapear(final By selector, final long timeout, final TimePeriod timePeriod, final boolean needRefresh) {
		log.info("Waiting until element: [" + selector + "] dissapear. Timeout: [" + timeout + " " + timePeriod.name() + "].");
		for (int i = 0; i < timeout; i++) {
			try {
				(new WebDriverWait(_getWebDriver(), _getTime(timePeriod))).until(ExpectedConditions.invisibilityOfElementLocated(selector));
				log.info("Web element dissapear!");
				return true;
			} catch (WebDriverException e) {
				log.warn("Waiting Web Element dissapear [" + (i + 1) + " " + timePeriod.name() + "]. WebDriverException: [" + e.getBuildInformation() + "].");
			}
			if (needRefresh) {
				_getWebDriver().navigate().refresh();
			}
		}
		log.info("Web element is not disapear!");
		return false;
	}
	
	public boolean _isWebElementDissapear(final WebElement element, final long timeout, final TimePeriod timePeriod, final boolean needRefresh) {
		By selector = ((WebElementWrapper) element).getBy();
		return _isWebElementDissapear(selector, timeout, timePeriod, needRefresh);
	}
	
        public void _waitWebElementDissapear(final By selector, final long timeout) {
		if(!_isWebElementDissapear(selector, timeout, TimePeriod.SECONDS, false)) {
                    throw new WebDriverPageException("Web Element is not dissapeared: [" + selector.toString() + "] . Timeout: [" + timeout + " SECONDS].");
                }
	}
        
	// visibility
	public boolean _isVisibilityWebElement(final By selector, final long timeout) {
		log.info("Waiting until element become visible of element located by: [" + selector + "]. Timeout: [" + timeout + " s].");
		for (int i = 0; i < timeout; i++) {
			try {
				(new WebDriverWait(_getWebDriver(), _getTime(TimePeriod.SECONDS))).until(ExpectedConditions.visibilityOfElementLocated(selector));
				log.info("Web element visible!");
				return true;
			} catch (Exception e) {
				log.warn("Waiting until Web Element become visible [" + (i + 1) + " s]. WebDriverException: [" + e + "].");
			}
		}
		log.info("Web element is not visible!");
		return false;
	}
	
	public boolean _isVisibilityWebElement(final WebElement webElement, final long timeout) {
		WebElement we;
		if(webElement instanceof WebElementWrapper) {
			log.info("Waiting until element become visible of element: [" + ((WebElementWrapper) webElement).getBy() + "]. Timeout: [" + timeout + " s].");
			we = ((WebElementWrapper) webElement).getWrappedElement();
		} else {
			 we = webElement;
			log.info("Waiting until element become visible of element: [" + webElement + "]. Timeout: [" + timeout + " s].");
		}
		for (int i = 0; i < timeout; i++) {
			try {
				(new WebDriverWait(_getWebDriver(), _getTime(TimePeriod.SECONDS))).until(ExpectedConditions.visibilityOf(we));
				log.info("Web element visible!");
				return true;
			} catch (Exception e) {
				log.warn("Waiting until Web Element become visible [" + (i + 1) + " s]. WebDriverException: [" + e + "].");
			}
		}
		log.info("Web element is not visible!");
		return false;
	}
	
	
	// wait visibility
	
	public void _waitVisibilityWebElement(final By selector, final long timeout) {
		if (!_isVisibilityWebElement(selector, timeout)) {
			log.error("Web Element with selector: [" + selector.toString() + "] isn`t visible. Timeout: [" + timeout + " SECONDS].");
			throw new WebDriverPageException("Web Element with selector: [" + selector.toString() + "] isn`t visible. Timeout: [" + timeout + " SECONDS].");
		}
	}

	public void _waitVisibilityWebElement(final WebElement element, final long timeout) {
		By by = null;
		if(element instanceof WebElementWrapper) {
			by = ((WebElementWrapper) element).getBy();
		}
		if (!_isVisibilityWebElement(element, timeout)) {
			log.error("Web Element with selector: [" + by + "] isn`t visible. Timeout: [" + timeout + " SECONDS].");
			throw new WebDriverPageException("Web Element with selector: [" + by + "] isn`t visible. Timeout: [" + timeout + " SECONDS].");
		}
	}
	
	
	// enable\disable
	
	public boolean _isEnabledWebElement(final By selector, final long timeout) {
		log.info("Waiting until element: [" + selector + "] become enabled. Timeout: [" + timeout + " s].");
		for (int i = 0; i < timeout; i++) {
			try {
				(new WebDriverWait(_getWebDriver(), _getTime(TimePeriod.SECONDS))).until(ExpectedConditions.elementToBeClickable(selector));
				log.info("Web element enabled!");
				return true;
			} catch (Exception e) {
				log.warn("Waiting until Web Element become enabled [" + (i + 1) + " s]. WebDriverException: [" + e + "].");
			}
		}
		log.info("Web element is not enabled!");
		return false;
	}
	
	public boolean _isDisabledWebElement(final By selector, final long timeout) {
		log.info("Waiting until element: [" + selector + "] become disabled. Timeout: [" + timeout + " s].");
		for (int i = 0; i < timeout; i++) {
			try {
				(new WebDriverWait(_getWebDriver(), _getTime(TimePeriod.SECONDS))).until(ExpectedConditions.elementToBeClickable(selector));
				log.info("enabled...");
			} catch (Exception e) {
				if(!_getWebDriver().findElement(selector).isEnabled()) {
					log.info("Web element disabled.");
					return true;
				}
				log.info("Waiting until Web Element become disabled [" + (i + 1) + " s]. WebDriverException: [" + e + "].");
			}			
		}
		log.info("Web element enabled.");
		return false;
	}
	
	public boolean isEnabledWebElement(final WebElement element, final long timeout) {
		final By selector = ((WebElementWrapper) element).getBy();
		return _isEnabledWebElement(selector, timeout);
	}
	
	public boolean isDisabledWebElement(final WebElement element, final long timeout) {
		final By selector = ((WebElementWrapper) element).getBy();
		return _isDisabledWebElement(selector, timeout);
	}
	
	
	// wait enable\disable
	
	public void _waitWebElementEnabled(final By selector, final long timeout) {
		if (!_isEnabledWebElement(selector, timeout)) {
			log.error("Web Element with selector: [" + selector.toString() + "] isn`t enabled. Timeout: [" + timeout + " SECONDS].");
			throw new WebDriverPageException("Web Element with selector: [" + selector.toString() + "] isn`t enabled. Timeout: [" + timeout + " SECONDS].");
		}
	}

	public void _waitWebElementEnabled(final WebElement element, final long timeout) {
		final By by = ((WebElementWrapper) element).getBy();
		_waitWebElementEnabled(by, timeout);
	}
	
         protected void _waitWebElementEnabled(final WebElement element) {
                Integer timeout = Integer.valueOf(rb.getString("time.implicit.value"));
		final By by = ((WebElementWrapper) element).getBy();
                _waitWebElementEnabled(by, timeout);
	}
	
	// check
	
	public void _check(By locator, boolean checkMode) {
		Integer timeout = Integer.valueOf(rb.getString("time.implicit.value"));
		_waitWebElement(locator, timeout);
		WebElement checkBox = _findElement(locator);
		if (!(checkBox.isSelected() == checkMode)) {
			checkBox.click();
		}
	}

	public void _check(WebElement element, boolean checkMode) {
		if (!(element.isSelected() == checkMode)) {
			element.click();
		}
	}

	
	// type
	
	public void _clearAndType(WebElement element, String text) {
		element.sendKeys(Keys.HOME);
		element.sendKeys(Keys.chord(Keys.SHIFT, Keys.END));
		element.sendKeys(text);
	}
	
	
	// find
	
	public WebElement _findElement(By by) {
		return findElement(by);
	}
	
	public List<WebElement> _findElements(By by) {
		return findElements(by);
	}
	
	@Override
	public WebElement findElement(By by) {
		try {
			return new WebElementWrapper(_getWebDriver().findElement(by), by);
		} catch (WebDriverException e) {
			throw new WebDriverPageException("Web element with selector: [" + by + "] was not found. \n" + e);
		}
	}

	@Override
	public List<WebElement> findElements(By by) {
		List<WebElement> listWEWrapper = new ArrayList<WebElement>();
		try {
			List<WebElement> listWE = _getWebDriver().findElements(by);
			for (WebElement we : listWE) {
				listWEWrapper.add(new WebElementWrapper(we, by));
			}
			return listWEWrapper;
		} catch (WebDriverException e) {
			throw new WebDriverPageException("Web elements with selector: [" + by + "] were not found. \n" + e);
		}
	}
	
	
	// editable
	
	public boolean _areFieldsEditable(boolean expectedEditable, WebElement... controls) {
		boolean areEditable = true;
		String notEditableFields = "";
		try {
			for (WebElement control : controls) {
				if (_isWebElementPresent(control, 1)) {
					if (!_isEnabledWebElement(((WebElementWrapper) control).getBy(), 1)) {
						areEditable = false;
						notEditableFields += "not editable: " + ((WebElementWrapper) control).getBy() + "\n";
					}
				} else {
					areEditable = false;
					notEditableFields += "not present: " + ((WebElementWrapper) control).getBy() + "\n";
				}
			}
		} catch (Exception e) {
			areEditable = false;
			notEditableFields += "Exception: " + e + "\n";
		} finally {
			if (!areEditable && expectedEditable) {
				log.info("Not editable fields: \n" + notEditableFields);
				throw new WebDriverPageException("There are presents no editable fields: [\n" + notEditableFields + "].");
			}
			if (areEditable && !expectedEditable) {
				throw new WebDriverPageException("There are all editable fields.");
			}
		}
		return areEditable;
	}
	
	// search elements in context 
	
	public boolean _isWebElementPresentInContext(final WebElement context, final By selector, long timeout, TimePeriod timePeriod, final boolean needRefresh) {
		log.info("Waiting selector: [" + selector + "]. Timeout: [" + timeout + " " + timePeriod.name() + "].");
		for (int i = 0; i < timeout; i++) {
			try {
				(new WebDriverWait(_getWebDriver(), _getTime(timePeriod))).until(new ExpectedCondition<WebElement>() {
					public WebElement apply(WebDriver d) {
						return context.findElement(selector);
					}
				});
				log.info("Web element found!");
				return true;
			} catch (Exception e) {
				log.warn("Waiting Web Element [" + (i + 1) + " " + timePeriod.name() + "]. /n" + e);
			}
			if (needRefresh) {
				_getWebDriver().navigate().refresh();
			}
		}
		return false;
	}
	
	public boolean _isWebElementPresentInContext(WebElement context, By selector, long timeoutSec) {
		return _isWebElementPresentInContext(context, selector, timeoutSec, TimePeriod.SECONDS, false);
	}
	
	public WebElement _findWebElementInContext(WebElement context, final By selector, long timeout) {
		if (_isWebElementPresentInContext(context, selector, timeout, TimePeriod.SECONDS, false)) {
			return context.findElement(selector);
		}	
		log.error("Web Element with selector: [" + selector.toString() + "] isn`t found in context. Timeout: [" + timeout + "sec].");
		throw new WebDriverPageException("Web Element with selector: [" + selector.toString() + "] isn`t found in context. Timeout: [" + timeout + "sec].");
	}

	public List<WebElement> _findWebElementsInContext(WebElement context, final By selector, long timeout) {
		if (_isWebElementPresentInContext(context, selector, timeout, TimePeriod.SECONDS, false)) {
			return context.findElements(selector);
		}	
		log.error("Web Element list with selector: [" + selector.toString() + "] isn`t found in context. Timeout: [" + timeout + "sec].");
		throw new WebDriverPageException("Web Element list with selector: [" + selector.toString() + "] isn`t found in context. Timeout: [" + timeout + "sec].");
	}
	

	private final String JAVASCRIPT_EXTRACT_ROOT_TEXT =	"var element = arguments[0];                                      " +
														"var text = '';                                                   " +
														"for (var i = 0; i < element.childNodes.length; i++)              " +
														" if (element.childNodes[i].nodeType === Node.TEXT_NODE)          " +
														" {                                                               " +
														"   text += element.childNodes[i].textContent + ' ';              " +
														" }                                                               " +
														"return text;                                                     ";

	public String _extractInnerText(WebElement rootElement) {
		if(rootElement instanceof WebElementWrapper) {
			rootElement = ((WebElementWrapper) rootElement).getWrappedElement();
		}
		return ((String)_executeJavascript(rootElement, JAVASCRIPT_EXTRACT_ROOT_TEXT)).trim();
	}


	//***********************************************************************************
	//****************************** Alerts
	//***********************************************************************************
	public void _clickAlertAccept() {
		try {
			_getWebDriver().switchTo().alert().accept();
		} catch (WebDriverException e) {
			log.warn("Alert wasn`t Present. WebDriverException. ");
			throw new WebDriverPageException("Alert isn`t present. \n" + e);
		}
	}

	public void _clickAlertDismiss() {
		try {
			_getWebDriver().switchTo().alert().dismiss();
		} catch (WebDriverException e) {
			log.warn("Alert wasn`t Present. WebDriverException. ");
			throw new WebDriverPageException("Alert isn`t present. \n" + e);
		}
	}

	public String _getAlertText() {
		try {
			Alert alert = _getWebDriver().switchTo().alert();
			String actualText = alert.getText();
			log.info("Alert actual text: [" + actualText + "].");
			return actualText;
		} catch (WebDriverException e) {
			throw new WebDriverPageException("The text of the alert wasn`t got.", e);
		}
	}

	public boolean _isAlertMessagePresent(String message) {
		Integer timeout = Integer.valueOf(rb.getString("time.implicit.value"));
		for (int i = 0; i < timeout; i++) {
			try {
				if (_getAlertText().contains(message)) {
					return true;
				}
			} catch (Exception e) {
				log.error("Alert or alert text is not present. \n" + e);
			}
			log.info("Warning alert text [" + i + "s].");
			_sleep(1000);
		}
		log.warn("Alert with the text: [" + message + "] is not FOUND! Timeout: [" + timeout + " SECONDS].");
		return false;
	}

	public boolean _isAlertPresent(int timeoutSec) {
		for (int i = 0; i < timeoutSec; i++) {
			try {
				_getWebDriver().switchTo().alert();
				return true;
			} catch (Exception e) {
				log.error("Alert or alert text is not present. \n" + e);
			}
			log.info("Warning alert text [" + i + "s].");
			_sleep(1000);
		}
		log.error("Alert is not FOUND! Timeout: [" + timeoutSec + " SECONDS].");
		return false;
	}

	
	//***********************************************************************************
	//****************************** Javascript and Actions
	//***********************************************************************************
	public Object _executeJavascript(final WebElement webElement, final String script) {
		try {
			return ((JavascriptExecutor) _getWebDriver()).executeScript(script, webElement);
		} catch (WebDriverException e) {
			throw new WebDriverPageException("Javascript: ["
					+ script + "] for web element with field name: ["
					+ ((WebElementWrapper) webElement).getWebElementFieldName()
					+ "] can not be executed.", e);
		}
		//IE ussue: The behavior of executeScript is currently undefined when there is a call to alert/confirm/prompt while a script: http://code.google.com/p/selenium/issues/detail?id=2760&q=executeScript&colspec=ID%20Stars%20Type%20Status%20Priority%20Milestone%20Owner%20Summary
		//Chrome issue: ChromeDriver gets stuck after clicking the particular anchor that open new window/tab: http://code.google.com/p/chromedriver/issues/detail?id=111                       
 	}
	
	public Object _executeAsyncJavascript(final WebElement webElement, final String script) {
		try {
			return ((JavascriptExecutor) _getWebDriver()).executeAsyncScript(script, webElement);
		} catch (WebDriverException e) {
			throw new WebDriverPageException("Async Javascript: ["
					+ script + "] for web element with field name: ["
					+ ((WebElementWrapper) webElement).getWebElementFieldName()
					+ "] can not be executed.", e);
		}
	}
	
	/**
	 * Actions class contains methods that enhance the of action WebElement.
	 */
	public Actions _getActions() {
		return new Actions(_getWebDriver());
	}
	
	
	
	
	//todo
	
	
	
	//***********************************************************************************
	//****************************** Selects
	//***********************************************************************************



	//***********************************************************************************
	//****************************** Windows
	//***********************************************************************************
	
	
	
	//***********************************************************************************
	//****************************** Frames
	//***********************************************************************************
	

	//***********************************************************************************
	//****************************** Browser
	//***********************************************************************************
	
	
	
}















