package pages.locators;

import org.openqa.selenium.By;

/**
 * Base class for all locator classes
 * Provides common locators and utility methods
 */
public abstract class BaseLocators {
    
    // Common locators that might be used across multiple pages
    protected static final By PAGE_TITLE = By.tagName("title");
    protected static final By BODY = By.tagName("body");
    protected static final By LOADING_SPINNER = By.className("loading-spinner");
    
    /**
     * Creates a locator for an element with a specific ID
     * @param id The element ID
     * @return By locator
     */
    protected static By byId(String id) {
        return By.id(id);
    }
    
    /**
     * Creates a locator for an element with a specific name
     * @param name The element name
     * @return By locator
     */
    protected static By byName(String name) {
        return By.name(name);
    }
    
    /**
     * Creates a locator for an element with a specific CSS class
     * @param className The CSS class name
     * @return By locator
     */
    protected static By byClassName(String className) {
        return By.className(className);
    }
    
    /**
     * Creates a locator for an element with a specific XPath
     * @param xpath The XPath expression
     * @return By locator
     */
    protected static By byXPath(String xpath) {
        return By.xpath(xpath);
    }
    
    /**
     * Creates a locator for an element with a specific CSS selector
     * @param cssSelector The CSS selector
     * @return By locator
     */
    protected static By byCssSelector(String cssSelector) {
        return By.cssSelector(cssSelector);
    }
    
    /**
     * Creates a locator for an element with a specific link text
     * @param linkText The link text
     * @return By locator
     */
    protected static By byLinkText(String linkText) {
        return By.linkText(linkText);
    }
    
    /**
     * Creates a locator for an element with a specific partial link text
     * @param partialLinkText The partial link text
     * @return By locator
     */
    protected static By byPartialLinkText(String partialLinkText) {
        return By.partialLinkText(partialLinkText);
    }
} 