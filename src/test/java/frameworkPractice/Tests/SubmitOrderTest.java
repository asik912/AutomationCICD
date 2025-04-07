package frameworkPractice.Tests;

import frameworkPractice.TestComponents.BaseTest;
import frameworkPractice.pageObjects.*;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class SubmitOrderTest extends BaseTest {

    @Test (dataProvider = "getJsonData")
    public void submitOrderTest(HashMap<String, String> input) throws InterruptedException {

        String countryName = "India";
        String confirmationMessage = "Thankyou for the order.";

        ProductCatalogue productCatalogue = landingPage.loginApplication
                (input.get("email"), input.get("password"));
        productCatalogue.addProductToCart(input.get("productName"));

        CartPage cartPage = productCatalogue.goToCart();
        Assert.assertTrue(cartPage.checkCart(input.get("productName")));

        PaymentAndShippingPage paymentAndShippingPage = cartPage.goToPaymentSection();
        paymentAndShippingPage.selectShippingCountry(countryName);

        OrderConfirmationPage orderConfirmationPage = paymentAndShippingPage.placeOrder();
        Assert.assertTrue(orderConfirmationPage.getOrderConfirmationMessage().equalsIgnoreCase(confirmationMessage));
    }

    @Test (dependsOnMethods = "submitOrderTest", dataProvider = "getData", groups = "PurchaseOrder")
    public void checkOrderTest(String email, String password, String productName) {

        ProductCatalogue productCatalogue = landingPage.loginApplication
                (email, password);
        OrdersPage ordersPage = productCatalogue.goToOrdersPage();
        Assert.assertTrue(ordersPage.verifyOrders(productName));
    }

    @DataProvider
    public Object[][] getJsonData() throws IOException {

        List<HashMap<String, String>> data = getDataFromJsonFile(System.getProperty("user.dir")
                + "//src//test//java//frameworkPractice//TestData//PurchaseOrderData.json");

        return new Object[][] {{data.get(0)}, {data.get(1)}};
    }

    @DataProvider
    public Object[][] getMapData() {

        HashMap<String, String> data = new HashMap<>();
        data.put("email", "oliverfein@gmail.com");
        data.put("password", "Oliver@123");
        data.put("productName", "ZARA COAT 3");

        HashMap<String, String> data1 = new HashMap<>();
        data1.put("email", "johnleo@gmail.com");
        data1.put("password", "John@123");
        data1.put("productName", "ADIDAS ORIGINAL");


        return new Object[][] {{data}, {data1}};
    }

    @DataProvider
    public Object[][] getData() {
        return new Object[][] {{"oliverfein@gmail.com", "Oliver@123", "ZARA COAT 3"},
                {"johnleo@gmail.com", "John@123", "ADIDAS ORIGINAL"}};
    }

    //Adding comments here to check webhook trigger in 'Continuous Integration/Continuous Delivery'
}
