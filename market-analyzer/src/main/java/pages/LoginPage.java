package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {
    
    // Locators
    private static final By USERNAME_FIELD = By.id("userName");
    private static final By PASSWORD_FIELD = By.name("password");
    private static final By LOGIN_BUTTON = By.xpath("//*[@type='submit']");
    private static final By LOGIN_TITLE = By.tagName("title");
    
    // Expected values
    private static final String LOGIN_PAGE_TITLE = "Zema Enterprise 5.13.0-SNAPSHOT (revision 109202)";
    private static final String DASHBOARD_TITLE = "Zema Enterprise 5.13";
    
    public LoginPage(WebDriver driver) {
        super(driver);
    }
    
    public void waitForLoginPageToLoad() {
        waitForPageTitle(LOGIN_PAGE_TITLE);
    }
    
    public void enterUsername(String username) {
        sendKeys(USERNAME_FIELD, username);
    }
    
    public void enterPassword(String password) {
        sendKeys(PASSWORD_FIELD, password);
    }
    
    public void clickLoginButton() {
        click(LOGIN_BUTTON);
    }
    
    public void login(String username, String password) {
        waitForLoginPageToLoad();
        enterUsername(username);
        enterPassword(password);
        clickLoginButton();
        waitForPageTitle(DASHBOARD_TITLE);
    }
    
    public boolean isLoginPageDisplayed() {
        return isElementDisplayed(USERNAME_FIELD) && isElementDisplayed(PASSWORD_FIELD);
    }
    
    public String getPageTitle() {
        return driver.getTitle();
    }
} 