Supported browsers: firefox, chrome

Meta:
@Completed
@testType ui
@xmlFile classes/testdata/price.xml


Scenario: Open reccomended tab and verify region and prof area default values and other default values
When open '//dbaccess/uri' page
Then active tab '//dbaccess/title' is displayed on the price card
And '//regions/default' list regions is present at the DbAccess tab
And '//profAreas/default' list prof areas is present at the DbAccess tab
And radio button with the label: '//dbaccess//default/tariffLabel' is checked at the DbAccess tab
And count: '//dbaccess//default/countRadiobuttons' radio buttons with cost is present at the DbAccess tab
And actual total cost: '//dbaccess//default/totalCost' is displayed at the DbAccess tab


Scenario: Verify tariffs
Then tariff with title: <xpathTariffTitle> and cost items: <xpathTariffCostItems> is displayed at the DbAccess tab
Examples:
| xpathTariffTitle            | xpathTariffCostItems            |
| //dbaccess//tariff[1]/title | //dbaccess//tariff[1]/costItems |
| //dbaccess//tariff[2]/title | //dbaccess//tariff[2]/costItems |
| //dbaccess//tariff[3]/title | //dbaccess//tariff[3]/costItems |


Scenario: Change regions and verify
When open '//dbaccess/uri' page
Then active tab '//dbaccess/title' is displayed on the price card
When change region to '//regions/selectRegions' list new regions at the DbAccess tab
Then '//regions/displayedRegions' list regions is present at the DbAccess tab


Scenario: Change prof area and verify
When open '//dbaccess/uri' page
Then active tab '//dbaccess/title' is displayed on the price card
When change prof area to '//profAreas/selectProfArea' list new prof areas at the DbAccess tab
Then '//profAreas/displayedProfArea' list prof areas is present at the DbAccess tab


Scenario: Change tariff
When open '//dbaccess/uri' page
Then active tab '//dbaccess/title' is displayed on the price card
When check radio button with days: '//changeTariff/selectButton/days' and cost: '//changeTariff/selectButton/cost' at the DbAccess tab
Then radio button with the label: '//changeTariff/displayedValue' is checked at the DbAccess tab
And actual total cost: '//dbaccess//changeTariff/totalCost' is displayed at the DbAccess tab


Scenario: Add tariff to cart and go to payment
When open '//dbaccess/uri' page
Then active tab '//dbaccess/title' is displayed on the price card
When 'Add to Cart' button is clicked at the DbAccess tab
Then item with title: '//dbaccess//addToCart//title', old price: '//dbaccess//addToCart//priceOld', actual price: '//dbaccess//addToCart//priceActual' and description: '//dbaccess//addToCart//descrip' is added to Cart
And gift: '//dbaccess//addToCart//giftText' text is displayed on Cart
When 'Go to payment' link is clicked at the DbAccess tab
Then 'Payment page' is opened


Scenario: Add tariff to cart, change tariff and click recalculate
When open '//dbaccess/uri' page
Then active tab '//dbaccess/title' is displayed on the price card
When 'Add to Cart' button is clicked at the DbAccess tab
Then item with title: '//dbaccess//addToCart//title', old price: '//dbaccess//addToCart//priceOld', actual price: '//dbaccess//addToCart//priceActual' and description: '//dbaccess//addToCart//descrip' is added to Cart
And gift: '//dbaccess//addToCart//giftText' text is displayed on Cart
When check radio button with days: '//changeTariff/selectButton/days' and cost: '//changeTariff/selectButton/cost' at the DbAccess tab
Then radio button with the label: '//changeTariff/displayedValue' is checked at the DbAccess tab
And actual total cost: '//dbaccess//changeTariff/totalCost' is displayed at the DbAccess tab
When 'Recalculate' button is clicked at the DbAccess tab
Then item with title: '//dbaccess//changeTariff/cartView/title', old price: '//dbaccess//changeTariff/cartView/priceOld', actual price: '//dbaccess//changeTariff/cartView/priceActual' and description: '//dbaccess//changeTariff/cartView/descrip' is added to Cart
And gift: '//dbaccess//addToCart//giftText' text is displayed on Cart

