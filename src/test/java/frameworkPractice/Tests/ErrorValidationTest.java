package frameworkPractice.Tests;

import frameworkPractice.TestComponents.BaseTest;
import frameworkPractice.TestComponents.RetryTestCase;
import frameworkPractice.pageObjects.CartPage;
import frameworkPractice.pageObjects.ProductCatalogue;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ErrorValidationTest extends BaseTest {

    @Test (groups = {"ErrorHandling"}, retryAnalyzer = RetryTestCase.class)
    public void LoginWithWrongCredentialsTest() {

        landingPage.loginApplication("oliverfeinm@gmail.com", "Oliver@123");
        Assert.assertEquals(landingPage.getErrorMessage(), "Incorrect email or password.");
    }

    @Test
    public void ProductValidationTest() throws InterruptedException {

        String productName = "ZARA COAT 3";

        ProductCatalogue productCatalogue = landingPage.loginApplication("oliverfein@gmail.com", "Oliver@123");
        productCatalogue.addProductToCart(productName);

        CartPage cartPage = productCatalogue.goToCart();
        Assert.assertFalse(cartPage.checkCart("ZARA COAT 33"));
    }
}
