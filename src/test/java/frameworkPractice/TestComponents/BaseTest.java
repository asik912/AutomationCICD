package frameworkPractice.TestComponents;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import frameworkPractice.pageObjects.LandingPage;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

public class BaseTest {

    public WebDriver driver;
    public LandingPage landingPage;

    public WebDriver openBrowser() throws IOException {

        Properties prop = new Properties();
        FileInputStream fis = new FileInputStream(System.getProperty("user.dir")
                + "//src//main//java//frameworkPractice//Resources//GlobalData.properties");
        prop.load(fis);

        String browserName = System.getProperty("browser") != null ?
                System.getProperty("browser") : prop.getProperty("browser");

        if (browserName.contains("chrome")) {

            ChromeOptions options = new ChromeOptions();

            if (browserName.contains("headless")) {
                options.addArguments("headless");
            }
            driver = new ChromeDriver(options);
            driver.manage().window().setSize(new Dimension(1440, 900));

        } else if (browserName.equalsIgnoreCase("firefox")) {
            driver = new FirefoxDriver();
        } else if (browserName.equalsIgnoreCase("edge")) {
            driver = new EdgeDriver();
        }
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        return driver;
    }

    public List<HashMap<String, String>> getDataFromJsonFile(String filePath) throws IOException {

        String jsonData = FileUtils.readFileToString(new File(filePath), StandardCharsets.UTF_8);
        ObjectMapper mapper = new ObjectMapper();

        return mapper.readValue(jsonData, new TypeReference<List<HashMap<String, String>>>() {});
    }

    public String getScreenShotOfFailedTestCase(String testCaseName, WebDriver driver) throws IOException {

        File image = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(image, new File
                (System.getProperty("user.dir") + "//testReports//" + testCaseName + ".png"));

        return System.getProperty("user.dir") + "//testReports//" + testCaseName + ".png";
    }

    @BeforeMethod (alwaysRun = true)
    public LandingPage launchApplication() throws IOException {

        driver = openBrowser();
        landingPage = new LandingPage(driver);
        landingPage.goToHomePage();

        return landingPage;
    }

    @AfterMethod (alwaysRun = true)
    public void closeBrowser() {
        driver.quit();
    }
}
