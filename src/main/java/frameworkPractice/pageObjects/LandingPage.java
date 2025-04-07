package frameworkPractice.pageObjects;

import frameworkPractice.AbstractComponents.AbstractComponent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LandingPage extends AbstractComponent {

    WebDriver driver;

    public LandingPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(id = "userEmail")
    WebElement emailElement;

    @FindBy(id = "userPassword")
    WebElement passwordElement;

    @FindBy(id = "login")
    WebElement loginElement;

    @FindBy(css = ".toast-error")
    WebElement errorMessage;

    public ProductCatalogue loginApplication(String email, String password) {
        emailElement.sendKeys(email);
        passwordElement.sendKeys(password);
        loginElement.click();
        return new ProductCatalogue(driver);
    }

    public void goToHomePage() {
        driver.get("https://rahulshettyacademy.com/client/");
    }

    public String getErrorMessage() {
        waitTillElementVisible(errorMessage);
        return errorMessage.getText();
    }
}
