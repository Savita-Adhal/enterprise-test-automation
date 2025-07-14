import utils.ConfigManager;
import org.testng.Assert;
import org.testng.annotations.Test;

public class FrameworkTest {

    @Test(description = "Verify test framework is working - simple assertion test")
    public void testFrameworkWorking() {
        // Simple test to verify the framework is working
        Assert.assertTrue(true, "Test framework is working correctly");
        System.out.println("Framework test passed successfully!");
    }
    
    @Test(description = "Verify configuration is loaded correctly")
    public void testConfigurationLoading() {
        // Test that configuration is accessible
        String username = ConfigManager.getUsername();
        Assert.assertNotNull(username, "Username should not be null");
        Assert.assertFalse(username.isEmpty(), "Username should not be empty");
        System.out.println("Configuration test passed! Username: " + username);
        
        // Test other configuration properties
        String baseUrl = ConfigManager.getBaseUrl();
        Assert.assertNotNull(baseUrl, "Base URL should not be null");
        System.out.println("Base URL: " + baseUrl);
    }
    
    @Test(description = "Verify configuration properties are accessible")
    public void testConfigurationProperties() {
        // Test various configuration properties
        String loginTitle = ConfigManager.getProperty("app.login.title");
        Assert.assertNotNull(loginTitle, "Login title should not be null");
        System.out.println("Login title: " + loginTitle);
        
        String defaultBrowser = ConfigManager.getDefaultBrowser();
        Assert.assertNotNull(defaultBrowser, "Default browser should not be null");
        System.out.println("Default browser: " + defaultBrowser);
    }
} 