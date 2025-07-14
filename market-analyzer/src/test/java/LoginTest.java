import base.BaseTest;
import pages.LoginPage;
import utils.ConfigManager;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

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
        LoginPage loginPage = new LoginPage(driver);
        
        // Verify login page is displayed
        Assert.assertTrue(loginPage.isLoginPageDisplayed(), "Login page should be displayed");
        Assert.assertEquals(loginPage.getPageTitle(), ConfigManager.getProperty("app.login.title"), 
                           "Login page title should match expected");
        
        // Perform login with new credentials
        loginPage.login(ConfigManager.getUsername(), ConfigManager.getPassword());
        
        // Verify successful login
        Assert.assertEquals(driver.getTitle(), ConfigManager.getProperty("app.dashboard.title"), 
                           "Dashboard title should match after successful login");
        
        System.out.println("Login test completed successfully with credentials: " + ConfigManager.getUsername());
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
