Feature: Error validation

    @ErrorValidation
    Scenario Outline: Login with incorrect credentials

      Given I landed on the e-commerce website
      When Logged in with username <userName> and password <password>
      Then "Incorrect email or password." message is displayed

      Examples:
        |        userName           |     password     |
        |    oliverfeinm@gmail.com  |    Oliver@123    |
