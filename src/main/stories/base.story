Supported browsers: firefox, chrome

Meta:
@Completed
@testType ui
@xmlFile classes/testdata/price.xml


Scenario: Open price page
When open '//priceCard/uri' page
Then page with '//priceCard/title' title is opened


Scenario: Check clickable tabs on the price card
When switch to <xpathTabName> tab on the price card
Then active tab <xpathTabName> is displayed on the price card
Examples:
| xpathTabName         |
| //additional/title   |
| //publications/title |
| //dbaccess/title     |
| //recommended/title  |


Scenario: Check saved items in cart if change tab
When open '//recommended/uri' page
Then active tab '//recommended/title' is displayed on the price card
When 'In cart' button on '//tariff[1]/id' tariff is clicked
Then item with title: '//tariff[1]/cartView/title', old price: '//tariff[1]/cartView/priceOld', actual price: '//tariff[1]/cartView/priceActual' and description: '//tariff[1]/cartView/descrip' is added to Cart
When switch to '//dbaccess/title' tab on the price card
Then active tab '//dbaccess/title' is displayed on the price card
And item with title: '//tariff[1]/cartView/title', old price: '//tariff[1]/cartView/priceOld', actual price: '//tariff[1]/cartView/priceActual' and description: '//tariff[1]/cartView/descrip' is added to Cart


Scenario: Go to pay of a Cart
When 'Go to payment' button is clicked at the Cart
Then 'Payment page' is opened

