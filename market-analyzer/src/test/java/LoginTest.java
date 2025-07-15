import base.BaseTest;
import pages.LoginPage;
import utils.ConfigManager;
import utils.TestStepLogger;
import utils.EnhancedScreenshotUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {
    
    private long testStartTime;

    @Test(description = "Verify test framework is working - simple assertion test")
    public void testFrameworkWorking() {
        // Simple test to verify the framework is working
        Assert.assertTrue(true, "Test framework is working correctly");
        System.out.println("LoginTest framework test passed successfully!");
    }
    
    @Test(description = "Verify configuration is loaded correctly")
    public void testConfigurationLoading() {
        // Test that configuration is accessible
        String username = ConfigManager.getUsername();
        Assert.assertNotNull(username, "Username should not be null");
        Assert.assertFalse(username.isEmpty(), "Username should not be empty");
        System.out.println("Configuration test passed! Username: " + username);
    }
    
    @Test(description = "Verify successful login with valid credentials")
    public void testSuccessfulLogin() {
        testStartTime = System.currentTimeMillis();
        TestStepLogger.logTestStart("testSuccessfulLogin", "Verify successful login with valid credentials");
        
        // Log test data
        TestStepLogger.logTestData("Username", ConfigManager.getUsername());
        TestStepLogger.logTestData("Base URL", ConfigManager.getBaseUrl());
        
        // Verify that we're on the correct staging URL
        String currentUrl = driver.getCurrentUrl();
        String expectedUrl = ConfigManager.getBaseUrl();
        TestStepLogger.logStep("URL Verification", "Checking current URL against expected URL", "INFO");
        TestStepLogger.logConfiguration("Current URL", currentUrl);
        TestStepLogger.logConfiguration("Expected URL", expectedUrl);
        
        boolean urlCheck = currentUrl.contains("5-13-staging-oracle.sandbox.zedev.net");
        TestStepLogger.logAssertion("URL Check", "Should be on staging environment", 
                                   currentUrl, urlCheck);
        Assert.assertTrue(urlCheck, "Should be on staging environment");
        
        // Capture screenshot of login page
        EnhancedScreenshotUtils.captureStepScreenshot(driver, "testSuccessfulLogin", "login_page", 
                                                    "LoginTest", "Login page displayed");
        
        LoginPage loginPage = new LoginPage(driver);
        
        // Verify login page is displayed
        TestStepLogger.logStep("Login Page Verification", "Checking if login page is displayed", "INFO");
        boolean loginPageDisplayed = loginPage.isLoginPageDisplayed();
        TestStepLogger.logAssertion("Login Page Displayed", "Login page should be displayed", 
                                   String.valueOf(loginPageDisplayed), loginPageDisplayed);
        Assert.assertTrue(loginPageDisplayed, "Login page should be displayed");
        
        // Verify page title
        String actualTitle = loginPage.getPageTitle();
        String expectedTitle = ConfigManager.getProperty("app.login.title");
        TestStepLogger.logAssertion("Page Title", expectedTitle, actualTitle, 
                                   actualTitle.equals(expectedTitle));
        Assert.assertEquals(actualTitle, expectedTitle, "Login page title should match expected");
        
        // Perform login with credentials
        TestStepLogger.logStep("Login Action", "Attempting to login with provided credentials", "INFO");
        TestStepLogger.logElementInteraction("Login", "Login form", 
                                           "Username: " + ConfigManager.getUsername());
        
        // Capture screenshot before login
        EnhancedScreenshotUtils.captureActionScreenshot(driver, "testSuccessfulLogin", "login", 
                                                      "LoginTest", true);
        
        loginPage.login(ConfigManager.getUsername(), ConfigManager.getPassword());
        
        // Capture screenshot after login
        EnhancedScreenshotUtils.captureActionScreenshot(driver, "testSuccessfulLogin", "login", 
                                                      "LoginTest", false);
        
        // Verify successful login
        TestStepLogger.logStep("Login Verification", "Verifying successful login", "INFO");
        String dashboardTitle = driver.getTitle();
        String expectedDashboardTitle = ConfigManager.getProperty("app.dashboard.title");
        TestStepLogger.logAssertion("Dashboard Title", expectedDashboardTitle, dashboardTitle, 
                                   dashboardTitle.equals(expectedDashboardTitle));
        Assert.assertEquals(dashboardTitle, expectedDashboardTitle, 
                           "Dashboard title should match after successful login");
        
        TestStepLogger.logTestComplete("testSuccessfulLogin", "PASS", System.currentTimeMillis() - testStartTime);
    }
    
    @Test(description = "Verify login page elements are present")
    public void testLoginPageElements() {
        LoginPage loginPage = new LoginPage(driver);
        
        Assert.assertTrue(loginPage.isLoginPageDisplayed(), "Login page should be displayed");
        Assert.assertEquals(loginPage.getPageTitle(), ConfigManager.getProperty("app.login.title"), 
                           "Login page title should match expected");
        
        System.out.println("Login page elements test completed successfully");
    }
}
