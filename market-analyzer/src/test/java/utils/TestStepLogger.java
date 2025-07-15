package utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility class for logging detailed test steps with timestamps
 */
public class TestStepLogger {
    
    private static final Logger logger = LoggerFactory.getLogger(TestStepLogger.class);
    private static EnhancedTestListener listener;
    
    /**
     * Set the test listener instance for step logging
     */
    public static void setListener(EnhancedTestListener testListener) {
        listener = testListener;
    }
    
    /**
     * Log a test step with timestamp
     */
    public static void logStep(String step) {
        String timestamp = java.time.LocalTime.now().toString();
        String formattedStep = String.format("[%s] %s", timestamp, step);
        
        logger.info("Test Step: {}", step);
        
        if (listener != null) {
            listener.logTestStep(step);
        }
    }
    
    /**
     * Log a test step with custom message
     */
    public static void logStep(String format, Object... args) {
        String step = String.format(format, args);
        logStep(step);
    }
    
    /**
     * Log test start
     */
    public static void logTestStart(String testName, String description) {
        logStep("TEST START: %s - %s", testName, description);
    }
    
    /**
     * Log test completion
     */
    public static void logTestComplete(String testName, String status, long duration) {
        logStep("TEST COMPLETE: %s - %s (Duration: %dms)", testName, status, duration);
    }
    
    /**
     * Log test configuration
     */
    public static void logConfiguration(String key, String value) {
        logStep("CONFIG: %s = %s", key, value);
    }
    
    /**
     * Log test data
     */
    public static void logTestData(String key, String value) {
        logStep("DATA: %s = %s", key, value);
    }
    
    /**
     * Log test assertion
     */
    public static void logAssertion(String assertion, String description, String actual, boolean result) {
        logStep("ASSERT: %s - %s | Actual: %s | Result: %s", assertion, description, actual, result ? "PASS" : "FAIL");
    }
    
    /**
     * Log element interaction
     */
    public static void logElementInteraction(String action, String element, String value) {
        if (value != null) {
            logStep("INTERACTION: %s on %s with value: %s", action, element, value);
        } else {
            logStep("INTERACTION: %s on %s", action, element);
        }
    }
    
    /**
     * Log wait operation
     */
    public static void logWait(String type, int seconds, String reason) {
        logStep("WAIT: %s wait for %d seconds - %s", type, seconds, reason);
    }
    
    /**
     * Log error
     */
    public static void logError(String errorType, String message) {
        logStep("ERROR: %s - %s", errorType, message);
    }
    
    /**
     * Log warning
     */
    public static void logWarning(String message) {
        logStep("WARNING: %s", message);
    }
    
    /**
     * Log step with screenshot information
     */
    public static void logStepWithScreenshot(String step, String description, String level, String screenshotPath) {
        logStep("STEP WITH SCREENSHOT: %s - %s | Level: %s | Screenshot: %s", step, description, level, screenshotPath);
    }
    
    /**
     * Log test setup information
     */
    public static void logSetup(String setupInfo) {
        logStep("SETUP: " + setupInfo);
    }
    
    /**
     * Log test action
     */
    public static void logAction(String action) {
        logStep("ACTION: " + action);
    }
    
    /**
     * Log test verification
     */
    public static void logVerification(String verification) {
        logStep("VERIFY: " + verification);
    }
    
    /**
     * Log test cleanup
     */
    public static void logCleanup(String cleanup) {
        logStep("CLEANUP: " + cleanup);
    }
} 