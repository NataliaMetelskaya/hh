package ru.hh.price.ui.price;

import org.jbehave.web.selenium.WebDriverProvider;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import ru.hh.price.ui.AbstractPage;

import java.util.List;
import java.util.ResourceBundle;

/**
 * @author e3s team
 */
public class PricePage extends AbstractPage {

    @FindBy(how = How.XPATH, using = "//h1[@class='title']")
    private WebElement priceTitle;

    @FindBy(how = How.XPATH, using = "//ul/li[contains(@class, 'PriceCart')]")
    private List<WebElement> priceTabs;

    @FindBy(how = How.XPATH, using = "//ul/a/span[contains(@class,'switcher-content')]")
    private List<WebElement> priceTabNames;

    private final String XPATH_PRICE_TAB = "//ul/a/span[contains(.,'%s')]";

    public PricePage(WebDriverProvider driverProvider) {
        super(driverProvider);
    }

    public PricePage open() {
        ResourceBundle rb = ResourceBundle.getBundle("configuration");
        get(rb.getString("url.base") + rb.getString("url.page.price"));
        return this;
    }

    public String getPriceTitle() {
        return priceTitle.getText();
    }

    public PricePage switchToTabPriceCard(String tabName) {
        findElement(By.xpath(String.format(XPATH_PRICE_TAB, tabName)))
        .click();
        return this;
    }

    public String getNameActivePriceTab() {
        return priceTabNames.get(getIndexActivePriceTab()).getText();
    }

    public int getIndexActivePriceTab() {
        for(int i = 0; i < priceTabs.size(); i++) {
            if(isActivePriceTab(priceTabs.get(i))) {
                return i;
            }
        }
        return -1;
    }

    private boolean isActivePriceTab(WebElement tab) {
        return tab.getAttribute("class").contains("g-expand");
    }


}
