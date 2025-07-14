package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class DashboardPage extends BasePage {
    
    // Locators
    private static final By CURVE_MANAGER_BUTTON = By.id("button-1483-btnInnerEl");
    private static final By PROFILE_MENU_ITEM = By.id("appmenuitem-1487-textEl");
    
    // Expected values
    private static final String CURVE_MANAGER_TEXT = "Curve Manager";
    
    public DashboardPage(WebDriver driver) {
        super(driver);
    }
    
    public boolean isCurveManagerButtonDisplayed() {
        return isElementDisplayed(CURVE_MANAGER_BUTTON);
    }
    
    public String getCurveManagerButtonText() {
        return getText(CURVE_MANAGER_BUTTON);
    }
    
    public void clickCurveManagerButton() {
        click(CURVE_MANAGER_BUTTON);
    }
    
    public void clickProfileMenuItem() {
        click(PROFILE_MENU_ITEM);
    }
    
    public void navigateToProfileCreation() {
        if (CURVE_MANAGER_TEXT.equals(getCurveManagerButtonText())) {
            clickCurveManagerButton();
            clickProfileMenuItem();
        } else {
            throw new RuntimeException("Curve Manager button not found or has unexpected text");
        }
    }
    
    public boolean isDashboardLoaded() {
        return driver.getTitle().contains("Zema Enterprise 5.13");
    }
} 