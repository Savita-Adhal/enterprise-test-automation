package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Dashboard Page using PageFactory pattern
 * Demonstrates advanced PageFactory features with @FindBy annotations
 */
public class DashboardPageFactory extends BasePageFactory {
    
    // Page elements using @FindBy annotations
    @FindBy(id = "button-1483-btnInnerEl")
    private WebElement curveManagerButton;
    
    @FindBy(id = "appmenuitem-1487-textEl")
    private WebElement profileMenuItem;
    
    @FindBy(className = "main-navigation")
    private WebElement mainNavigation;
    
    @FindBy(css = ".user-profile-dropdown")
    private WebElement userProfileDropdown;
    
    @FindBy(linkText = "Logout")
    private WebElement logoutButton;
    
    @FindBy(linkText = "Settings")
    private WebElement settingsLink;
    
    @FindBy(css = ".user-avatar")
    private WebElement userAvatar;
    
    @FindBy(className = "dashboard-container")
    private WebElement dashboardContainer;
    
    @FindBy(css = ".welcome-message")
    private WebElement welcomeMessage;
    
    @FindBy(className = "quick-actions")
    private WebElement quickActionsPanel;
    
    @FindBy(className = "side-menu")
    private WebElement sideMenu;
    
    @FindBy(css = ".menu-item")
    private WebElement menuItems;
    
    @FindBy(css = ".sub-menu-item")
    private WebElement subMenuItems;
    
    @FindBy(className = "notification-panel")
    private WebElement notificationPanel;
    
    @FindBy(className = "alert-message")
    private WebElement alertMessage;
    
    @FindBy(css = ".success-message")
    private WebElement successMessage;
    
    @FindBy(css = ".search-box input")
    private WebElement searchBox;
    
    @FindBy(css = ".search-box button")
    private WebElement searchButton;
    
    @FindBy(className = "filter-panel")
    private WebElement filterPanel;
    
    // Expected values
    private static final String CURVE_MANAGER_TEXT = "Curve Manager";
    
    /**
     * Constructor
     * @param driver WebDriver instance
     */
    public DashboardPageFactory(WebDriver driver) {
        super(driver);
    }
    
    /**
     * Check if Curve Manager button is displayed
     * @return true if Curve Manager button is displayed
     */
    public boolean isCurveManagerButtonDisplayed() {
        return isElementDisplayed(curveManagerButton);
    }
    
    /**
     * Get Curve Manager button text
     * @return Curve Manager button text
     */
    public String getCurveManagerButtonText() {
        return getText(curveManagerButton);
    }
    
    /**
     * Click Curve Manager button
     */
    public void clickCurveManagerButton() {
        click(curveManagerButton);
    }
    
    /**
     * Click Profile menu item
     */
    public void clickProfileMenuItem() {
        click(profileMenuItem);
    }
    
    /**
     * Navigate to profile creation
     */
    public void navigateToProfileCreation() {
        if (CURVE_MANAGER_TEXT.equals(getCurveManagerButtonText())) {
            clickCurveManagerButton();
            clickProfileMenuItem();
        } else {
            throw new RuntimeException("Curve Manager button not found or has unexpected text");
        }
    }
    
    /**
     * Check if dashboard is loaded
     * @return true if dashboard is loaded
     */
    public boolean isDashboardLoaded() {
        return driver.getTitle().contains("Zema Enterprise 5.13");
    }
    
    /**
     * Click logout button
     */
    public void clickLogout() {
        click(logoutButton);
    }
    
    /**
     * Click settings link
     */
    public void clickSettings() {
        click(settingsLink);
    }
    
    /**
     * Check if user avatar is displayed
     * @return true if user avatar is displayed
     */
    public boolean isUserAvatarDisplayed() {
        return isElementDisplayed(userAvatar);
    }
    
    /**
     * Get welcome message text
     * @return Welcome message text
     */
    public String getWelcomeMessage() {
        return getText(welcomeMessage);
    }
    
    /**
     * Check if quick actions panel is displayed
     * @return true if quick actions panel is displayed
     */
    public boolean isQuickActionsPanelDisplayed() {
        return isElementDisplayed(quickActionsPanel);
    }
    
    /**
     * Enter search term
     * @param searchTerm Search term to enter
     */
    public void enterSearchTerm(String searchTerm) {
        sendKeys(searchBox, searchTerm);
    }
    
    /**
     * Click search button
     */
    public void clickSearchButton() {
        click(searchButton);
    }
    
    /**
     * Perform search
     * @param searchTerm Search term to search for
     */
    public void performSearch(String searchTerm) {
        enterSearchTerm(searchTerm);
        clickSearchButton();
    }
    
    /**
     * Check if notification panel is displayed
     * @return true if notification panel is displayed
     */
    public boolean isNotificationPanelDisplayed() {
        return isElementDisplayed(notificationPanel);
    }
    
    /**
     * Get alert message text
     * @return Alert message text
     */
    public String getAlertMessage() {
        return getText(alertMessage);
    }
    
    /**
     * Get success message text
     * @return Success message text
     */
    public String getSuccessMessage() {
        return getText(successMessage);
    }
    
    /**
     * Check if side menu is displayed
     * @return true if side menu is displayed
     */
    public boolean isSideMenuDisplayed() {
        return isElementDisplayed(sideMenu);
    }
    
    /**
     * Check if main navigation is displayed
     * @return true if main navigation is displayed
     */
    public boolean isMainNavigationDisplayed() {
        return isElementDisplayed(mainNavigation);
    }
    
    /**
     * Check if filter panel is displayed
     * @return true if filter panel is displayed
     */
    public boolean isFilterPanelDisplayed() {
        return isElementDisplayed(filterPanel);
    }
} 