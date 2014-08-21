package ru.hh.price.steps;

import org.jbehave.core.annotations.AfterStories;
import org.jbehave.web.selenium.FirefoxWebDriverProvider;
import org.jbehave.web.selenium.WebDriverProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author Dzianis_Shkindzirau
 * 
 */
public class JournaledStoriesSteps {

	private static Logger log = LoggerFactory.getLogger(JournaledStoriesSteps.class);
	
    private String JOURNAL_FIREFOX_COMMANDS;
    private final WebDriverProvider webDriverProvider;

    public JournaledStoriesSteps(WebDriverProvider webDriverProvider) {
        this.webDriverProvider = webDriverProvider;
    }

    @AfterStories
    public void afterStories() throws Exception {
    	JOURNAL_FIREFOX_COMMANDS = System.getProperty("JOURNAL_FIREFOX_COMMANDS", "false");
        if (!JOURNAL_FIREFOX_COMMANDS.equals("false") && webDriverProvider instanceof FirefoxWebDriverProvider) {
            FirefoxWebDriverProvider.WebDriverJournal journal = ((FirefoxWebDriverProvider) webDriverProvider).getJournal();
            log.info("Journal of WebDriver Commands:");
            for (Object entry : journal) {
            	log.info("[" + entry + "]");
            }
            ((FirefoxWebDriverProvider) webDriverProvider).clearJournal();
        }
    }
    
}
