import base.BaseTest;
import pages.LoginPage;
import utils.ConfigManager;
import utils.TestAssertions;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Demo test class showcasing screenshot functionality
 * Demonstrates various screenshot capture scenarios
 */
public class ScreenshotDemoTest extends BaseTest {

    @Test(description = "Demo: Manual screenshot capture during test execution")
    public void testManualScreenshotCapture() {
        LoginPage loginPage = new LoginPage(driver);
        
        // Capture screenshot at the beginning
        String initialScreenshot = captureScreenshot("test_start");
        System.out.println("Initial screenshot saved: " + initialScreenshot);
        
        // Perform some actions
        loginPage.waitForLoginPageToLoad();
        
        // Capture screenshot after login page loads
        String loginPageScreenshot = captureScreenshotWithCustomName("login_page_loaded");
        System.out.println("Login page screenshot saved: " + loginPageScreenshot);
        
        // Verify login page is displayed
        Assert.assertTrue(loginPage.isLoginPageDisplayed(), "Login page should be displayed");
    }
    
    @Test(description = "Demo: Screenshot capture on assertion failure using custom assertions")
    public void testScreenshotOnAssertionFailure() {
        LoginPage loginPage = new LoginPage(driver);
        
        // This will fail and capture screenshot automatically
        TestAssertions.assertEquals(
            loginPage.getPageTitle(), 
            "Wrong Title", 
            "Page title should match expected", 
            driver, 
            "testScreenshotOnAssertionFailure", 
            this.getClass().getSimpleName()
        );
    }
    
    @Test(description = "Demo: Screenshot capture using BaseTest methods")
    public void testBaseTestScreenshotMethods() {
        LoginPage loginPage = new LoginPage(driver);
        
        // Capture screenshot using BaseTest method
        String screenshotPath = captureScreenshot("base_test_method");
        System.out.println("Screenshot captured using BaseTest method: " + screenshotPath);
        
        // Verify login page is displayed
        Assert.assertTrue(loginPage.isLoginPageDisplayed(), "Login page should be displayed");
        
        // Capture screenshot with custom name
        String customScreenshot = captureScreenshotWithCustomName("custom_named_screenshot");
        System.out.println("Custom named screenshot saved: " + customScreenshot);
    }
    
    @Test(description = "Demo: Screenshot capture on manual assertion failure")
    public void testManualAssertionFailureScreenshot() {
        LoginPage loginPage = new LoginPage(driver);
        
        try {
            // This assertion will fail
            Assert.assertEquals(loginPage.getPageTitle(), "Wrong Title", "Page title should match");
        } catch (AssertionError e) {
            // Capture screenshot manually when assertion fails
            String failureScreenshot = captureScreenshotOnAssertionFailure(
                "testManualAssertionFailureScreenshot", 
                "Page title assertion failed"
            );
            System.out.println("Manual failure screenshot saved: " + failureScreenshot);
            throw e; // Re-throw the assertion error
        }
    }
    
    @Test(description = "Demo: Screenshot capture at different test stages")
    public void testScreenshotAtDifferentStages() {
        LoginPage loginPage = new LoginPage(driver);
        
        // Stage 1: Initial page load
        String stage1Screenshot = captureScreenshot("stage1_initial_load");
        System.out.println("Stage 1 screenshot: " + stage1Screenshot);
        
        // Stage 2: After login page verification
        loginPage.waitForLoginPageToLoad();
        String stage2Screenshot = captureScreenshot("stage2_login_page_verified");
        System.out.println("Stage 2 screenshot: " + stage2Screenshot);
        
        // Stage 3: Before login attempt
        loginPage.enterUsername(ConfigManager.getUsername());
        String stage3Screenshot = captureScreenshot("stage3_username_entered");
        System.out.println("Stage 3 screenshot: " + stage3Screenshot);
        
        // Stage 4: After login
        loginPage.enterPassword(ConfigManager.getPassword());
        loginPage.clickLoginButton();
        String stage4Screenshot = captureScreenshot("stage4_after_login");
        System.out.println("Stage 4 screenshot: " + stage4Screenshot);
        
        // Verify successful login
        Assert.assertEquals(driver.getTitle(), ConfigManager.getProperty("app.dashboard.title"), 
                           "Dashboard title should match after successful login");
    }
} 