package frameworkPractice.Tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.List;

public class StandAloneTest {

    public static void main(String[] args) {

        String productName = "ZARA COAT 3";
        String countryName = "India";

        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();

        driver.get("https://rahulshettyacademy.com/client/");

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        driver.findElement(By.id("userEmail")).sendKeys("oliverfein@gmail.com");
        driver.findElement(By.id("userPassword")).sendKeys("Oliver@123");
        driver.findElement(By.id("login")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".mb-3")));

        List<WebElement> products = driver.findElements(By.cssSelector(".mb-3"));

        WebElement desiredProduct = products.stream().filter(product ->
                product.findElement(By.cssSelector("b")).getText().equals(productName)).findFirst().orElse(null);

        desiredProduct.findElement(By.cssSelector(".card-body button:last-of-type")).click();

        wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.cssSelector(".ng-animating"))));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("toast-container")));

        driver.findElement(By.cssSelector("[routerlink*='cart']")).click();

        List<WebElement> cartList = driver.findElements(By.cssSelector(".cartSection h3"));

        Boolean hasMatch = cartList.stream().anyMatch(product -> product.getText().equals(productName));
        Assert.assertTrue(hasMatch);

        driver.findElement(By.cssSelector(".totalRow button")).click();

//        driver.findElement(By.cssSelector("[placeholder='Select Country']")).sendKeys(countryName);

        Actions actions = new Actions(driver);
        actions.sendKeys(driver.findElement(By.cssSelector("[placeholder='Select Country']")), countryName).build().perform();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".ta-results")));

        List<WebElement> options = driver.findElements(By.cssSelector("span[class='ng-star-inserted']"));
        WebElement desiredOption = options.stream().filter(option
                -> option.getText().trim().equals(countryName)).findFirst().orElse(null);
        desiredOption.click();

        driver.findElement(By.cssSelector(".action__submit")).click();

        String confirmationMessage = driver.findElement(By.cssSelector(".hero-primary")).getText();
        Assert.assertTrue(confirmationMessage.equalsIgnoreCase("Thankyou for the order."));
    }
}
