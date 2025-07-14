package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Base class for all page objects
 * Provides common functionality and PageFactory initialization
 */
public abstract class BasePage {
    
    protected WebDriver driver;
    protected WebDriverWait wait;
    
    /**
     * Constructor that initializes PageFactory
     * @param driver WebDriver instance
     */
    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 20);
        
        // Initialize PageFactory for this page
        PageFactory.initElements(driver, this);
    }
    
    /**
     * Wait for element to be visible
     * @param element WebElement to wait for
     * @return WebElement once visible
     */
    protected WebElement waitForElementVisible(WebElement element) {
        return wait.until(ExpectedConditions.visibilityOf(element));
    }
    
    /**
     * Wait for element to be clickable
     * @param element WebElement to wait for
     * @return WebElement once clickable
     */
    protected WebElement waitForElementClickable(WebElement element) {
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }
    
    /**
     * Wait for page title to match expected title
     * @param title Expected page title
     */
    protected void waitForPageTitle(String title) {
        wait.until(ExpectedConditions.titleIs(title));
    }
    
    /**
     * Click on an element after waiting for it to be clickable
     * @param element WebElement to click
     */
    protected void click(WebElement element) {
        waitForElementClickable(element).click();
    }
    
    /**
     * Send keys to an element after waiting for it to be visible
     * @param element WebElement to send keys to
     * @param text Text to send
     */
    protected void sendKeys(WebElement element, String text) {
        WebElement visibleElement = waitForElementVisible(element);
        visibleElement.clear();
        visibleElement.sendKeys(text);
    }
    
    /**
     * Get text from an element after waiting for it to be visible
     * @param element WebElement to get text from
     * @return Text content of the element
     */
    protected String getText(WebElement element) {
        return waitForElementVisible(element).getText();
    }
    
    /**
     * Check if an element is displayed
     * @param element WebElement to check
     * @return true if element is displayed, false otherwise
     */
    protected boolean isElementDisplayed(WebElement element) {
        try {
            return waitForElementVisible(element).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Wait for element to be present in DOM
     * @param element WebElement to wait for
     * @return WebElement once present
     */
    protected WebElement waitForElementPresent(WebElement element) {
        return wait.until(ExpectedConditions.visibilityOf(element));
    }
    
    /**
     * Wait for text to be present in element
     * @param element WebElement to check
     * @param text Text to wait for
     */
    protected void waitForTextToBePresent(WebElement element, String text) {
        wait.until(ExpectedConditions.textToBePresentInElement(element, text));
    }
    
    /**
     * Wait for element to disappear
     * @param element WebElement to wait for
     */
    protected void waitForElementToDisappear(WebElement element) {
        wait.until(ExpectedConditions.invisibilityOf(element));
    }
    
    /**
     * Get current page title
     * @return Current page title
     */
    public String getPageTitle() {
        return driver.getTitle();
    }
    
    /**
     * Get current page URL
     * @return Current page URL
     */
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
} 