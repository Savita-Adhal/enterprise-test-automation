# PageFactory Pattern Guide

This guide explains how to use the PageFactory pattern as an advanced feature in your Selenium automation framework.

## Overview

PageFactory is a design pattern that provides a more elegant way to initialize page objects using annotations. It automatically initializes WebElements when the page object is created, reducing boilerplate code.

## Key Features

### 1. **@FindBy Annotations**
PageFactory uses `@FindBy` annotations to locate elements:

```java
@FindBy(id = "userName")
private WebElement usernameField;

@FindBy(name = "password")
private WebElement passwordField;

@FindBy(xpath = "//*[@type='submit']")
private WebElement loginButton;
```

### 2. **Automatic Element Initialization**
Elements are automatically initialized when the page object is created:

```java
public LoginPageFactory(WebDriver driver) {
    super(driver); // This calls PageFactory.initElements(driver, this);
}
```

### 3. **Cleaner Code**
No need to manually find elements - they're ready to use:

```java
public void enterUsername(String username) {
    sendKeys(usernameField, username); // Direct element usage
}
```

## Available @FindBy Strategies

| Strategy | Example | Description |
|----------|---------|-------------|
| `id` | `@FindBy(id = "userName")` | Find by element ID |
| `name` | `@FindBy(name = "password")` | Find by element name |
| `className` | `@FindBy(className = "error")` | Find by CSS class |
| `css` | `@FindBy(css = ".logo")` | Find by CSS selector |
| `xpath` | `@FindBy(xpath = "//button[@type='submit']")` | Find by XPath |
| `linkText` | `@FindBy(linkText = "Logout")` | Find by link text |
| `partialLinkText` | `@FindBy(partialLinkText = "Log")` | Find by partial link text |

## Project Structure

```
pages/
├── BasePage.java              # Traditional approach
├── BasePageFactory.java       # PageFactory approach
├── LoginPage.java             # Traditional LoginPage
├── LoginPageFactory.java      # PageFactory LoginPage
├── DashboardPage.java         # Traditional DashboardPage
├── DashboardPageFactory.java  # PageFactory DashboardPage
└── locators/                  # Separated locators (optional)
    ├── BaseLocators.java
    ├── LoginPageLocators.java
    └── DashboardPageLocators.java
```

## Usage Examples

### Traditional Approach (Existing)
```java
public class LoginPage extends BasePage {
    private static final By USERNAME_FIELD = By.id("userName");
    private static final By PASSWORD_FIELD = By.name("password");
    
    public void enterUsername(String username) {
        sendKeys(USERNAME_FIELD, username);
    }
}
```

### PageFactory Approach (New)
```java
public class LoginPageFactory extends BasePageFactory {
    @FindBy(id = "userName")
    private WebElement usernameField;
    
    @FindBy(name = "password")
    private WebElement passwordField;
    
    public void enterUsername(String username) {
        sendKeys(usernameField, username);
    }
}
```

## Benefits of PageFactory

### 1. **Cleaner Code**
- No need to manually find elements
- Direct element usage
- Less boilerplate code

### 2. **Better Performance**
- Elements are initialized once
- Lazy loading of elements
- Reduced element search time

### 3. **Easier Maintenance**
- Centralized element definitions
- Clear separation of concerns
- Easier to update locators

### 4. **Type Safety**
- Compile-time checking
- Better IDE support
- Reduced runtime errors

## When to Use PageFactory

### Use PageFactory When:
- ✅ You want cleaner, more maintainable code
- ✅ You have complex page objects with many elements
- ✅ You want better performance
- ✅ You're building new page objects

### Use Traditional Approach When:
- ✅ You need dynamic locators
- ✅ You're working with existing code
- ✅ You need more control over element finding
- ✅ You're dealing with complex element hierarchies

## Best Practices

### 1. **Element Naming**
```java
// Good
@FindBy(id = "userName")
private WebElement usernameField;

// Avoid
@FindBy(id = "userName")
private WebElement element1;
```

### 2. **Access Modifiers**
```java
// Use private for encapsulation
@FindBy(id = "userName")
private WebElement usernameField;

// Provide public methods for interaction
public void enterUsername(String username) {
    sendKeys(usernameField, username);
}
```

### 3. **Wait Strategies**
```java
// Use explicit waits in your methods
public void enterUsername(String username) {
    sendKeys(usernameField, username); // Uses wait from BasePageFactory
}
```

### 4. **Error Handling**
```java
public boolean isElementDisplayed(WebElement element) {
    try {
        return waitForElementVisible(element).isDisplayed();
    } catch (Exception e) {
        return false;
    }
}
```

## Running PageFactory Tests

```bash
# Run PageFactory demo tests
./gradlew test --tests PageFactoryDemoTest

# Run all tests including PageFactory
./gradlew test
```

## Migration Guide

### From Traditional to PageFactory

1. **Create PageFactory version**:
   ```java
   public class LoginPageFactory extends BasePageFactory {
       @FindBy(id = "userName")
       private WebElement usernameField;
       // ... other elements
   }
   ```

2. **Update test methods**:
   ```java
   // Before
   LoginPage loginPage = new LoginPage(driver);
   
   // After
   LoginPageFactory loginPage = new LoginPageFactory(driver);
   ```

3. **Update element interactions**:
   ```java
   // Before
   loginPage.sendKeys(LoginPageLocators.USERNAME_FIELD, username);
   
   // After
   loginPage.enterUsername(username);
   ```

## Troubleshooting

### Common Issues

1. **Element Not Found**
   - Check if `@FindBy` annotation is correct
   - Verify element exists in DOM
   - Ensure PageFactory is initialized

2. **StaleElementReferenceException**
   - Use explicit waits
   - Refresh page object if needed
   - Handle dynamic content properly

3. **Performance Issues**
   - Use lazy loading
   - Avoid unnecessary element searches
   - Cache frequently used elements

## Conclusion

PageFactory provides a more elegant and maintainable approach to page object creation. It's especially useful for complex pages with many elements. You can use both approaches in your project - traditional for existing code and PageFactory for new development.

For more examples, see the `PageFactoryDemoTest` class in the test suite. 