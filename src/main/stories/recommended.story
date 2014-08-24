Supported browsers: firefox, chrome

Meta:
@Completed
@testType ui
@xmlFile classes/testdata/price.xml


Scenario: Open reccomended tab and verify card
When open '//recommended/uri' page
Then active tab '//recommended/title' is displayed on the price card
And '//tariff[1]/id' tariff with title: '//tariff[1]/title', old price: '//tariff[1]/priceOld', actual price: '//tariff[1]/priceActual' and offer plus: '//tariff[1]/offerPlus' is present
And '//tariff[2]/id' tariff with title: '//tariff[2]/title', old price: '//tariff[2]/priceOld', actual price: '//tariff[2]/priceActual' and offer plus: '//tariff[2]/offerPlus' is present
And Cart is empty


Scenario: Add all tariffs to cart
When 'In cart' button on '//tariff[1]/id' tariff is clicked
Then item with title: '//tariff[1]/cartView/title', old price: '//tariff[1]/cartView/priceOld', actual price: '//tariff[1]/cartView/priceActual' and description: '//tariff[1]/cartView/descrip' is added to Cart
And total old: '//tariff[1]/cartView/priceOld' and actual: '//tariff[1]/cartView/priceActual' cost is displayed on Cart
And gift: '//tariff[1]/cartView/giftText' text is displayed on Cart
And text: '//inCardText' is displayed on tariff: '//tariff[1]/id' button
When 'In cart' button on '//tariff[2]/id' tariff is clicked
Then item with title: '//tariff[2]/cartView/title', old price: '//tariff[2]/cartView/priceOld', actual price: '//tariff[2]/cartView/priceActual' and description: '//tariff[2]/cartView/descrip' is added to Cart
And total old: '//cart//priceOld' and actual: '//cart//priceActual' cost is displayed on Cart
And gift: '//tariff[1]/cartView/giftText' text is displayed on Cart
And gift: '//tariff[2]/cartView/giftText' text is displayed on Cart
And text: '//inCardText' is displayed on tariff: '//tariff[1]/id' button
And text: '//inCardText' is displayed on tariff: '//tariff[2]/id' button


Scenario: Remove tariff (item) from Cart
When 'Remove' link on '//tariff[2]/cartView/title' item is clicked on Cart
Then item with title: '//tariff[1]/cartView/title', old price: '//tariff[1]/cartView/priceOld', actual price: '//tariff[1]/cartView/priceActual' and description: '//tariff[1]/cartView/descrip' is present in Cart
And total old: '//tariff[1]/cartView/priceOld' and actual: '//tariff[1]/cartView/priceActual' cost is displayed on Cart
And gift: '//tariff[1]/cartView/giftText' text is displayed on Cart
And text: '//inCardText' is displayed on tariff: '//tariff[1]/id' button
And text: '//addInCartText' is displayed on tariff: '//tariff[2]/id' button