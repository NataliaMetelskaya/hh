package ru.hh.price.ui.price;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import org.jbehave.core.annotations.Alias;
import org.jbehave.core.annotations.Named;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.hh.price.ui.BaseSteps;

/**
 * 
 * @author Dzianis_Shkindzirau
 * 
 */
@Component
public class PricePageSteps extends BaseSteps {
	
	@Autowired
	private Pages pages;
	
	@When("open Price page")
	public void open_price_page() {
		pages.pricePage().open();
	}

    @Then("page with '$xpathTitlePage' title is opened")
    @Alias("page with <xpathTitlePage> title is opened")
    public void title_is_present(@Named("xmlFile") String xmlFile, @Named("xpathTitlePage") String xpathTitlePage) {
        String titlePage = xmlParser.getDataElement(xmlFile, xpathTitlePage);
        assertThat("The page with title: [" + titlePage + "] is not opened.",
                pages.pricePage().getPriceTitle(),
                is(equalTo(titlePage)));
    }

    @When("switch to '$xpathTabName' tab on price card")
    public void switch_to_tab(@Named("xmlFile") String xmlFile, @Named("xpathTabName") String xpathTabName) {
        String tabName = xmlParser.getDataElement(xmlFile, xpathTabName);
        pages.pricePage().switchToTabPriceCard(tabName);
    }

    @Then("active tab is '$xpathTabName' on price card")
    public void verify_name_active_price_tab(@Named("xmlFile") String xmlFile, @Named("xpathTabName") String xpathTabName) {
        String tabName = xmlParser.getDataElement(xmlFile, xpathTabName);
        assertThat("",
                pages.pricePage().getNameActivePriceTab(),
                is(equalTo(tabName)));
    }
}
