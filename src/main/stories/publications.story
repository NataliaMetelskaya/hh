Supported browsers: firefox, chrome

Meta:
@Completed
@testType ui
@xmlFile classes/testdata/price.xml


Scenario: Open publications tab
When open '//publications/uri' page
Then active tab '//publications/title' is displayed on the price card


Scenario: Verify all publications
Then publication with the title: <xpathTitle>, amount: <xpathAmount> and cost: <xpathCost> is displayed at the Publications tab
Examples:
| xpathTitle                                  | xpathAmount                                  | xpathCost                                  |
| //publications/default/publication[1]/title | //publications/default/publication[1]/amount | //publications/default/publication[1]/cost |
| //publications/default/publication[2]/title | //publications/default/publication[2]/amount | //publications/default/publication[2]/cost |
| //publications/default/publication[3]/title | //publications/default/publication[3]/amount | //publications/default/publication[3]/cost |
| //publications/default/publication[4]/title | //publications/default/publication[4]/amount | //publications/default/publication[4]/cost |


Scenario: Change amount and verify cost
When set amount: '//publications/changeAmount/amount' for publication with the title: '//publications/default/publication[2]/title' at the Publications tab
Then cost: '//publications/changeAmount/cost' with currency: '//publications/changeAmount/cur' for publication with the title: '//publications/default/publication[2]/title' is displayed at the Publications tab


Scenario: Add to cart
When open '//publications/uri' page
Then active tab '//publications/title' is displayed on the price card
When 'Add to Cart' ('Recalculate') button is clicked for publication with the title: '//publications/default/publication[4]/title' at the Publications tab
Then item with title: '//publications/cartView/title', old price: '//publications/cartView/priceOld', actual price: '//publications/cartView/priceActual' and description: '//publications/cartView/descrip' is added to Cart
And gift: '//publications/cartView/giftText' text is displayed on Cart


Scenario: Add discaunt rate to cart
When open '//publications/uri' page
Then active tab '//publications/title' is displayed on the price card
When discaunt rate link with the amount: '//publications/discountRate/link/amount' and cost: '//publications/discountRate/link/cost' is clicked for publication with the title: '//publications/default/publication[1]/title' at the Publications tab
Then publication with the title: '//publications/default/publication[1]/title', amount: '//publications/discountRate/view/amount' and cost: '//publications/discountRate/view/cost' is displayed at the Publications tab
Then publication with the title: '//publications/default/publication[1]/title' and old cost: '//publications/discountRate/view/oldCost' is displayed at the Publications tab
When 'Add to Cart' ('Recalculate') button is clicked for publication with the title: '//publications/default/publication[1]/title' at the Publications tab
Then item with title: '//publications/discountRate/cart/title', old price: '//publications/discountRate/cart/priceOld', actual price: '//publications/discountRate/cart/priceActual' and description: '//publications/discountRate/cart/descrip' is added to Cart
And gift: '//publications/discountRate/cart/giftText' text is displayed on Cart
