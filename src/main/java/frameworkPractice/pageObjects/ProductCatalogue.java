package frameworkPractice.pageObjects;

import frameworkPractice.AbstractComponents.AbstractComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class ProductCatalogue extends AbstractComponent {

    WebDriver driver;

    public ProductCatalogue(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(css = ".mb-3")
    List<WebElement> products;

    @FindBy(css = ".ng-animating")
    WebElement animating;

    By allProducts = By.cssSelector(".mb-3");
    By addToCart = By.cssSelector(".card-body button:last-of-type");
    By productAddedConfirmation = By.id("toast-container");

    public List<WebElement> getProducts() {
        waitTillElementVisible(allProducts);
        return products;
    }

    public WebElement getProductByName(String productName) {
        WebElement desiredProduct;
        desiredProduct = getProducts().stream().filter(product ->
                product.findElement(By.cssSelector("b")).getText().equals(productName)).findFirst().orElse(null);
        return desiredProduct;
    }

    public void addProductToCart(String productName) {
        getProductByName(productName).findElement(addToCart).click();
        waitTillElementVisible(productAddedConfirmation);
        waitTillElementInvisible(animating);
    }
}
