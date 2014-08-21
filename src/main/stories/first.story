Supported browsers: ie, firefox, chrome

Meta:
@InProgress
@testType ui
@uiFramework wui
@xmlFile classes/testdata/price.xml


Scenario: Open Price page
When open Price page
Then page with '//priceCard/title' title is opened
When switch to '//additional/title' tab on price card
Then active tab is '//publications/title' on price card