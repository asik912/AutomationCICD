package frameworkPractice.pageObjects;

import frameworkPractice.AbstractComponents.AbstractComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class PaymentAndShippingPage extends AbstractComponent {

    WebDriver driver;

    public PaymentAndShippingPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(css = "[placeholder='Select Country']")
    WebElement countrySelect;

    @FindBy(css = "span[class='ng-star-inserted']")
    List<WebElement> options;

    @FindBy(css = ".action__submit")
    WebElement submit;

    By countryList = By.cssSelector(".ta-results");

    public WebElement getShippingCountry(String countryName) {

        Actions actions = new Actions(driver);
        actions.sendKeys(countrySelect, countryName).build().perform();
        waitTillElementVisible(countryList);

        WebElement desiredOption;
        desiredOption = options.stream().filter(option
                -> option.getText().trim().equals(countryName)).findFirst().orElse(null);
        return desiredOption;
    }

    public void selectShippingCountry(String countryName) {
        getShippingCountry(countryName).click();
    }

    public OrderConfirmationPage placeOrder() {
        submit.click();
        return new OrderConfirmationPage(driver);
    }
}
