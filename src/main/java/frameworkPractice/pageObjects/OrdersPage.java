package frameworkPractice.pageObjects;

import frameworkPractice.AbstractComponents.AbstractComponent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class OrdersPage extends AbstractComponent {

    WebDriver driver;

    public OrdersPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//tr/td[2]")
    List<WebElement> orders;

    public boolean verifyOrders(String productName) {
        boolean hasMatch;
        hasMatch = orders.stream().anyMatch(product -> product.getText().equals(productName));
        return hasMatch;
    }
}
