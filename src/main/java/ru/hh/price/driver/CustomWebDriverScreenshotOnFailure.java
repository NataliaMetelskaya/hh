package ru.hh.price.driver;

import org.jbehave.core.annotations.AfterScenario;
import org.jbehave.core.annotations.AfterScenario.Outcome;
import org.jbehave.core.failures.PendingStepFound;
import org.jbehave.core.failures.UUIDExceptionWrapper;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.web.selenium.RemoteWebDriverProvider;
import org.jbehave.web.selenium.WebDriverProvider;
import org.jbehave.web.selenium.WebDriverSteps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.text.MessageFormat;
import java.util.UUID;

/**
 *
 * @author Dzianis_Shkindzirau
 *
 */
public class CustomWebDriverScreenshotOnFailure  extends WebDriverSteps {

	private static Logger log = LoggerFactory.getLogger(CustomWebDriverScreenshotOnFailure.class);
	
    public static final String DEFAULT_SCREENSHOT_PATH_PATTERN = "{0}/screenshots/failed-scenario-{1}.png";
    protected final StoryReporterBuilder reporterBuilder;
    protected final String screenshotPathPattern;

    public CustomWebDriverScreenshotOnFailure(WebDriverProvider driverProvider) {
        this(driverProvider, new StoryReporterBuilder());
    }

    public CustomWebDriverScreenshotOnFailure(WebDriverProvider driverProvider, StoryReporterBuilder reporterBuilder) {
        this(driverProvider, reporterBuilder, DEFAULT_SCREENSHOT_PATH_PATTERN);
    }

    public CustomWebDriverScreenshotOnFailure(WebDriverProvider driverProvider, StoryReporterBuilder reporterBuilder, String screenshotPathPattern) {
        super(driverProvider);
        this.reporterBuilder = reporterBuilder;
        this.screenshotPathPattern = screenshotPathPattern;
    }

    @AfterScenario(uponOutcome = Outcome.FAILURE)
    public void afterScenarioFailure(UUIDExceptionWrapper uuidWrappedFailure) {
    	log.info("+++++++++++++++++++++++++++++++++++++");
    	log.info("+++++++++++++++++++++++++++++++++++++");
    	log.info("++ TAKE SKREENSHOT - START ++++++++++");
    	log.info("+++++++++++++++++++++++++++++++++++++");
    	log.info("+++++++++++++++++++++++++++++++++++++");
 
        if (uuidWrappedFailure instanceof PendingStepFound) {
        	log.info("Don't take screen-shots for Pending Steps.");
            return; // we don't take screen-shots for Pending Steps
        }
        
        String screenshotPath = ("[unknown screanshot path]");
        try {
        	screenshotPath = screenshotPath(uuidWrappedFailure.getUUID());
        } catch (Exception e) {
        	log.error("Screenshot Path was not create.\n", e);
        	return;
        }

        String currentUrl = "[unknown page title]";
        try {
            currentUrl = driverProvider.get().getCurrentUrl();
        } catch (Exception e) {
        	log.warn("Web driver url was not got. Becouse: \n", e);
        }
        boolean savedIt = false;
        try {
            savedIt = driverProvider.saveScreenshotTo(screenshotPath);
        } catch (RemoteWebDriverProvider.SauceLabsJobHasEnded e) {
            log.error("Screenshot of page '" + currentUrl + "' has **NOT** been saved. The SauceLabs job has ended, possibly timing out on their end.", e);
            return;
        } catch (Exception e) {
            log.warn("Screenshot of page '" + currentUrl + ". Will try again. Cause: \n", e);
            // Try it again.  WebDriver (on SauceLabs at least?) has blank-page and zero length files issues.
            try {
                savedIt = driverProvider.saveScreenshotTo(screenshotPath);
            } catch (Exception e1) {
                log.error("Screenshot of page '" + currentUrl + "' has **NOT** been saved to '" + screenshotPath + "' because error '" + e.getMessage() + "' encountered. Stack trace follows:\n", e);
                return;
            }
        }
        if (savedIt) {
            log.info("Screenshot of page '" + currentUrl + "' has been saved to '" + screenshotPath +"' with " + new File(screenshotPath).length() + " bytes");
        } else {
            log.error("Screenshot of page '" + currentUrl + "' has **NOT** been saved. If there is no error, perhaps the WebDriver type you are using is not compatible with taking screenshots");
        }
    }

    protected String screenshotPath(UUID uuid) {
        return MessageFormat.format(screenshotPathPattern, reporterBuilder.outputDirectory(), uuid);
    }
}
