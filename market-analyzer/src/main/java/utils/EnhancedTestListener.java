package utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;

public class EnhancedTestListener implements ITestListener {
    
    private static final Logger logger = LoggerFactory.getLogger(EnhancedTestListener.class);
    private static final String REPORT_DIR = "enhanced-reports";
    private static final String SCREENSHOTS_DIR = "test-output/screenshots";
    private static final String LOGS_DIR = "logs";
    
    private Map<String, TestExecutionInfo> testExecutionMap = new HashMap<>();
    private List<String> testSteps = new ArrayList<>();
    private long testStartTime;
    private String currentTestName;
    
    public EnhancedTestListener() {
        logger.info("=== EnhancedTestListener CONSTRUCTOR CALLED ===");
        logger.info("=== EnhancedTestListener instance created ===");
    }
    
    @Override
    public void onTestStart(ITestResult result) {
        logger.info("=== EnhancedTestListener.onTestStart() CALLED ===");
        currentTestName = result.getName();
        testStartTime = System.currentTimeMillis();
        testSteps.clear();
        
        logger.info("=== Test Started: {} ===", currentTestName);
        logger.debug("EnhancedTestListener: Test started - {}", currentTestName);
        
        TestExecutionInfo testInfo = new TestExecutionInfo();
        testInfo.setTestName(currentTestName);
        testInfo.setStartTime(new Date(testStartTime));
        testInfo.setTestClass(result.getTestClass().getName());
        testInfo.setTestMethod(result.getMethod().getMethodName());
        testExecutionMap.put(currentTestName, testInfo);
        
        logger.info("=== Test execution map size: {} ===", testExecutionMap.size());
    }
    
    @Override
    public void onTestSuccess(ITestResult result) {
        long endTime = System.currentTimeMillis();
        long duration = endTime - testStartTime;
        
        logger.info("=== Test Passed: {} (Duration: {}ms) ===", currentTestName, duration);
        logger.debug("EnhancedTestListener: Test passed - {}", currentTestName);
        
        TestExecutionInfo testInfo = testExecutionMap.get(currentTestName);
        if (testInfo != null) {
            testInfo.setStatus("PASS");
            testInfo.setEndTime(new Date(endTime));
            testInfo.setDuration(duration);
            testInfo.setTestSteps(new ArrayList<>(testSteps));
        }
    }
    
    @Override
    public void onTestFailure(ITestResult result) {
        long endTime = System.currentTimeMillis();
        long duration = endTime - testStartTime;
        
        logger.error("=== Test Failed: {} (Duration: {}ms) ===", currentTestName, duration);
        logger.debug("EnhancedTestListener: Test failed - {}", currentTestName);
        
        TestExecutionInfo testInfo = testExecutionMap.get(currentTestName);
        if (testInfo != null) {
            testInfo.setStatus("FAIL");
            testInfo.setEndTime(new Date(endTime));
            testInfo.setDuration(duration);
            testInfo.setTestSteps(new ArrayList<>(testSteps));
            testInfo.setErrorMessage(result.getThrowable() != null ? result.getThrowable().getMessage() : "Unknown error");
            
            // Capture screenshot on failure
            captureScreenshot(currentTestName);
        }
    }
    
    @Override
    public void onTestSkipped(ITestResult result) {
        logger.warn("=== Test Skipped: {} ===", currentTestName);
        logger.debug("EnhancedTestListener: Test skipped - {}", currentTestName);
        
        TestExecutionInfo testInfo = testExecutionMap.get(currentTestName);
        if (testInfo != null) {
            testInfo.setStatus("SKIP");
            testInfo.setEndTime(new Date());
            testInfo.setTestSteps(new ArrayList<>(testSteps));
        }
    }
    
    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        logger.warn("=== Test Failed But Within Success Percentage: {} ===", currentTestName);
        logger.debug("EnhancedTestListener: Test failed but within success percentage - {}", currentTestName);
        
        TestExecutionInfo testInfo = testExecutionMap.get(currentTestName);
        if (testInfo != null) {
            testInfo.setStatus("FAIL_WITHIN_PERCENTAGE");
            testInfo.setEndTime(new Date());
            testInfo.setTestSteps(new ArrayList<>(testSteps));
            testInfo.setErrorMessage(result.getThrowable() != null ? result.getThrowable().getMessage() : "Unknown error");
        }
    }
    
    @Override
    public void onStart(ITestContext context) {
        logger.info("=== Test Suite Started ===");
        logger.debug("EnhancedTestListener: Test suite started");
    }
    
    @Override
    public void onFinish(ITestContext context) {
        logger.info("=== Test Suite Finished ===");
        logger.debug("EnhancedTestListener: Test suite finished, generating report");
        
        try {
            generateEnhancedReport();
        } catch (Exception e) {
            logger.error("Failed to generate enhanced report", e);
        }
    }
    
    public void logTestStep(String step) {
        String timestamp = new SimpleDateFormat("HH:mm:ss.SSS").format(new Date());
        String stepWithTimestamp = String.format("[%s] %s", timestamp, step);
        testSteps.add(stepWithTimestamp);
        logger.info("Test Step: {}", step);
    }
    
    private void captureScreenshot(String testName) {
        try {
            logger.info("=== Capturing screenshot for test: {} ===", testName);
            
            // Create screenshots directory if it doesn't exist
            Path screenshotsPath = Paths.get(SCREENSHOTS_DIR);
            if (!Files.exists(screenshotsPath)) {
                Files.createDirectories(screenshotsPath);
                logger.debug("Created screenshots directory: {}", screenshotsPath.toAbsolutePath());
            }
            
            // Find the actual screenshot file that was created by the test
            String actualScreenshotPath = findActualScreenshot(testName);
            
            if (actualScreenshotPath != null) {
                logger.info("=== Found screenshot for test {}: {} ===", testName, actualScreenshotPath);
                
                TestExecutionInfo testInfo = testExecutionMap.get(testName);
                if (testInfo != null) {
                    testInfo.setScreenshotPath(actualScreenshotPath);
                    logger.info("=== Screenshot path set for test {}: {} ===", testName, actualScreenshotPath);
                } else {
                    logger.warn("=== Test info not found for test: {} ===", testName);
                }
            } else {
                logger.warn("=== No screenshot found for test: {} ===", testName);
            }
            
        } catch (Exception e) {
            logger.error("=== Failed to find screenshot for test {} ===", testName, e);
        }
    }
    
    /**
     * Find the actual screenshot file created by the test
     */
    private String findActualScreenshot(String testName) {
        try {
            Path screenshotsPath = Paths.get(SCREENSHOTS_DIR);
            if (!Files.exists(screenshotsPath)) {
                logger.debug("Screenshots directory does not exist: {}", screenshotsPath);
                return null;
            }
            
            // Get the test class name from the current test execution
            TestExecutionInfo testInfo = testExecutionMap.get(testName);
            String className = testInfo != null ? testInfo.getTestClass() : "";
            
            // Extract just the class name without package
            String simpleClassName = className;
            if (className.contains(".")) {
                simpleClassName = className.substring(className.lastIndexOf(".") + 1);
            }
            
            logger.debug("Looking for screenshots for test: {} (class: {})", testName, simpleClassName);
            
            // List all files in the screenshots directory
            File[] files = screenshotsPath.toFile().listFiles();
            if (files == null) {
                logger.debug("No files found in screenshots directory");
                return null;
            }
            
            // First, try to find the most recent screenshot for this test
            String mostRecentScreenshot = null;
            long mostRecentTime = 0;
            
            for (File file : files) {
                if (file.isFile() && file.getName().toLowerCase().endsWith(".png")) {
                    String fileName = file.getName();
                    
                    // Check if the filename contains both class name and test method name
                    if (fileName.contains(simpleClassName) && fileName.contains(testName)) {
                        long fileTime = file.lastModified();
                        if (fileTime > mostRecentTime) {
                            mostRecentTime = fileTime;
                            mostRecentScreenshot = fileName;
                        }
                        logger.debug("Found matching screenshot: {}", fileName);
                    }
                }
            }
            
            if (mostRecentScreenshot != null) {
                String screenshotPath = SCREENSHOTS_DIR + "/" + mostRecentScreenshot;
                logger.info("Selected screenshot for test {}: {}", testName, screenshotPath);
                return screenshotPath;
            }
            
            // If no match found with class name, try just the test method name
            for (File file : files) {
                if (file.isFile() && file.getName().toLowerCase().endsWith(".png")) {
                    String fileName = file.getName();
                    if (fileName.contains(testName)) {
                        long fileTime = file.lastModified();
                        if (fileTime > mostRecentTime) {
                            mostRecentTime = fileTime;
                            mostRecentScreenshot = fileName;
                        }
                        logger.debug("Found screenshot by test name only: {}", fileName);
                    }
                }
            }
            
            if (mostRecentScreenshot != null) {
                String screenshotPath = SCREENSHOTS_DIR + "/" + mostRecentScreenshot;
                logger.info("Selected screenshot by test name only for {}: {}", testName, screenshotPath);
                return screenshotPath;
            }
            
            logger.warn("No screenshot found for test: {}", testName);
            return null;
            
        } catch (Exception e) {
            logger.error("Error finding screenshot for test {}", testName, e);
            return null;
        }
    }
    
    private void generateEnhancedReport() {
        try {
            // Create report directory if it doesn't exist
            Path reportPath = Paths.get(REPORT_DIR);
            if (!Files.exists(reportPath)) {
                Files.createDirectories(reportPath);
                logger.debug("Created report directory: {}", reportPath.toAbsolutePath());
            }
            
            String reportFileName = String.format("enhanced-test-report_%s.html", 
                new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()));
            String reportFilePath = Paths.get(REPORT_DIR, reportFileName).toString();
            
            logger.debug("Generating enhanced report at: {}", reportFilePath);
            
            StringBuilder html = new StringBuilder();
            html.append("<!DOCTYPE html>\n");
            html.append("<html lang=\"en\">\n");
            html.append("<head>\n");
            html.append("    <meta charset=\"UTF-8\">\n");
            html.append("    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n");
            html.append("    <title>Enhanced Test Report</title>\n");
            html.append("    <style>\n");
            html.append("        body { font-family: Arial, sans-serif; margin: 20px; background-color: #f5f5f5; }\n");
            html.append("        .header { background-color: #2c3e50; color: white; padding: 20px; border-radius: 5px; margin-bottom: 20px; }\n");
            html.append("        .summary { background-color: white; padding: 20px; border-radius: 5px; margin-bottom: 20px; box-shadow: 0 2px 4px rgba(0,0,0,0.1); }\n");
            html.append("        .test-case { background-color: white; margin-bottom: 15px; border-radius: 5px; overflow: hidden; box-shadow: 0 2px 4px rgba(0,0,0,0.1); }\n");
            html.append("        .test-header { padding: 15px; cursor: pointer; font-weight: bold; }\n");
            html.append("        .test-header.pass { background-color: #d4edda; color: #155724; border-left: 4px solid #28a745; }\n");
            html.append("        .test-header.fail { background-color: #f8d7da; color: #721c24; border-left: 4px solid #dc3545; }\n");
            html.append("        .test-header.skip { background-color: #fff3cd; color: #856404; border-left: 4px solid #ffc107; }\n");
            html.append("        .test-content { padding: 15px; display: none; }\n");
            html.append("        .test-steps { background-color: #f8f9fa; padding: 10px; border-radius: 3px; margin: 10px 0; }\n");
            html.append("        .test-step { margin: 5px 0; padding: 5px; background-color: white; border-left: 3px solid #007bff; }\n");
            html.append("        .screenshot { margin: 10px 0; }\n");
            html.append("        .screenshot img { max-width: 100%; border: 1px solid #ddd; border-radius: 3px; }\n");
            html.append("        .error-message { background-color: #f8d7da; color: #721c24; padding: 10px; border-radius: 3px; margin: 10px 0; }\n");
            html.append("        .stats { display: flex; justify-content: space-around; text-align: center; }\n");
            html.append("        .stat { flex: 1; padding: 10px; }\n");
            html.append("        .stat.pass { background-color: #d4edda; color: #155724; }\n");
            html.append("        .stat.fail { background-color: #f8d7da; color: #721c24; }\n");
            html.append("        .stat.skip { background-color: #fff3cd; color: #856404; }\n");
            html.append("    </style>\n");
            html.append("</head>\n");
            html.append("<body>\n");
            
            // Header
            html.append("    <div class=\"header\">\n");
            html.append("        <h1>Enhanced Test Report</h1>\n");
            html.append("        <p>Generated on: ").append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())).append("</p>\n");
            html.append("    </div>\n");
            
            // Summary
            int totalTests = testExecutionMap.size();
            int passedTests = (int) testExecutionMap.values().stream().filter(t -> "PASS".equals(t.getStatus())).count();
            int failedTests = (int) testExecutionMap.values().stream().filter(t -> "FAIL".equals(t.getStatus())).count();
            int skippedTests = (int) testExecutionMap.values().stream().filter(t -> "SKIP".equals(t.getStatus())).count();
            
            html.append("    <div class=\"summary\">\n");
            html.append("        <h2>Test Summary</h2>\n");
            html.append("        <div class=\"stats\">\n");
            html.append("            <div class=\"stat pass\">\n");
            html.append("                <h3>").append(passedTests).append("</h3>\n");
            html.append("                <p>Passed</p>\n");
            html.append("            </div>\n");
            html.append("            <div class=\"stat fail\">\n");
            html.append("                <h3>").append(failedTests).append("</h3>\n");
            html.append("                <p>Failed</p>\n");
            html.append("            </div>\n");
            html.append("            <div class=\"stat skip\">\n");
            html.append("                <h3>").append(skippedTests).append("</h3>\n");
            html.append("                <p>Skipped</p>\n");
            html.append("            </div>\n");
            html.append("        </div>\n");
            html.append("    </div>\n");
            
            // Test Cases
            html.append("    <h2>Test Details</h2>\n");
            logger.info("=== Generating report for {} test executions ===", testExecutionMap.size());
            for (TestExecutionInfo testInfo : testExecutionMap.values()) {
                logger.debug("=== Processing test: {} (status: {}) ===", 
                    testInfo.getTestName(), testInfo.getStatus());
                html.append("    <div class=\"test-case\">\n");
                String status = testInfo.getStatus() != null ? testInfo.getStatus().toLowerCase() : "unknown";
                String testName = testInfo.getTestName() != null ? testInfo.getTestName() : "Unknown Test";
                String displayStatus = testInfo.getStatus() != null ? testInfo.getStatus() : "UNKNOWN";
                
                html.append("        <div class=\"test-header ").append(status).append("\" onclick=\"toggleTest(this)\">\n");
                html.append("            <strong>").append(testName).append("</strong> - ");
                html.append(displayStatus).append(" (");
                html.append(testInfo.getDuration()).append("ms)\n");
                html.append("        </div>\n");
                html.append("        <div class=\"test-content\">\n");
                if (testInfo.getTestClass() != null) {
                    html.append("            <p><strong>Class:</strong> ").append(testInfo.getTestClass()).append("</p>\n");
                }
                if (testInfo.getTestMethod() != null) {
                    html.append("            <p><strong>Method:</strong> ").append(testInfo.getTestMethod()).append("</p>\n");
                }
                if (testInfo.getStartTime() != null) {
                    html.append("            <p><strong>Start Time:</strong> ").append(new SimpleDateFormat("HH:mm:ss.SSS").format(testInfo.getStartTime())).append("</p>\n");
                }
                if (testInfo.getEndTime() != null) {
                    html.append("            <p><strong>End Time:</strong> ").append(new SimpleDateFormat("HH:mm:ss.SSS").format(testInfo.getEndTime())).append("</p>\n");
                }
                html.append("            <p><strong>Duration:</strong> ").append(testInfo.getDuration()).append("ms</p>\n");
                
                if (testInfo.getErrorMessage() != null && !testInfo.getErrorMessage().isEmpty()) {
                    html.append("            <div class=\"error-message\">\n");
                    html.append("                <strong>Error:</strong> ").append(testInfo.getErrorMessage()).append("\n");
                    html.append("            </div>\n");
                }
                
                if (testInfo.getTestSteps() != null && !testInfo.getTestSteps().isEmpty()) {
                    html.append("            <div class=\"test-steps\">\n");
                    html.append("                <h4>Test Steps:</h4>\n");
                    for (String step : testInfo.getTestSteps()) {
                        html.append("                <div class=\"test-step\">").append(step).append("</div>\n");
                    }
                    html.append("            </div>\n");
                }
                
                if (testInfo.getScreenshotPath() != null && !testInfo.getScreenshotPath().isEmpty()) {
                    // Make screenshot path relative to the report directory
                    String relativeScreenshotPath = "../" + testInfo.getScreenshotPath().replace("\\", "/");
                    html.append("            <div class=\"screenshot\">\n");
                    html.append("                <h4>Screenshot:</h4>\n");
                    html.append("                <img src=\"").append(relativeScreenshotPath).append("\" alt=\"Test Screenshot\">\n");
                    html.append("            </div>\n");
                } else {
                    logger.debug("=== No screenshot path for test: {} ===", testInfo.getTestName());
                }
                
                html.append("        </div>\n");
                html.append("    </div>\n");
            }
            
            html.append("    <script>\n");
            html.append("        function toggleTest(element) {\n");
            html.append("            const content = element.nextElementSibling;\n");
            html.append("            if (content.style.display === 'none' || content.style.display === '') {\n");
            html.append("                content.style.display = 'block';\n");
            html.append("            } else {\n");
            html.append("                content.style.display = 'none';\n");
            html.append("            }\n");
            html.append("        }\n");
            html.append("    </script>\n");
            html.append("</body>\n");
            html.append("</html>");
            
            // Write the report to file
            try (FileWriter writer = new FileWriter(reportFilePath)) {
                writer.write(html.toString());
                logger.info("Enhanced test report generated: {}", reportFilePath);
            }
            
        } catch (IOException e) {
            logger.error("Failed to write enhanced report", e);
        }
    }
    
    public static class TestExecutionInfo {
        private String testName;
        private String testClass;
        private String testMethod;
        private String status;
        private Date startTime;
        private Date endTime;
        private long duration;
        private List<String> testSteps;
        private String errorMessage;
        private String screenshotPath;
        
        // Getters and Setters
        public String getTestName() { return testName; }
        public void setTestName(String testName) { this.testName = testName; }
        
        public String getTestClass() { return testClass; }
        public void setTestClass(String testClass) { this.testClass = testClass; }
        
        public String getTestMethod() { return testMethod; }
        public void setTestMethod(String testMethod) { this.testMethod = testMethod; }
        
        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
        
        public Date getStartTime() { return startTime; }
        public void setStartTime(Date startTime) { this.startTime = startTime; }
        
        public Date getEndTime() { return endTime; }
        public void setEndTime(Date endTime) { this.endTime = endTime; }
        
        public long getDuration() { return duration; }
        public void setDuration(long duration) { this.duration = duration; }
        
        public List<String> getTestSteps() { return testSteps; }
        public void setTestSteps(List<String> testSteps) { this.testSteps = testSteps; }
        
        public String getErrorMessage() { return errorMessage; }
        public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }
        
        public String getScreenshotPath() { return screenshotPath; }
        public void setScreenshotPath(String screenshotPath) { this.screenshotPath = screenshotPath; }
    }
} 