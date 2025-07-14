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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ScreenshotUtils {
    
    private static final Logger logger = LoggerFactory.getLogger(ScreenshotUtils.class);
    private static final String SCREENSHOT_DIR = "screenshots";
    private static final String SCREENSHOT_FORMAT = "png";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
    
    /**
     * Captures a screenshot and saves it to the screenshots directory
     * @param driver WebDriver instance
     * @param testName Name of the test method
     * @param className Name of the test class
     * @return Path to the saved screenshot file, or null if failed
     */
    public static String captureScreenshot(WebDriver driver, String testName, String className) {
        if (driver == null) {
            logger.warn("WebDriver is null, cannot capture screenshot");
            return null;
        }
        
        try {
            // Create screenshots directory if it doesn't exist
            createScreenshotDirectory();
            
            // Generate filename with timestamp
            String timestamp = LocalDateTime.now().format(DATE_FORMATTER);
            String fileName = String.format("%s_%s_%s.%s", 
                className, testName, timestamp, SCREENSHOT_FORMAT);
            
            // Remove special characters from filename
            fileName = fileName.replaceAll("[^a-zA-Z0-9._-]", "_");
            
            Path screenshotPath = Paths.get(SCREENSHOT_DIR, fileName);
            
            // Capture screenshot
            TakesScreenshot ts = (TakesScreenshot) driver;
            File screenshotFile = ts.getScreenshotAs(OutputType.FILE);
            
            // Copy file to destination
            Files.copy(screenshotFile.toPath(), screenshotPath);
            
            logger.info("Screenshot captured successfully: {}", screenshotPath.toString());
            return screenshotPath.toString();
            
        } catch (IOException e) {
            logger.error("Failed to capture screenshot: {}", e.getMessage());
            return null;
        } catch (Exception e) {
            logger.error("Unexpected error while capturing screenshot: {}", e.getMessage());
            return null;
        }
    }
    
    /**
     * Captures a screenshot with a custom filename
     * @param driver WebDriver instance
     * @param customName Custom name for the screenshot
     * @return Path to the saved screenshot file, or null if failed
     */
    public static String captureScreenshot(WebDriver driver, String customName) {
        if (driver == null) {
            logger.warn("WebDriver is null, cannot capture screenshot");
            return null;
        }
        
        try {
            // Create screenshots directory if it doesn't exist
            createScreenshotDirectory();
            
            // Generate filename with timestamp
            String timestamp = LocalDateTime.now().format(DATE_FORMATTER);
            String fileName = String.format("%s_%s.%s", customName, timestamp, SCREENSHOT_FORMAT);
            
            // Remove special characters from filename
            fileName = fileName.replaceAll("[^a-zA-Z0-9._-]", "_");
            
            Path screenshotPath = Paths.get(SCREENSHOT_DIR, fileName);
            
            // Capture screenshot
            TakesScreenshot ts = (TakesScreenshot) driver;
            File screenshotFile = ts.getScreenshotAs(OutputType.FILE);
            
            // Copy file to destination
            Files.copy(screenshotFile.toPath(), screenshotPath);
            
            logger.info("Screenshot captured successfully: {}", screenshotPath.toString());
            return screenshotPath.toString();
            
        } catch (IOException e) {
            logger.error("Failed to capture screenshot: {}", e.getMessage());
            return null;
        } catch (Exception e) {
            logger.error("Unexpected error while capturing screenshot: {}", e.getMessage());
            return null;
        }
    }
    
    /**
     * Creates the screenshots directory if it doesn't exist
     */
    private static void createScreenshotDirectory() throws IOException {
        Path screenshotDir = Paths.get(SCREENSHOT_DIR);
        if (!Files.exists(screenshotDir)) {
            Files.createDirectories(screenshotDir);
            logger.info("Created screenshots directory: {}", screenshotDir.toString());
        }
    }
    
    /**
     * Cleans up old screenshots (older than specified days)
     * @param daysToKeep Number of days to keep screenshots
     */
    public static void cleanupOldScreenshots(int daysToKeep) {
        try {
            Path screenshotDir = Paths.get(SCREENSHOT_DIR);
            if (!Files.exists(screenshotDir)) {
                return;
            }
            
            long cutoffTime = System.currentTimeMillis() - (daysToKeep * 24 * 60 * 60 * 1000L);
            
            List<Path> filesToDelete = new ArrayList<>();
            for (Path path : Files.newDirectoryStream(screenshotDir)) {
                if (path.toString().endsWith("." + SCREENSHOT_FORMAT)) {
                    try {
                        if (Files.getLastModifiedTime(path).toMillis() < cutoffTime) {
                            filesToDelete.add(path);
                        }
                    } catch (IOException e) {
                        // Skip this file
                    }
                }
            }
            
            for (Path path : filesToDelete) {
                try {
                    Files.delete(path);
                    logger.info("Deleted old screenshot: {}", path.toString());
                } catch (IOException e) {
                    logger.warn("Failed to delete old screenshot {}: {}", path.toString(), e.getMessage());
                }
            }
                
        } catch (IOException e) {
            logger.error("Error during screenshot cleanup: {}", e.getMessage());
        }
    }
} 