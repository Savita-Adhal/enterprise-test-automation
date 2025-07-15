package utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Enhanced screenshot utility for better organization and naming
 * Provides detailed screenshots for HTML test reports
 */
public class EnhancedScreenshotUtils {
    
    private static final Logger logger = LoggerFactory.getLogger(EnhancedScreenshotUtils.class);
    private static final String SCREENSHOTS_DIR = "test-output/screenshots";
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
    
    /**
     * Capture screenshot with detailed naming
     * @param driver WebDriver instance
     * @param testName Name of the test
     * @param stepName Name of the test step
     * @param className Name of the test class
     * @return Path to the saved screenshot
     */
    public static String captureDetailedScreenshot(WebDriver driver, String testName, String stepName, String className) {
        try {
            createScreenshotsDirectory();
            
            String timestamp = DATE_FORMAT.format(new Date());
            String fileName = String.format("%s_%s_%s_%s.png", 
                    className, testName, stepName, timestamp);
            
            // Clean filename by removing special characters
            fileName = fileName.replaceAll("[^a-zA-Z0-9._-]", "_");
            
            Path screenshotPath = Paths.get(SCREENSHOTS_DIR, fileName);
            
            if (driver instanceof TakesScreenshot) {
                TakesScreenshot ts = (TakesScreenshot) driver;
                File screenshot = ts.getScreenshotAs(OutputType.FILE);
                Files.copy(screenshot.toPath(), screenshotPath);
                
                logger.info("Screenshot captured: {}", screenshotPath.toString());
                return screenshotPath.toString();
            } else {
                logger.warn("WebDriver does not support screenshots");
                return null;
            }
        } catch (IOException e) {
            logger.error("Error capturing screenshot: {}", e.getMessage());
            return null;
        }
    }
    
    /**
     * Capture screenshot on test failure
     * @param driver WebDriver instance
     * @param testName Name of the test
     * @param className Name of the test class
     * @param errorMessage Error message
     * @return Path to the saved screenshot
     */
    public static String captureFailureScreenshot(WebDriver driver, String testName, String className, String errorMessage) {
        try {
            createScreenshotsDirectory();
            
            String timestamp = DATE_FORMAT.format(new Date());
            String fileName = String.format("%s_%s_FAILURE_%s.png", 
                    className, testName, timestamp);
            
            // Clean filename
            fileName = fileName.replaceAll("[^a-zA-Z0-9._-]", "_");
            
            Path screenshotPath = Paths.get(SCREENSHOTS_DIR, fileName);
            
            if (driver instanceof TakesScreenshot) {
                TakesScreenshot ts = (TakesScreenshot) driver;
                File screenshot = ts.getScreenshotAs(OutputType.FILE);
                Files.copy(screenshot.toPath(), screenshotPath);
                
                logger.info("Failure screenshot captured: {}", screenshotPath.toString());
                
                // Log the error message with the screenshot
                TestStepLogger.logError("Test Failure", errorMessage);
                TestStepLogger.logStepWithScreenshot("Screenshot Captured", 
                    "Failure screenshot saved", "INFO", screenshotPath.toString());
                
                return screenshotPath.toString();
            } else {
                logger.warn("WebDriver does not support screenshots");
                return null;
            }
        } catch (IOException e) {
            logger.error("Error capturing failure screenshot: {}", e.getMessage());
            return null;
        }
    }
    
    /**
     * Capture screenshot for a specific test step
     * @param driver WebDriver instance
     * @param testName Name of the test
     * @param stepName Name of the test step
     * @param className Name of the test class
     * @param stepDescription Description of the step
     * @return Path to the saved screenshot
     */
    public static String captureStepScreenshot(WebDriver driver, String testName, String stepName, 
                                             String className, String stepDescription) {
        String screenshotPath = captureDetailedScreenshot(driver, testName, stepName, className);
        
        if (screenshotPath != null) {
            TestStepLogger.logStepWithScreenshot("Step Screenshot", 
                stepDescription, "INFO", screenshotPath);
        }
        
        return screenshotPath;
    }
    
    /**
     * Capture screenshot before and after an action
     * @param driver WebDriver instance
     * @param testName Name of the test
     * @param actionName Name of the action
     * @param className Name of the test class
     * @param isBefore Whether this is before or after the action
     * @return Path to the saved screenshot
     */
    public static String captureActionScreenshot(WebDriver driver, String testName, String actionName, 
                                               String className, boolean isBefore) {
        String stepName = isBefore ? actionName + "_BEFORE" : actionName + "_AFTER";
        String description = isBefore ? "Before performing " + actionName : "After performing " + actionName;
        
        return captureStepScreenshot(driver, testName, stepName, className, description);
    }
    
    /**
     * Capture screenshot with custom description
     * @param driver WebDriver instance
     * @param testName Name of the test
     * @param customName Custom name for the screenshot
     * @param description Description of what the screenshot shows
     * @return Path to the saved screenshot
     */
    public static String captureCustomScreenshot(WebDriver driver, String testName, String customName, String description) {
        try {
            createScreenshotsDirectory();
            
            String timestamp = DATE_FORMAT.format(new Date());
            String fileName = String.format("%s_%s_%s.png", testName, customName, timestamp);
            
            // Clean filename
            fileName = fileName.replaceAll("[^a-zA-Z0-9._-]", "_");
            
            Path screenshotPath = Paths.get(SCREENSHOTS_DIR, fileName);
            
            if (driver instanceof TakesScreenshot) {
                TakesScreenshot ts = (TakesScreenshot) driver;
                File screenshot = ts.getScreenshotAs(OutputType.FILE);
                Files.copy(screenshot.toPath(), screenshotPath);
                
                logger.info("Custom screenshot captured: {}", screenshotPath.toString());
                
                TestStepLogger.logStepWithScreenshot("Custom Screenshot", 
                    description, "INFO", screenshotPath.toString());
                
                return screenshotPath.toString();
            } else {
                logger.warn("WebDriver does not support screenshots");
                return null;
            }
        } catch (IOException e) {
            logger.error("Error capturing custom screenshot: {}", e.getMessage());
            return null;
        }
    }
    
    /**
     * Create screenshots directory if it doesn't exist
     */
    private static void createScreenshotsDirectory() {
        try {
            Path screenshotsPath = Paths.get(SCREENSHOTS_DIR);
            if (!Files.exists(screenshotsPath)) {
                Files.createDirectories(screenshotsPath);
                logger.info("Created screenshots directory: {}", screenshotsPath.toString());
            }
        } catch (IOException e) {
            logger.error("Error creating screenshots directory: {}", e.getMessage());
        }
    }
    
    /**
     * Clean up old screenshots (keep only the last N days)
     * @param daysToKeep Number of days to keep screenshots
     */
    public static void cleanupOldScreenshots(int daysToKeep) {
        try {
            Path screenshotsPath = Paths.get(SCREENSHOTS_DIR);
            if (!Files.exists(screenshotsPath)) {
                return;
            }
            
            long cutoffTime = System.currentTimeMillis() - (daysToKeep * 24 * 60 * 60 * 1000L);
            
            Files.list(screenshotsPath)
                .filter(path -> path.toString().endsWith(".png"))
                .filter(path -> {
                    try {
                        return Files.getLastModifiedTime(path).toMillis() < cutoffTime;
                    } catch (IOException e) {
                        return false;
                    }
                })
                .forEach(path -> {
                    try {
                        Files.delete(path);
                        logger.info("Deleted old screenshot: {}", path.toString());
                    } catch (IOException e) {
                        logger.warn("Could not delete old screenshot: {}", path.toString());
                    }
                });
                
        } catch (IOException e) {
            logger.error("Error cleaning up old screenshots: {}", e.getMessage());
        }
    }
    
    /**
     * Get relative path for HTML report
     * @param absolutePath Absolute path to the screenshot
     * @return Relative path for HTML report
     */
    public static String getRelativePathForReport(String absolutePath) {
        if (absolutePath == null) {
            return null;
        }
        
        Path screenshotPath = Paths.get(absolutePath);
        Path screenshotsDir = Paths.get(SCREENSHOTS_DIR);
        
        try {
            return screenshotsDir.relativize(screenshotPath).toString();
        } catch (IllegalArgumentException e) {
            // If paths are not related, return just the filename
            return screenshotPath.getFileName().toString();
        }
    }
} 