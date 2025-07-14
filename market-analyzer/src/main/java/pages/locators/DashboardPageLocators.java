package pages.locators;

import org.openqa.selenium.By;

/**
 * Locators for Dashboard Page
 * Contains all element locators specific to the dashboard page
 */
public class DashboardPageLocators extends BaseLocators {
    
    // Main navigation elements
    public static final By CURVE_MANAGER_BUTTON = byId("button-1483-btnInnerEl");
    public static final By PROFILE_MENU_ITEM = byId("appmenuitem-1487-textEl");
    public static final By MAIN_NAVIGATION = byClassName("main-navigation");
    
    // User profile and settings
    public static final By USER_PROFILE_DROPDOWN = byCssSelector(".user-profile-dropdown");
    public static final By LOGOUT_BUTTON = byLinkText("Logout");
    public static final By SETTINGS_LINK = byLinkText("Settings");
    public static final By USER_AVATAR = byCssSelector(".user-avatar");
    
    // Dashboard widgets and content
    public static final By DASHBOARD_CONTAINER = byClassName("dashboard-container");
    public static final By WELCOME_MESSAGE = byCssSelector(".welcome-message");
    public static final By QUICK_ACTIONS_PANEL = byClassName("quick-actions");
    
    // Menu items and navigation
    public static final By SIDE_MENU = byClassName("side-menu");
    public static final By MENU_ITEMS = byCssSelector(".menu-item");
    public static final By SUB_MENU_ITEMS = byCssSelector(".sub-menu-item");
    
    // Notifications and alerts
    public static final By NOTIFICATION_PANEL = byClassName("notification-panel");
    public static final By ALERT_MESSAGE = byClassName("alert-message");
    public static final By SUCCESS_MESSAGE = byCssSelector(".success-message");
    
    // Search and filters
    public static final By SEARCH_BOX = byCssSelector(".search-box input");
    public static final By SEARCH_BUTTON = byCssSelector(".search-box button");
    public static final By FILTER_PANEL = byClassName("filter-panel");
    
    // Dynamic locators
    public static By getMenuItemByText(String menuText) {
        return byXPath("//div[contains(@class,'menu-item') and contains(text(),'" + menuText + "')]");
    }
    
    public static By getSubMenuItemByText(String subMenuText) {
        return byXPath("//div[contains(@class,'sub-menu-item') and contains(text(),'" + subMenuText + "')]");
    }
    
    public static By getNotificationByText(String notificationText) {
        return byXPath("//div[contains(@class,'notification') and contains(text(),'" + notificationText + "')]");
    }
} 