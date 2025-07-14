package utils;

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

/**
 * Custom assertion utility that captures screenshots on assertion failures
 */
public class TestAssertions {
    
    private static final Logger logger = LoggerFactory.getLogger(TestAssertions.class);
    
    /**
     * Asserts that a condition is true and captures screenshot on failure
     * @param condition Condition to assert
     * @param message Assertion message
     * @param driver WebDriver instance for screenshot
     * @param testName Test method name
     * @param className Test class name
     */
    public static void assertTrue(boolean condition, String message, WebDriver driver, String testName, String className) {
        try {
            Assert.assertTrue(condition, message);
        } catch (AssertionError e) {
            captureScreenshotOnFailure(driver, testName, className, message);
            throw e;
        }
    }
    
    /**
     * Asserts that two objects are equal and captures screenshot on failure
     * @param actual Actual value
     * @param expected Expected value
     * @param message Assertion message
     * @param driver WebDriver instance for screenshot
     * @param testName Test method name
     * @param className Test class name
     */
    public static void assertEquals(Object actual, Object expected, String message, WebDriver driver, String testName, String className) {
        try {
            Assert.assertEquals(actual, expected, message);
        } catch (AssertionError e) {
            captureScreenshotOnFailure(driver, testName, className, message);
            throw e;
        }
    }
    
    /**
     * Asserts that an object is not null and captures screenshot on failure
     * @param object Object to check
     * @param message Assertion message
     * @param driver WebDriver instance for screenshot
     * @param testName Test method name
     * @param className Test class name
     */
    public static void assertNotNull(Object object, String message, WebDriver driver, String testName, String className) {
        try {
            Assert.assertNotNull(object, message);
        } catch (AssertionError e) {
            captureScreenshotOnFailure(driver, testName, className, message);
            throw e;
        }
    }
    
    /**
     * Asserts that a condition is false and captures screenshot on failure
     * @param condition Condition to assert
     * @param message Assertion message
     * @param driver WebDriver instance for screenshot
     * @param testName Test method name
     * @param className Test class name
     */
    public static void assertFalse(boolean condition, String message, WebDriver driver, String testName, String className) {
        try {
            Assert.assertFalse(condition, message);
        } catch (AssertionError e) {
            captureScreenshotOnFailure(driver, testName, className, message);
            throw e;
        }
    }
    
    /**
     * Asserts that a string contains a substring and captures screenshot on failure
     * @param actualString Actual string
     * @param expectedSubstring Expected substring
     * @param message Assertion message
     * @param driver WebDriver instance for screenshot
     * @param testName Test method name
     * @param className Test class name
     */
    public static void assertContains(String actualString, String expectedSubstring, String message, WebDriver driver, String testName, String className) {
        try {
            Assert.assertTrue(actualString != null && actualString.contains(expectedSubstring), 
                            message + " - Expected to contain: " + expectedSubstring + ", but was: " + actualString);
        } catch (AssertionError e) {
            captureScreenshotOnFailure(driver, testName, className, message);
            throw e;
        }
    }
    
    /**
     * Captures screenshot when an assertion fails
     * @param driver WebDriver instance
     * @param testName Test method name
     * @param className Test class name
     * @param assertionMessage Assertion message
     */
    private static void captureScreenshotOnFailure(WebDriver driver, String testName, String className, String assertionMessage) {
        if (driver != null) {
            try {
                String screenshotPath = ScreenshotUtils.captureScreenshot(driver, testName + "_assertion_failure", className);
                if (screenshotPath != null) {
                    logger.error("Assertion failed: {}. Screenshot saved: {}", assertionMessage, screenshotPath);
                } else {
                    logger.error("Assertion failed: {}. Screenshot capture failed.", assertionMessage);
                }
            } catch (Exception e) {
                logger.error("Error capturing screenshot on assertion failure: {}", e.getMessage());
            }
        } else {
            logger.warn("WebDriver is null, cannot capture screenshot for assertion failure: {}", assertionMessage);
        }
    }
} 