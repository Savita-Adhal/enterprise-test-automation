package base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Optional;
import utils.ConfigManager;
import utils.ScreenshotUtils;

import java.time.Duration;

public class BaseTest {

    protected WebDriver driver;
    protected WebDriverWait wait;
    private static final int IMPLICIT_WAIT = 10;
    private static final int EXPLICIT_WAIT = 20;

    @Parameters({"browser"})
    @BeforeTest
    public void setUp(@Optional("firefox") String browserName) {
        try {
            driver = createDriver(browserName);
            setupDriver();
            wait = new WebDriverWait(driver, EXPLICIT_WAIT);
        } catch (Exception e) {
            System.err.println("Failed to initialize WebDriver: " + e.getMessage());
            throw new RuntimeException("WebDriver initialization failed", e);
        }
    }

    private WebDriver createDriver(String browserName) {
        switch (browserName.toLowerCase()) {
            case "chrome":
                WebDriverManager.chromedriver().driverVersion("latest").setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--headless");
                chromeOptions.addArguments("--no-sandbox");
                chromeOptions.addArguments("--disable-dev-shm-usage");
                chromeOptions.addArguments("--disable-gpu");
                chromeOptions.addArguments("--window-size=1920,1080");
                chromeOptions.addArguments("--disable-notifications");
                chromeOptions.addArguments("--disable-popup-blocking");
                return new ChromeDriver(chromeOptions);
                
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.addArguments("--start-maximized");
                return new FirefoxDriver(firefoxOptions);
                
            default:
                throw new IllegalArgumentException("Unsupported browser: " + browserName);
        }
    }

    private void setupDriver() {
        driver.manage().deleteAllCookies();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(IMPLICIT_WAIT, java.util.concurrent.TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(30, java.util.concurrent.TimeUnit.SECONDS);
        
        // Open the staging URL
        String baseUrl = ConfigManager.getBaseUrl();
        System.out.println("Opening staging URL: " + baseUrl);
        driver.get(baseUrl);
        System.out.println("Successfully opened URL: " + driver.getCurrentUrl());
    }

    @AfterTest
    public void tearDown() {
        if (driver != null) {
            try {
                driver.quit();
            } catch (Exception e) {
                System.err.println("Error closing WebDriver: " + e.getMessage());
            }
        }
    }
    
    /**
     * Captures a screenshot with the current test method name
     * @param testMethodName Name of the current test method
     * @return Path to the saved screenshot, or null if failed
     */
    protected String captureScreenshot(String testMethodName) {
        return ScreenshotUtils.captureScreenshot(driver, testMethodName, this.getClass().getSimpleName());
    }
    
    /**
     * Captures a screenshot with a custom name
     * @param customName Custom name for the screenshot
     * @return Path to the saved screenshot, or null if failed
     */
    protected String captureScreenshotWithCustomName(String customName) {
        return ScreenshotUtils.captureScreenshot(driver, customName);
    }
    
    /**
     * Captures a screenshot when an assertion fails
     * @param testMethodName Name of the current test method
     * @param assertionMessage Message describing what was being tested
     * @return Path to the saved screenshot, or null if failed
     */
    protected String captureScreenshotOnAssertionFailure(String testMethodName, String assertionMessage) {
        String screenshotPath = captureScreenshot(testMethodName + "_assertion_failure");
        if (screenshotPath != null) {
            System.err.println("Assertion failed: " + assertionMessage);
            System.err.println("Screenshot saved: " + screenshotPath);
        }
        return screenshotPath;
    }
    
    /**
     * Explicitly opens the staging URL - can be called from test methods if needed
     */
    protected void openStagingUrl() {
        String baseUrl = ConfigManager.getBaseUrl();
        System.out.println("Explicitly opening staging URL: " + baseUrl);
        driver.get(baseUrl);
        System.out.println("Successfully opened staging URL: " + driver.getCurrentUrl());
    }
} 