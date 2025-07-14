import base.BaseTest;
import pages.LoginPageFactory;
import pages.DashboardPageFactory;
import utils.ConfigManager;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Demo test class showcasing PageFactory pattern usage
 * Compares traditional Page Object Model vs PageFactory approach
 */
public class PageFactoryDemoTest extends BaseTest {

    @Test(description = "Demo: Login using PageFactory pattern")
    public void testLoginWithPageFactory() {
        // Using PageFactory approach
        LoginPageFactory loginPageFactory = new LoginPageFactory(driver);
        
        // Verify login page is displayed
        Assert.assertTrue(loginPageFactory.isLoginPageDisplayed(), "Login page should be displayed");
        Assert.assertEquals(loginPageFactory.getPageTitle(), ConfigManager.getProperty("app.login.title"), 
                           "Login page title should match expected");
        
        // Perform login using PageFactory
        loginPageFactory.login(ConfigManager.getUsername(), ConfigManager.getPassword());
        
        // Verify successful login
        Assert.assertEquals(driver.getTitle(), ConfigManager.getProperty("app.dashboard.title"), 
                           "Dashboard title should match after successful login");
    }
    
    @Test(description = "Demo: Dashboard navigation using PageFactory pattern")
    public void testDashboardNavigationWithPageFactory() {
        // Login first
        LoginPageFactory loginPageFactory = new LoginPageFactory(driver);
        loginPageFactory.login(ConfigManager.getUsername(), ConfigManager.getPassword());
        
        // Use PageFactory for dashboard
        DashboardPageFactory dashboardPageFactory = new DashboardPageFactory(driver);
        
        // Verify dashboard is loaded
        Assert.assertTrue(dashboardPageFactory.isDashboardLoaded(), "Dashboard should be loaded");
        
        // Verify Curve Manager button is present
        Assert.assertTrue(dashboardPageFactory.isCurveManagerButtonDisplayed(), 
                         "Curve Manager button should be displayed");
        
        // Verify button text
        Assert.assertEquals(dashboardPageFactory.getCurveManagerButtonText(), "Curve Manager", 
                           "Curve Manager button should have correct text");
        
        // Navigate to profile creation using PageFactory
        dashboardPageFactory.navigateToProfileCreation();
        
        // Additional verification can be added here for profile creation page
    }
    
    @Test(description = "Demo: PageFactory element interactions")
    public void testPageFactoryElementInteractions() {
        LoginPageFactory loginPageFactory = new LoginPageFactory(driver);
        
        // Test individual element interactions
        loginPageFactory.enterUsername("test_user");
        loginPageFactory.enterPassword("test_pass");
        
        // Verify form elements are populated
        Assert.assertTrue(loginPageFactory.isLoginFormDisplayed(), "Login form should be displayed");
        
        // Clear form
        loginPageFactory.clearForm();
        
        // Verify logo is displayed
        Assert.assertTrue(loginPageFactory.isLogoDisplayed(), "Logo should be displayed");
    }
    
    @Test(description = "Demo: Dashboard PageFactory features")
    public void testDashboardPageFactoryFeatures() {
        // Login first
        LoginPageFactory loginPageFactory = new LoginPageFactory(driver);
        loginPageFactory.login(ConfigManager.getUsername(), ConfigManager.getPassword());
        
        // Use PageFactory for dashboard
        DashboardPageFactory dashboardPageFactory = new DashboardPageFactory(driver);
        
        // Test various dashboard features
        Assert.assertTrue(dashboardPageFactory.isMainNavigationDisplayed(), "Main navigation should be displayed");
        Assert.assertTrue(dashboardPageFactory.isSideMenuDisplayed(), "Side menu should be displayed");
        
        // Test search functionality
        dashboardPageFactory.performSearch("test search");
        
        // Verify search was performed (you might need to add actual verification based on your app)
        Assert.assertTrue(dashboardPageFactory.isFilterPanelDisplayed(), "Filter panel should be displayed");
    }
    
    @Test(description = "Demo: Comparing traditional vs PageFactory approach")
    public void testCompareApproaches() {
        // Traditional approach (existing)
        pages.LoginPage traditionalLoginPage = new pages.LoginPage(driver);
        
        // PageFactory approach (new)
        LoginPageFactory pageFactoryLoginPage = new LoginPageFactory(driver);
        
        // Both should work the same way
        Assert.assertTrue(traditionalLoginPage.isLoginPageDisplayed(), "Traditional approach: Login page should be displayed");
        Assert.assertTrue(pageFactoryLoginPage.isLoginPageDisplayed(), "PageFactory approach: Login page should be displayed");
        
        // Both should have the same page title
        Assert.assertEquals(traditionalLoginPage.getPageTitle(), pageFactoryLoginPage.getPageTitle(), 
                           "Both approaches should return the same page title");
        
        // Both should be able to perform login
        traditionalLoginPage.login(ConfigManager.getUsername(), ConfigManager.getPassword());
        
        // Verify login was successful
        Assert.assertEquals(driver.getTitle(), ConfigManager.getProperty("app.dashboard.title"), 
                           "Login should be successful with traditional approach");
    }
} 