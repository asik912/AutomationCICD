package frameworkPractice.pageObjects;

import frameworkPractice.AbstractComponents.AbstractComponent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class CartPage extends AbstractComponent {

    WebDriver driver;

    public CartPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(css = ".cartSection h3")
    List<WebElement> cartList;

    @FindBy(css = ".totalRow button")
    WebElement checkOutButton;

    public boolean checkCart(String productName) {
        boolean hasMatch;
        hasMatch = cartList.stream().anyMatch(product -> product.getText().equals(productName));
        return hasMatch;
    }

    public PaymentAndShippingPage goToPaymentSection() {
        checkOutButton.click();
        return new PaymentAndShippingPage(driver);
    }
}
