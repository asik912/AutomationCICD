Feature: Purchase an order in an e-commerce website

  Background:
  Given I landed on the e-commerce website

    @Regression
    Scenario Outline: Positive test of submitting the order

      Given Logged in with username <userName> and password <password>
      When I add product <productName> to cart
      And Checkout <productName> and submit the order
      Then "THANKYOU FOR THE ORDER." message is displayed on OrderConfirmationPage

      Examples:
        |        userName          |     password     |    productName    |
        |    oliverfein@gmail.com  |    Oliver@123    |    ZARA COAT 3    |
