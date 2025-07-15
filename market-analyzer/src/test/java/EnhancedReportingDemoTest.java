import base.BaseTest;
import pages.LoginPage;
import pages.DashboardPage;
import utils.ConfigManager;
import utils.TestStepLogger;
import utils.EnhancedScreenshotUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Demo test class showcasing enhanced reporting features
 * This test demonstrates detailed step logging, screenshots, and comprehensive reporting
 */
public class EnhancedReportingDemoTest extends BaseTest {
    
    private long testStartTime;
    
    @Test(description = "Enhanced demo test with detailed step logging and screenshots")
    public void testEnhancedReportingDemo() {
        testStartTime = System.currentTimeMillis();
        TestStepLogger.logTestStart("testEnhancedReportingDemo", "Enhanced demo test with detailed step logging and screenshots");
        
        try {
            // Step 1: Log test configuration
            TestStepLogger.logStep("Test Configuration", "Logging test configuration details", "INFO");
            TestStepLogger.logConfiguration("Browser", "Edge");
            TestStepLogger.logConfiguration("Base URL", ConfigManager.getBaseUrl());
            TestStepLogger.logConfiguration("Username", ConfigManager.getUsername());
            
            // Step 2: Verify initial page load
            TestStepLogger.logStep("Page Load Verification", "Verifying initial page load", "INFO");
            String currentUrl = driver.getCurrentUrl();
            TestStepLogger.logConfiguration("Current URL", currentUrl);
            
            boolean urlValid = currentUrl.contains("zedev.net");
            TestStepLogger.logAssertion("URL Validation", "URL should contain zedev.net", currentUrl, urlValid);
            Assert.assertTrue(urlValid, "URL should contain zedev.net");
            
            // Step 3: Capture initial page screenshot
            EnhancedScreenshotUtils.captureStepScreenshot(driver, "testEnhancedReportingDemo", "initial_page", 
                                                        "EnhancedReportingDemoTest", "Initial page loaded");
            
            // Step 4: Initialize page objects
            TestStepLogger.logStep("Page Object Initialization", "Initializing page objects", "INFO");
            LoginPage loginPage = new LoginPage(driver);
            TestStepLogger.logStep("Login Page Object", "LoginPage object created successfully", "PASS");
            
            // Step 5: Verify login page elements
            TestStepLogger.logStep("Login Page Elements", "Verifying login page elements", "INFO");
            boolean loginPageDisplayed = loginPage.isLoginPageDisplayed();
            TestStepLogger.logAssertion("Login Page Displayed", "Login page should be displayed", 
                                       String.valueOf(loginPageDisplayed), loginPageDisplayed);
            Assert.assertTrue(loginPageDisplayed, "Login page should be displayed");
            
            // Step 6: Verify page title
            String actualTitle = loginPage.getPageTitle();
            String expectedTitle = ConfigManager.getProperty("app.login.title");
            TestStepLogger.logAssertion("Page Title", expectedTitle, actualTitle, 
                                       actualTitle.equals(expectedTitle));
            Assert.assertEquals(actualTitle, expectedTitle, "Login page title should match expected");
            
            // Step 7: Capture pre-login screenshot
            EnhancedScreenshotUtils.captureActionScreenshot(driver, "testEnhancedReportingDemo", "login", 
                                                          "EnhancedReportingDemoTest", true);
            
            // Step 8: Perform login action
            TestStepLogger.logStep("Login Action", "Performing login with credentials", "INFO");
            TestStepLogger.logElementInteraction("Enter Username", "Username field", ConfigManager.getUsername());
            TestStepLogger.logElementInteraction("Enter Password", "Password field", "***");
            TestStepLogger.logElementInteraction("Click Login", "Login button", null);
            
            loginPage.login(ConfigManager.getUsername(), ConfigManager.getPassword());
            
            // Step 9: Capture post-login screenshot
            EnhancedScreenshotUtils.captureActionScreenshot(driver, "testEnhancedReportingDemo", "login", 
                                                          "EnhancedReportingDemoTest", false);
            
            // Step 10: Verify successful login
            TestStepLogger.logStep("Login Verification", "Verifying successful login", "INFO");
            String dashboardTitle = driver.getTitle();
            String expectedDashboardTitle = ConfigManager.getProperty("app.dashboard.title");
            TestStepLogger.logAssertion("Dashboard Title", expectedDashboardTitle, dashboardTitle, 
                                       dashboardTitle.equals(expectedDashboardTitle));
            Assert.assertEquals(dashboardTitle, expectedDashboardTitle, 
                               "Dashboard title should match after successful login");
            
            // Step 11: Initialize dashboard page
            TestStepLogger.logStep("Dashboard Page", "Initializing dashboard page object", "INFO");
            DashboardPage dashboardPage = new DashboardPage(driver);
            TestStepLogger.logStep("Dashboard Page Object", "DashboardPage object created successfully", "PASS");
            
            // Step 12: Verify dashboard elements
            TestStepLogger.logStep("Dashboard Elements", "Verifying dashboard page elements", "INFO");
            boolean dashboardLoaded = dashboardPage.isDashboardLoaded();
            TestStepLogger.logAssertion("Dashboard Loaded", "Dashboard should be loaded", 
                                       String.valueOf(dashboardLoaded), dashboardLoaded);
            Assert.assertTrue(dashboardLoaded, "Dashboard should be loaded");
            
            // Step 13: Capture final dashboard screenshot
            EnhancedScreenshotUtils.captureStepScreenshot(driver, "testEnhancedReportingDemo", "dashboard", 
                                                        "EnhancedReportingDemoTest", "Dashboard page displayed");
            
            // Step 14: Log test completion
            TestStepLogger.logTestComplete("testEnhancedReportingDemo", "PASS", 
                                         System.currentTimeMillis() - testStartTime);
            
        } catch (Exception e) {
            // Step 15: Handle exceptions with detailed logging
            TestStepLogger.logError("Test Exception", e.getMessage());
            EnhancedScreenshotUtils.captureFailureScreenshot(driver, "testEnhancedReportingDemo", 
                                                           "EnhancedReportingDemoTest", e.getMessage());
            TestStepLogger.logTestComplete("testEnhancedReportingDemo", "FAIL", 
                                         System.currentTimeMillis() - testStartTime);
            throw e;
        }
    }
    
    @Test(description = "Demo test with intentional failure to showcase error reporting")
    public void testFailureDemo() {
        testStartTime = System.currentTimeMillis();
        TestStepLogger.logTestStart("testFailureDemo", "Demo test with intentional failure to showcase error reporting");
        
        try {
            // Step 1: Log test start
            TestStepLogger.logStep("Test Start", "Starting failure demo test", "INFO");
            
            // Step 2: Capture initial screenshot
            EnhancedScreenshotUtils.captureStepScreenshot(driver, "testFailureDemo", "initial_state", 
                                                        "EnhancedReportingDemoTest", "Initial page state");
            
            // Step 3: Perform some actions
            TestStepLogger.logStep("Page Navigation", "Navigating to test page", "INFO");
            String currentUrl = driver.getCurrentUrl();
            TestStepLogger.logConfiguration("Current URL", currentUrl);
            
            // Step 4: Intentional failure for demo purposes
            TestStepLogger.logStep("Intentional Failure", "Performing intentional failure for demo", "INFO");
            TestStepLogger.logWarning("This is an intentional failure for demonstration purposes");
            
            // This will cause the test to fail
            Assert.fail("Intentional failure to demonstrate error reporting and screenshot capture");
            
        } catch (Exception e) {
            // Step 5: Capture failure screenshot
            TestStepLogger.logError("Test Failure", e.getMessage());
            EnhancedScreenshotUtils.captureFailureScreenshot(driver, "testFailureDemo", 
                                                           "EnhancedReportingDemoTest", e.getMessage());
            TestStepLogger.logTestComplete("testFailureDemo", "FAIL", 
                                         System.currentTimeMillis() - testStartTime);
            throw e;
        }
    }
    
    @Test(description = "Demo test with multiple steps and wait operations")
    public void testMultiStepDemo() {
        testStartTime = System.currentTimeMillis();
        
        // Debug: Check if listener is available
        System.out.println("=== DEBUG: Test starting ===");
        System.out.println("=== DEBUG: Looking for EnhancedTestListener ===");
        
        TestStepLogger.logTestStart("testMultiStepDemo", "Demo test with multiple steps and wait operations");
        
        try {
            // Step 1: Initial setup
            TestStepLogger.logStep("Setup", "Initial test setup", "INFO");
            TestStepLogger.logConfiguration("Test Environment", "Staging");
            TestStepLogger.logConfiguration("Test Data", "Demo data loaded");
            
            // Step 2: Page load verification
            TestStepLogger.logStep("Page Load", "Verifying page load", "INFO");
            TestStepLogger.logWait("Implicit", 10, "Page to load completely");
            
            String currentUrl = driver.getCurrentUrl();
            TestStepLogger.logConfiguration("Current URL", currentUrl);
            
            // Step 3: Element verification
            TestStepLogger.logStep("Element Verification", "Verifying page elements", "INFO");
            LoginPage loginPage = new LoginPage(driver);
            
            boolean elementsPresent = loginPage.isLoginPageDisplayed();
            TestStepLogger.logAssertion("Elements Present", "Page elements should be present", 
                                       String.valueOf(elementsPresent), elementsPresent);
            Assert.assertTrue(elementsPresent, "Page elements should be present");
            
            // Step 4: Screenshot capture
            EnhancedScreenshotUtils.captureStepScreenshot(driver, "testMultiStepDemo", "elements_verified", 
                                                        "EnhancedReportingDemoTest", "Page elements verified");
            
            // Step 5: Wait operation demo
            TestStepLogger.logStep("Wait Operation", "Demonstrating wait operations", "INFO");
            TestStepLogger.logWait("Explicit", 5, "Page to be fully interactive");
            
            // Step 6: Final verification
            TestStepLogger.logStep("Final Verification", "Performing final verifications", "INFO");
            String pageTitle = driver.getTitle();
            TestStepLogger.logAssertion("Page Title", "Page should have a title", pageTitle, !pageTitle.isEmpty());
            Assert.assertFalse(pageTitle.isEmpty(), "Page should have a title");
            
            // Step 7: Test completion
            TestStepLogger.logTestComplete("testMultiStepDemo", "PASS", 
                                         System.currentTimeMillis() - testStartTime);
            
            System.out.println("=== DEBUG: Test completed successfully ===");
            
        } catch (Exception e) {
            TestStepLogger.logError("Test Exception", e.getMessage());
            EnhancedScreenshotUtils.captureFailureScreenshot(driver, "testMultiStepDemo", 
                                                           "EnhancedReportingDemoTest", e.getMessage());
            TestStepLogger.logTestComplete("testMultiStepDemo", "FAIL", 
                                         System.currentTimeMillis() - testStartTime);
            System.out.println("=== DEBUG: Test failed with exception: " + e.getMessage() + " ===");
            throw e;
        }
    }
} 