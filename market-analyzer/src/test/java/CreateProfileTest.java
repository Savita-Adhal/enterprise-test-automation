import Base.BaseTest;
import pages.LoginPage;
import pages.DashboardPage;
import utils.ConfigManager;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CreateProfileTest extends BaseTest {

    @Test(priority = 1, description = "Verify successful login before profile creation")
    public void testLoginForProfileCreation() {
        LoginPage loginPage = new LoginPage(driver);
        
        // Verify login page is displayed
        Assert.assertTrue(loginPage.isLoginPageDisplayed(), "Login page should be displayed");
        
        // Perform login
        loginPage.login(ConfigManager.getUsername(), ConfigManager.getPassword());
        
        // Verify successful login
        Assert.assertEquals(driver.getTitle(), ConfigManager.getProperty("app.dashboard.title"), 
                           "Dashboard title should match after successful login");
    }

    @Test(priority = 2, description = "Verify navigation to profile creation")
    public void testNavigateToProfileCreation() {
        DashboardPage dashboardPage = new DashboardPage(driver);
        
        // Verify dashboard is loaded
        Assert.assertTrue(dashboardPage.isDashboardLoaded(), "Dashboard should be loaded");
        
        // Verify Curve Manager button is present
        Assert.assertTrue(dashboardPage.isCurveManagerButtonDisplayed(), 
                         "Curve Manager button should be displayed");
        
        // Verify button text
        Assert.assertEquals(dashboardPage.getCurveManagerButtonText(), "Curve Manager", 
                           "Curve Manager button should have correct text");
        
        // Navigate to profile creation
        dashboardPage.navigateToProfileCreation();
        
        // Additional verification can be added here for profile creation page
    }
}
