package utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for viewing and managing screenshots
 */
public class ScreenshotViewer {
    
    private static final Logger logger = LoggerFactory.getLogger(ScreenshotViewer.class);
    private static final String SCREENSHOT_DIR = "screenshots";
    
    /**
     * Lists all screenshots in the screenshots directory
     * @return List of screenshot file paths
     */
    public static List<String> listScreenshots() {
        try {
            Path screenshotDir = Paths.get(SCREENSHOT_DIR);
            if (!Files.exists(screenshotDir)) {
                logger.info("Screenshots directory does not exist");
                return new ArrayList<>();
            }
            
            List<String> screenshots = new ArrayList<>();
            for (Path path : Files.newDirectoryStream(screenshotDir)) {
                if (path.toString().endsWith(".png")) {
                    screenshots.add(path.toString());
                }
            }
            screenshots.sort(String::compareTo);
            return screenshots;
                
        } catch (IOException e) {
            logger.error("Error listing screenshots: {}", e.getMessage());
            return new ArrayList<>();
        }
    }
    
    /**
     * Lists screenshots for a specific test class
     * @param className Test class name
     * @return List of screenshot file paths for the class
     */
    public static List<String> listScreenshotsForClass(String className) {
        List<String> allScreenshots = listScreenshots();
        List<String> classScreenshots = new ArrayList<>();
        for (String path : allScreenshots) {
            if (path.contains(className)) {
                classScreenshots.add(path);
            }
        }
        return classScreenshots;
    }
    
    /**
     * Lists screenshots for a specific test method
     * @param testMethodName Test method name
     * @return List of screenshot file paths for the method
     */
    public static List<String> listScreenshotsForMethod(String testMethodName) {
        List<String> allScreenshots = listScreenshots();
        List<String> methodScreenshots = new ArrayList<>();
        for (String path : allScreenshots) {
            if (path.contains(testMethodName)) {
                methodScreenshots.add(path);
            }
        }
        return methodScreenshots;
    }
    
    /**
     * Lists screenshots taken today
     * @return List of screenshot file paths taken today
     */
    public static List<String> listTodayScreenshots() {
        String today = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        List<String> allScreenshots = listScreenshots();
        List<String> todayScreenshots = new ArrayList<>();
        for (String path : allScreenshots) {
            if (path.contains(today)) {
                todayScreenshots.add(path);
            }
        }
        return todayScreenshots;
    }
    
    /**
     * Gets screenshot count
     * @return Total number of screenshots
     */
    public static int getScreenshotCount() {
        return listScreenshots().size();
    }
    
    /**
     * Gets screenshot count for today
     * @return Number of screenshots taken today
     */
    public static int getTodayScreenshotCount() {
        return listTodayScreenshots().size();
    }
    
    /**
     * Prints screenshot information to console
     */
    public static void printScreenshotInfo() {
        List<String> allScreenshots = listScreenshots();
        List<String> todayScreenshots = listTodayScreenshots();
        
        logger.info("=== Screenshot Information ===");
        logger.info("Total screenshots: {}", allScreenshots.size());
        logger.info("Screenshots taken today: {}", todayScreenshots.size());
        
        if (!allScreenshots.isEmpty()) {
            logger.info("Latest screenshots:");
            int count = 0;
            for (String path : allScreenshots) {
                if (count < 5) {
                    logger.info("  - {}", path);
                    count++;
                } else {
                    break;
                }
            }
        }
        
        if (!todayScreenshots.isEmpty()) {
            logger.info("Today's screenshots:");
            for (String path : todayScreenshots) {
                logger.info("  - {}", path);
            }
        }
    }
    
    /**
     * Main method for command-line usage
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        if (args.length == 0) {
            printScreenshotInfo();
        } else {
            switch (args[0].toLowerCase()) {
                case "list":
                    for (String screenshot : listScreenshots()) {
                        System.out.println(screenshot);
                    }
                    break;
                case "today":
                    for (String screenshot : listTodayScreenshots()) {
                        System.out.println(screenshot);
                    }
                    break;
                case "count":
                    System.out.println("Total screenshots: " + getScreenshotCount());
                    System.out.println("Today's screenshots: " + getTodayScreenshotCount());
                    break;
                case "class":
                    if (args.length > 1) {
                        for (String screenshot : listScreenshotsForClass(args[1])) {
                            System.out.println(screenshot);
                        }
                    } else {
                        System.out.println("Please provide class name");
                    }
                    break;
                case "method":
                    if (args.length > 1) {
                        for (String screenshot : listScreenshotsForMethod(args[1])) {
                            System.out.println(screenshot);
                        }
                    } else {
                        System.out.println("Please provide method name");
                    }
                    break;
                default:
                    System.out.println("Usage: ScreenshotViewer [list|today|count|class <className>|method <methodName>]");
            }
        }
    }
} 