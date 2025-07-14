import Base.BaseTest;
import pages.LoginPage;
import utils.ConfigManager;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    @Test(description = "Verify successful login with valid credentials")
    public void testSuccessfulLogin() {
        LoginPage loginPage = new LoginPage(driver);
        
        // Verify login page is displayed
        Assert.assertTrue(loginPage.isLoginPageDisplayed(), "Login page should be displayed");
        Assert.assertEquals(loginPage.getPageTitle(), ConfigManager.getProperty("app.login.title"), 
                           "Login page title should match expected");
        
        // Perform login
        loginPage.login(ConfigManager.getUsername(), ConfigManager.getPassword());
        
        // Verify successful login
        Assert.assertEquals(driver.getTitle(), ConfigManager.getProperty("app.dashboard.title"), 
                           "Dashboard title should match after successful login");
    }
    
    @Test(description = "Verify login page elements are present")
    public void testLoginPageElements() {
        LoginPage loginPage = new LoginPage(driver);
        
        Assert.assertTrue(loginPage.isLoginPageDisplayed(), "Login page should be displayed");
        Assert.assertEquals(loginPage.getPageTitle(), ConfigManager.getProperty("app.login.title"), 
                           "Login page title should match expected");
    }
}
