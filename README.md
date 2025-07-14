# Regression Testing Framework

A comprehensive Selenium WebDriver-based automation framework for regression testing of the Zema Enterprise application.

## 🏗️ Project Structure

```
RegressionTesting/
├── core/                          # Core framework components
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   ├── pages/         # Page Object Model classes
│   │   │   │   └── utils/         # Utility classes
│   │   │   └── resources/         # Configuration files
│   │   └── test/
│   │       └── java/
│   │           └── BaseTest.java  # Base test class
├── market-analyzer/               # Application-specific tests
│   ├── src/
│   │   ├── main/
│   │   │   └── java/
│   │   │       └── pages/         # Application page objects
│   │   └── test/
│   │       ├── java/              # Test classes
│   │       └── resources/
│   │           └── testrunner/    # TestNG configuration
├── screenshots/                   # Screenshot directory (auto-created)
├── logs/                         # Log files directory
├── build.gradle                   # Root build configuration
├── settings.gradle               # Project settings
└── README.md                     # This file
```

## 🚀 Features

- **Page Object Model**: Maintainable and reusable page objects
- **WebDriver Manager**: Automatic browser driver management
- **Configuration Management**: Externalized test data and settings
- **Parallel Execution**: Support for parallel test execution
- **Comprehensive Logging**: Detailed test execution logs
- **Test Reporting**: Enhanced test results reporting
- **Multi-browser Support**: Chrome and Firefox support
- **Screenshot Capture**: Automatic and manual screenshot functionality
- **Custom Assertions**: Enhanced assertions with screenshot capture

## 📋 Prerequisites

- Java 11 or higher
- Gradle 7.0 or higher
- Chrome or Firefox browser

## 🛠️ Setup Instructions

1. **Clone the repository**:
   ```bash
   git clone <repository-url>
   cd RegressionTesting
   ```

2. **Build the project**:
   ```bash
   ./gradlew clean build
   ```

3. **Run tests**:
   ```bash
   # Run all tests
   ./gradlew test
   
   # Run specific test suite
   ./gradlew test --tests LoginTest
   
   # Run with specific browser
   ./gradlew test -Dbrowser=firefox
   ```

## 📁 Configuration

### Environment Configuration
Edit `core/src/main/resources/config.properties` to configure:
- Application URLs
- Test credentials
- Browser settings
- Timeout values
- Screenshot settings

### Screenshot Configuration
```properties
# Screenshot Configuration
screenshot.enabled=true
screenshot.directory=screenshots
screenshot.format=png
screenshot.retention.days=7
screenshot.on.failure=true
screenshot.on.assertion.failure=true
```

### Test Data
Test data is externalized in the configuration file:
```properties
test.username=test_user_automation
test.password=Password@123
```

## 📸 Screenshot Functionality

The framework provides comprehensive screenshot capture capabilities:

### Automatic Screenshot Capture
- **Test Failures**: Screenshots are automatically captured when tests fail
- **Assertion Failures**: Screenshots are captured when assertions fail
- **Test Listener**: Custom TestListener handles automatic screenshot capture

### Manual Screenshot Capture
```java
// Capture screenshot with test method name
String screenshotPath = captureScreenshot("testMethodName");

// Capture screenshot with custom name
String customScreenshot = captureScreenshotWithCustomName("custom_name");

// Capture screenshot on assertion failure
String failureScreenshot = captureScreenshotOnAssertionFailure(
    "testMethodName", 
    "Assertion message"
);
```

### Custom Assertions with Screenshots
```java
// Use custom assertions that automatically capture screenshots
TestAssertions.assertEquals(
    actualValue, 
    expectedValue, 
    "Assertion message", 
    driver, 
    "testMethodName", 
    "ClassName"
);
```

### Screenshot Features
- **Timestamped filenames**: Each screenshot includes timestamp
- **Organized storage**: Screenshots saved in `screenshots/` directory
- **Automatic cleanup**: Old screenshots automatically deleted (configurable retention)
- **Error handling**: Graceful handling of screenshot capture failures
- **Multiple formats**: Support for different screenshot formats

## 🧪 Test Structure

### Base Test Class
All test classes extend `BaseTest` which provides:
- WebDriver initialization
- Browser configuration
- Common setup and teardown
- Screenshot capture methods

### Page Objects
Page objects encapsulate web elements and actions:
- `LoginPage`: Login functionality
- `DashboardPage`: Dashboard navigation
- `BasePage`: Common page operations

### Test Classes
- `LoginTest`: Login functionality tests
- `CreateProfileTest`: Profile creation workflow tests
- `ScreenshotDemoTest`: Demo tests showing screenshot functionality

## 📊 Test Execution

### TestNG Configuration
Tests are configured in `market-analyzer/src/test/resources/testrunner/testng.xml`:
- Parallel execution support
- Test grouping
- Custom listeners for screenshot capture

### Running Tests
```bash
# Run all tests
./gradlew test

# Run specific test class
./gradlew test --tests LoginTest

# Run screenshot demo tests
./gradlew test --tests ScreenshotDemoTest

# Run with parallel execution
./gradlew test -Dparallel=true
```

## 📈 Reporting

### Test Results
- Test results are generated in `test-output/` directory
- HTML reports with detailed test execution information
- Console output with real-time test progress
- Screenshot paths included in test results

### Logging
- Logs are written to `logs/automation.log`
- Daily log rotation
- Different log levels for different components
- Screenshot capture events logged

### Screenshots
- Screenshots saved in `screenshots/` directory
- Automatic cleanup of old screenshots (7 days by default)
- Timestamped filenames for easy identification
- Organized by test class and method names

## 🔧 Maintenance

### Adding New Tests
1. Create page objects in `pages/` package
2. Extend `BaseTest` for new test classes
3. Add test methods with proper annotations
4. Use screenshot methods as needed
5. Update TestNG configuration if needed

### Adding New Page Objects
1. Extend `BasePage` class
2. Define locators as constants
3. Implement page-specific methods
4. Add proper wait conditions

### Screenshot Management
```bash
# Clean up old screenshots manually
# Screenshots older than 7 days are automatically cleaned up

# View screenshot directory
ls -la screenshots/

# Configure screenshot retention
# Edit config.properties: screenshot.retention.days=7
```

## 🐛 Troubleshooting

### Common Issues
1. **WebDriver not found**: Ensure WebDriver Manager is properly configured
2. **Element not found**: Check locators and wait conditions
3. **Test failures**: Review logs in `logs/automation.log`
4. **Screenshot failures**: Check disk space and permissions

### Screenshot Issues
1. **Screenshots not captured**: Verify screenshot.enabled=true in config
2. **Permission errors**: Ensure write permissions to screenshots directory
3. **Disk space**: Check available disk space for screenshot storage

### Debug Mode
Run tests with debug logging:
```bash
./gradlew test -Dlog.level=DEBUG
```

## 📝 Best Practices

1. **Page Object Model**: Always use page objects for web interactions
2. **Explicit Waits**: Use explicit waits instead of implicit waits
3. **Configuration**: Externalize all test data and settings
4. **Logging**: Use proper logging for debugging and monitoring
5. **Assertions**: Use meaningful assertion messages
6. **Test Independence**: Ensure tests can run independently
7. **Screenshots**: Use screenshots strategically for debugging
8. **Cleanup**: Regularly clean up old screenshots to save disk space

## 🤝 Contributing

1. Follow the existing code structure
2. Add proper documentation for new features
3. Include tests for new functionality
4. Update this README as needed
5. Use screenshot functionality appropriately

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details. 