package ru.hh.price.ui.price;

import org.jbehave.web.selenium.WebDriverProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author e3s team
 */
@Component
public class Pages {

    private static Logger log = LoggerFactory.getLogger(Pages.class);

    @Autowired
    private WebDriverProvider webDriverProvider;

    private ThreadLocal<PricePage> pages = new ThreadLocal<>();

    public PricePage pricePage() {
        if(pages.get() == null) {
            pages.set(new PricePage(webDriverProvider).<PricePage>_initPage());
        }
        return pages.get();
    }
}
