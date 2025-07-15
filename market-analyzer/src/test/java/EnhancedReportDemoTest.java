import org.testng.Assert;
import org.testng.annotations.Test;
import utils.TestStepLogger;

/**
 * Demo test class to showcase enhanced reporting with multiple test scenarios
 */
public class EnhancedReportDemoTest {
    
    @Test(description = "Passing test to demonstrate success reporting")
    public void testPassingScenario() {
        TestStepLogger.logTestStart("testPassingScenario", "This test demonstrates successful execution");
        TestStepLogger.logConfiguration("Browser", "Chrome");
        TestStepLogger.logConfiguration("Environment", "Staging");
        
        TestStepLogger.logStep("Step 1: Initialize test data");
        TestStepLogger.logTestData("Username", "testuser");
        TestStepLogger.logTestData("Password", "testpass");
        
        TestStepLogger.logStep("Step 2: Perform test action");
        TestStepLogger.logAction("Navigate to login page");
        TestStepLogger.logElementInteraction("Enter Username", "Username field", "testuser");
        TestStepLogger.logElementInteraction("Enter Password", "Password field", "***");
        TestStepLogger.logElementInteraction("Click Login", "Login button", null);
        
        TestStepLogger.logStep("Step 3: Verify results");
        TestStepLogger.logAssertion("Login Success", "User should be logged in", "Dashboard displayed", true);
        TestStepLogger.logVerification("Dashboard elements are present");
        
        TestStepLogger.logTestComplete("testPassingScenario", "PASS", 1500);
    }
    
    @Test(description = "Failing test to demonstrate failure reporting")
    public void testFailingScenario() {
        TestStepLogger.logTestStart("testFailingScenario", "This test demonstrates failure reporting");
        TestStepLogger.logConfiguration("Browser", "Firefox");
        TestStepLogger.logConfiguration("Environment", "Production");
        
        TestStepLogger.logStep("Step 1: Initialize test data");
        TestStepLogger.logTestData("Invalid Username", "invaliduser");
        TestStepLogger.logTestData("Invalid Password", "invalidpass");
        
        TestStepLogger.logStep("Step 2: Perform test action");
        TestStepLogger.logAction("Navigate to login page");
        TestStepLogger.logElementInteraction("Enter Username", "Username field", "invaliduser");
        TestStepLogger.logElementInteraction("Enter Password", "Password field", "***");
        TestStepLogger.logElementInteraction("Click Login", "Login button", null);
        
        TestStepLogger.logStep("Step 3: Verify results");
        TestStepLogger.logAssertion("Login Failure", "User should not be logged in", "Error message displayed", false);
        
        // This will cause the test to fail
        Assert.fail("Intentional failure to demonstrate error reporting");
    }
    
    @Test(description = "Skipped test to demonstrate skip reporting", enabled = false)
    public void testSkippedScenario() {
        TestStepLogger.logTestStart("testSkippedScenario", "This test demonstrates skip reporting");
        TestStepLogger.logConfiguration("Browser", "Edge");
        TestStepLogger.logConfiguration("Environment", "Development");
        
        TestStepLogger.logStep("This test is disabled to demonstrate skip reporting");
        TestStepLogger.logTestComplete("testSkippedScenario", "SKIP", 500);
    }
    
    @Test(description = "Test with wait operations and detailed logging")
    public void testDetailedLoggingScenario() {
        TestStepLogger.logTestStart("testDetailedLoggingScenario", "This test demonstrates detailed logging");
        TestStepLogger.logConfiguration("Browser", "Safari");
        TestStepLogger.logConfiguration("Environment", "QA");
        
        TestStepLogger.logStep("Step 1: Setup phase");
        TestStepLogger.logSetup("Initialize WebDriver");
        TestStepLogger.logSetup("Load test data");
        TestStepLogger.logWait("Implicit", 10, "Page to load completely");
        
        TestStepLogger.logStep("Step 2: Action phase");
        TestStepLogger.logAction("Navigate to application");
        TestStepLogger.logWait("Explicit", 5, "Element to be clickable");
        TestStepLogger.logElementInteraction("Click Menu", "Main menu", null);
        TestStepLogger.logWait("Explicit", 3, "Submenu to appear");
        
        TestStepLogger.logStep("Step 3: Verification phase");
        TestStepLogger.logVerification("Menu is expanded");
        TestStepLogger.logAssertion("Menu State", "Menu should be expanded", "Menu expanded", true);
        TestStepLogger.logVerification("Submenu items are visible");
        
        TestStepLogger.logStep("Step 4: Cleanup phase");
        TestStepLogger.logCleanup("Close browser");
        TestStepLogger.logCleanup("Clear test data");
        
        TestStepLogger.logTestComplete("testDetailedLoggingScenario", "PASS", 3000);
    }
} 