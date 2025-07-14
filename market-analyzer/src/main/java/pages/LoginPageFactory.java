package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Login Page using PageFactory pattern
 * Demonstrates advanced PageFactory features with @FindBy annotations
 */
public class LoginPageFactory extends BasePageFactory {
    
    // Page elements using @FindBy annotations
    @FindBy(id = "userName")
    private WebElement usernameField;
    
    @FindBy(name = "password")
    private WebElement passwordField;
    
    @FindBy(xpath = "//*[@type='submit']")
    private WebElement loginButton;
    
    @FindBy(css = "form[action*='LoginAction']")
    private WebElement loginForm;
    
    @FindBy(className = "error-message")
    private WebElement errorMessage;
    
    @FindBy(className = "validation-message")
    private WebElement validationMessage;
    
    @FindBy(css = ".logo")
    private WebElement logo;
    
    @FindBy(className = "login-container")
    private WebElement loginContainer;
    
    @FindBy(css = ".welcome-message")
    private WebElement welcomeMessage;
    
    // Expected values
    private static final String LOGIN_PAGE_TITLE = "Zema Enterprise 5.13.0-SNAPSHOT (revision 109202)";
    private static final String DASHBOARD_TITLE = "Zema Enterprise 5.13";
    
    /**
     * Constructor
     * @param driver WebDriver instance
     */
    public LoginPageFactory(WebDriver driver) {
        super(driver);
    }
    
    /**
     * Wait for login page to load
     */
    public void waitForLoginPageToLoad() {
        waitForPageTitle(LOGIN_PAGE_TITLE);
    }
    
    /**
     * Enter username in the username field
     * @param username Username to enter
     */
    public void enterUsername(String username) {
        sendKeys(usernameField, username);
    }
    
    /**
     * Enter password in the password field
     * @param password Password to enter
     */
    public void enterPassword(String password) {
        sendKeys(passwordField, password);
    }
    
    /**
     * Click the login button
     */
    public void clickLoginButton() {
        click(loginButton);
    }
    
    /**
     * Perform complete login action
     * @param username Username to enter
     * @param password Password to enter
     */
    public void login(String username, String password) {
        waitForLoginPageToLoad();
        enterUsername(username);
        enterPassword(password);
        clickLoginButton();
        waitForPageTitle(DASHBOARD_TITLE);
    }
    
    /**
     * Check if login page is displayed
     * @return true if login page is displayed
     */
    public boolean isLoginPageDisplayed() {
        return isElementDisplayed(usernameField) && isElementDisplayed(passwordField);
    }
    
    /**
     * Get the page title
     * @return Current page title
     */
    public String getPageTitle() {
        return driver.getTitle();
    }
    
    /**
     * Get error message text
     * @return Error message text
     */
    public String getErrorMessage() {
        return getText(errorMessage);
    }
    
    /**
     * Get validation message text
     * @return Validation message text
     */
    public String getValidationMessage() {
        return getText(validationMessage);
    }
    
    /**
     * Check if error message is displayed
     * @return true if error message is displayed
     */
    public boolean isErrorMessageDisplayed() {
        return isElementDisplayed(errorMessage);
    }
    
    /**
     * Check if logo is displayed
     * @return true if logo is displayed
     */
    public boolean isLogoDisplayed() {
        return isElementDisplayed(logo);
    }
    
    /**
     * Get welcome message text
     * @return Welcome message text
     */
    public String getWelcomeMessage() {
        return getText(welcomeMessage);
    }
    
    /**
     * Clear all form fields
     */
    public void clearForm() {
        usernameField.clear();
        passwordField.clear();
    }
    
    /**
     * Check if login form is displayed
     * @return true if login form is displayed
     */
    public boolean isLoginFormDisplayed() {
        return isElementDisplayed(loginForm);
    }
} 