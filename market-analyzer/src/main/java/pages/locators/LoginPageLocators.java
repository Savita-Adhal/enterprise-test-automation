package pages.locators;

import org.openqa.selenium.By;

/**
 * Locators for Login Page
 * Contains all element locators specific to the login page
 */
public class LoginPageLocators extends BaseLocators {
    
    // Login form elements
    public static final By USERNAME_FIELD = byId("userName");
    public static final By PASSWORD_FIELD = byName("password");
    public static final By LOGIN_BUTTON = byXPath("//*[@type='submit']");
    public static final By LOGIN_FORM = byCssSelector("form[action*='LoginAction']");
    
    // Error messages and validation
    public static final By ERROR_MESSAGE = byClassName("error-message");
    public static final By VALIDATION_MESSAGE = byClassName("validation-message");
    public static final By REQUIRED_FIELD_INDICATOR = byCssSelector(".required-field");
    
    // Remember me and other options
    public static final By REMEMBER_ME_CHECKBOX = byName("rememberMe");
    public static final By FORGOT_PASSWORD_LINK = byLinkText("Forgot Password?");
    public static final By HELP_LINK = byLinkText("Help");
    
    // Page specific elements
    public static final By LOGO = byCssSelector(".logo");
    public static final By LOGIN_CONTAINER = byClassName("login-container");
    public static final By WELCOME_MESSAGE = byCssSelector(".welcome-message");
    
    // Dynamic locators (for elements that might change)
    public static By getErrorMessageByText(String errorText) {
        return byXPath("//div[contains(@class,'error') and contains(text(),'" + errorText + "')]");
    }
    
    public static By getFieldByLabel(String labelText) {
        return byXPath("//label[contains(text(),'" + labelText + "')]/following-sibling::input");
    }
} 