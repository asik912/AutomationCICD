package frameworkPractice.stepDefinition;

import frameworkPractice.TestComponents.BaseTest;
import frameworkPractice.pageObjects.*;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

import java.io.IOException;

public class StepDefinitionImplementation extends BaseTest {

    public LandingPage landingPage;
    public ProductCatalogue productCatalogue;
    public CartPage cartPage;
    public PaymentAndShippingPage paymentAndShippingPage;
    public OrderConfirmationPage orderConfirmationPage;

    String countryName = "India";

    @Given("I landed on the e-commerce website")
    public void ILandedOnTheEcommerceWebsite() throws IOException {
        landingPage = launchApplication();
    }

    @Given("^Logged in with username (.+) and password (.+)$")
    public void LoggedInWithUsernameAndPassword(String userName, String password) {
        productCatalogue = landingPage.loginApplication(userName, password);
    }

    @When("^I add product (.+) to cart$")
    public void IAddProductToCart(String productName) {
        productCatalogue.addProductToCart(productName);
    }

    @And("^Checkout (.+) and submit the order$")
    public void checkoutProductNameAndSubmitTheOrder(String productName) throws InterruptedException {

        cartPage = productCatalogue.goToCart();
        Assert.assertTrue(cartPage.checkCart(productName));

        paymentAndShippingPage = cartPage.goToPaymentSection();
        paymentAndShippingPage.selectShippingCountry(countryName);
    }

    @Then("{string} message is displayed on OrderConfirmationPage")
    public void messageIsDisplayedOnOrderConfirmationPage(String confirmationMessage) {

        orderConfirmationPage = paymentAndShippingPage.placeOrder();
        Assert.assertTrue(orderConfirmationPage.getOrderConfirmationMessage().equalsIgnoreCase(confirmationMessage));
        driver.quit();
    }

    @Then("{string} message is displayed")
    public void messageIsDisplayed(String string) {
        Assert.assertEquals(landingPage.getErrorMessage(), "Incorrect email or password.");
        driver.quit();
    }
}
