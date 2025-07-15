package utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Reporter;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Utility class for logging test steps with timestamps and status
 * This helps in creating detailed test reports with step-by-step execution
 */
public class TestStepLogger {
    
    private static final Logger logger = LoggerFactory.getLogger(TestStepLogger.class);
    private static final SimpleDateFormat TIMESTAMP_FORMAT = new SimpleDateFormat("HH:mm:ss.SSS");
    
    /**
     * Log a test step with INFO status
     * @param stepName Name of the test step
     * @param description Description of what the step does
     */
    public static void logStep(String stepName, String description) {
        logStep(stepName, description, "INFO");
    }
    
    /**
     * Log a test step with custom status
     * @param stepName Name of the test step
     * @param description Description of what the step does
     * @param status Status of the step (INFO, PASS, FAIL, WARN)
     */
    public static void logStep(String stepName, String description, String status) {
        String timestamp = TIMESTAMP_FORMAT.format(new Date());
        String stepMessage = String.format("[%s] %s - %s (%s)", timestamp, stepName, description, status);
        
        // Log to TestNG Reporter for HTML reports
        Reporter.log(stepMessage);
        
        // Log to SLF4J logger
        switch (status.toUpperCase()) {
            case "PASS":
                logger.info(stepMessage);
                break;
            case "FAIL":
                logger.error(stepMessage);
                break;
            case "WARN":
                logger.warn(stepMessage);
                break;
            default:
                logger.info(stepMessage);
                break;
        }
    }
    
    /**
     * Log a test step with screenshot capture
     * @param stepName Name of the test step
     * @param description Description of what the step does
     * @param status Status of the step
     * @param screenshotPath Path to the screenshot (can be null)
     */
    public static void logStepWithScreenshot(String stepName, String description, String status, String screenshotPath) {
        logStep(stepName, description, status);
        
        if (screenshotPath != null) {
            logStep("Screenshot Captured", "Screenshot saved: " + screenshotPath, "INFO");
        }
    }
    
    /**
     * Log test start
     * @param testName Name of the test
     * @param description Test description
     */
    public static void logTestStart(String testName, String description) {
        logStep("Test Started", String.format("Test '%s' - %s", testName, description), "INFO");
    }
    
    /**
     * Log test completion
     * @param testName Name of the test
     * @param status Test status (PASS, FAIL, SKIP)
     * @param duration Test duration in milliseconds
     */
    public static void logTestComplete(String testName, String status, long duration) {
        String durationStr = String.format("%.2f", duration / 1000.0);
        logStep("Test Completed", String.format("Test '%s' completed with status: %s (Duration: %ss)", 
                testName, status, durationStr), status);
    }
    
    /**
     * Log assertion
     * @param assertionName Name of the assertion
     * @param expected Expected value
     * @param actual Actual value
     * @param passed Whether assertion passed
     */
    public static void logAssertion(String assertionName, String expected, String actual, boolean passed) {
        String status = passed ? "PASS" : "FAIL";
        String description = String.format("Assertion '%s': Expected='%s', Actual='%s'", 
                assertionName, expected, actual);
        logStep("Assertion", description, status);
    }
    
    /**
     * Log page navigation
     * @param pageName Name of the page
     * @param url URL navigated to
     */
    public static void logPageNavigation(String pageName, String url) {
        logStep("Page Navigation", String.format("Navigated to '%s' page: %s", pageName, url), "INFO");
    }
    
    /**
     * Log element interaction
     * @param action Action performed (click, type, select, etc.)
     * @param elementDescription Description of the element
     * @param value Value used (if applicable)
     */
    public static void logElementInteraction(String action, String elementDescription, String value) {
        String description = value != null ? 
            String.format("%s on element: %s with value: %s", action, elementDescription, value) :
            String.format("%s on element: %s", action, elementDescription);
        logStep("Element Interaction", description, "INFO");
    }
    
    /**
     * Log wait operation
     * @param waitType Type of wait (implicit, explicit, fluent)
     * @param duration Duration in seconds
     * @param condition Condition waited for
     */
    public static void logWait(String waitType, int duration, String condition) {
        logStep("Wait", String.format("%s wait for %d seconds: %s", waitType, duration, condition), "INFO");
    }
    
    /**
     * Log error with details
     * @param errorMessage Error message
     * @param details Additional error details
     */
    public static void logError(String errorMessage, String details) {
        logStep("Error", String.format("%s - %s", errorMessage, details), "FAIL");
    }
    
    /**
     * Log warning
     * @param warningMessage Warning message
     */
    public static void logWarning(String warningMessage) {
        logStep("Warning", warningMessage, "WARN");
    }
    
    /**
     * Log test data
     * @param dataType Type of test data
     * @param dataValue Value of the test data
     */
    public static void logTestData(String dataType, String dataValue) {
        logStep("Test Data", String.format("%s: %s", dataType, dataValue), "INFO");
    }
    
    /**
     * Log configuration information
     * @param configKey Configuration key
     * @param configValue Configuration value
     */
    public static void logConfiguration(String configKey, String configValue) {
        logStep("Configuration", String.format("%s: %s", configKey, configValue), "INFO");
    }
} 