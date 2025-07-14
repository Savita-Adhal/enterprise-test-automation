package utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener {
    
    private static final Logger logger = LoggerFactory.getLogger(TestListener.class);
    
    @Override
    public void onTestStart(ITestResult result) {
        logger.info("Starting test: {} in class: {}", 
                   result.getName(), result.getTestClass().getName());
    }
    
    @Override
    public void onTestSuccess(ITestResult result) {
        logger.info("Test PASSED: {} in class: {}", 
                   result.getName(), result.getTestClass().getName());
    }
    
    @Override
    public void onTestFailure(ITestResult result) {
        logger.error("Test FAILED: {} in class: {}", 
                    result.getName(), result.getTestClass().getName());
        logger.error("Failure reason: {}", result.getThrowable().getMessage());
        
        // Capture screenshot on failure
        captureScreenshotOnFailure(result);
    }
    
    @Override
    public void onTestSkipped(ITestResult result) {
        logger.warn("Test SKIPPED: {} in class: {}", 
                   result.getName(), result.getTestClass().getName());
    }
    
    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        logger.warn("Test failed but within success percentage: {} in class: {}", 
                   result.getName(), result.getTestClass().getName());
        
        // Capture screenshot even for partial failures
        captureScreenshotOnFailure(result);
    }
    
    @Override
    public void onStart(ITestContext context) {
        logger.info("Starting test suite: {}", context.getName());
        
        // Clean up old screenshots (keep last 7 days)
        ScreenshotUtils.cleanupOldScreenshots(7);
    }
    
    @Override
    public void onFinish(ITestContext context) {
        logger.info("Finished test suite: {}", context.getName());
        logger.info("Total tests: {}, Passed: {}, Failed: {}, Skipped: {}", 
                   context.getAllTestMethods().length,
                   context.getPassedTests().size(),
                   context.getFailedTests().size(),
                   context.getSkippedTests().size());
    }
    
    /**
     * Captures screenshot when a test fails
     * @param result Test result containing failure information
     */
    private void captureScreenshotOnFailure(ITestResult result) {
        try {
            // Get the test instance to access WebDriver
            Object testInstance = result.getInstance();
            if (testInstance != null) {
                // Try to get WebDriver from BaseTest
                java.lang.reflect.Field driverField = null;
                try {
                    driverField = testInstance.getClass().getSuperclass().getDeclaredField("driver");
                } catch (NoSuchFieldException e) {
                    // Try to find driver field in the test class itself
                    try {
                        driverField = testInstance.getClass().getDeclaredField("driver");
                    } catch (NoSuchFieldException ex) {
                        logger.warn("Could not find WebDriver field in test class: {}", 
                                  testInstance.getClass().getName());
                        return;
                    }
                }
                
                if (driverField != null) {
                    driverField.setAccessible(true);
                    Object driver = driverField.get(testInstance);
                    
                    if (driver instanceof org.openqa.selenium.WebDriver) {
                        org.openqa.selenium.WebDriver webDriver = (org.openqa.selenium.WebDriver) driver;
                        
                        // Capture screenshot
                        String screenshotPath = ScreenshotUtils.captureScreenshot(
                            webDriver, 
                            result.getName(), 
                            result.getTestClass().getRealClass().getSimpleName()
                        );
                        
                        if (screenshotPath != null) {
                            logger.info("Screenshot captured for failed test: {}", screenshotPath);
                            
                            // Add screenshot path to test result for reporting
                            result.setAttribute("screenshot", screenshotPath);
                        } else {
                            logger.warn("Failed to capture screenshot for test: {}", result.getName());
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error("Error capturing screenshot for test {}: {}", 
                        result.getName(), e.getMessage());
        }
    }
} 