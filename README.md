# Selenium Framework with Page Object Model

[![Selenium](https://img.shields.io/badge/selenium-4.x-green.svg)](https://www.selenium.dev/)
[![Java](https://img.shields.io/badge/java-21-orange.svg)](https://www.oracle.com/java/)
[![Maven](https://img.shields.io/badge/maven-3.6%2B-blue.svg)](https://maven.apache.org/)
[![TestNG](https://img.shields.io/badge/testng-7.x-red.svg)](https://testng.org/)

A streamlined Java-based Selenium automation framework built with Maven, TestNG, and ExtentReports. Features industry-standard test cases using **SauceDemo** - a fully-featured e-commerce application designed for testing.

## ✨ Features

- **Page Object Model (POM)** - Maintainable and reusable page objects
- **Maven** - Dependency management and build automation
- **TestNG** - Test execution and reporting framework
- **ExtentReports** - Beautiful HTML reports with screenshots (auto-opens after tests)
- **WebDriverManager** - Automatic driver management
- **Firefox Default** - Stable headless execution out of the box
- **Simplified Architecture** - Clean, focused test suite (31 core tests)
- **Auto-Report Opening** - Reports automatically open in browser after execution
- **Smart Cleanup** - Automatic old report cleanup on each run
- **Custom Maven Wrapper** - Easy-to-use commands for all operations
- **Configuration Management** - Centralized configuration
- **Java 21** - Latest LTS Java version

## 📁 Project Structure

```
Java Selenium Framework/
├── pom.xml                           # Maven configuration with auto-cleanup
├── README.md                         # This documentation
├── mvn-wrapper.sh                    # Custom Maven wrapper (auto-opens reports)
├── .gitignore                        # Git ignore rules
├── src/main/java/com/framework/
│   ├── config/
│   │   ├── WebDriverConfig.java     # WebDriver setup & management (Firefox default)
│   │   ├── ConfigReader.java        # Configuration file reader
│   │   └── EnvironmentConfig.java   # Environment variable support
│   ├── pages/
│   │   ├── BasePage.java            # Base page class with common methods
│   │   ├── LoginPage.java           # SauceDemo login page
│   │   ├── ProductsPage.java        # Products listing page
│   │   └── CartPage.java            # Shopping cart page
│   ├── reports/
│   │   ├── ExtentReportManager.java # HTML report management
│   │   └── ScreenshotUtils.java     # Screenshot utilities
│   └── utils/
│       ├── WebDriverUtils.java      # Common WebDriver operations
│       └── ExcelUtils.java          # Excel file operations
└── src/test/
    ├── java/com/framework/tests/
    │   ├── LoginTest.java           # Login tests (6 tests)
    │   ├── ProductTest.java         # Product tests (8 tests)
    │   └── CartTest.java            # Cart tests (17 tests)
    └── resources/
        ├── config/
        │   └── config.properties    # Configuration settings
        ├── testdata/
        │   └── testdata.json        # Test data
        └── testng.xml               # TestNG suite (sequential execution)
```

## 📋 Prerequisites

- **Java 21** (LTS - Latest Long Term Support)
- **Maven 3.6+**
- **Firefox** browser (default) or Chrome/Edge
- macOS/Linux/Windows

## Setup Instructions

1. **Clone or download the project**

2. **Navigate to project directory**
   ```bash
   cd "Java Selenium Framework"
   ```

3. **Set up environment variables (Important!)**
   ```bash
   # Copy the example environment file
   cp .env.example .env
   
   # Edit .env with your credentials (optional for demo)
   # The default values work for SauceDemo
   ```

4. **Install dependencies**
   ```bash
   mvn clean install
   ```

## 🚀 Running Tests

### ⚡ Quick Start (Recommended)

Use the custom Maven wrapper script - it auto-cleans old reports and opens results:

```bash
# Run all tests with Firefox (default) + auto-open report
./mvn-wrapper.sh test

# Run with Chrome + auto-open report
./mvn-wrapper.sh test-chrome

# Run in headless mode + auto-open report
./mvn-wrapper.sh test-headless

# Just open the latest report (without running tests)
./mvn-wrapper.sh report

# Show all available commands
./mvn-wrapper.sh help
```

### 🛠️ Maven Wrapper Commands

| Command | Description |
|---------|-------------|
| `./mvn-wrapper.sh test` | Run all tests (Firefox) + open report |
| `./mvn-wrapper.sh test-chrome` | Run with Chrome + open report |
| `./mvn-wrapper.sh test-firefox` | Run with Firefox + open report |
| `./mvn-wrapper.sh test-headless` | Run in headless mode + open report |
| `./mvn-wrapper.sh test-only` | Run tests WITHOUT opening report |
| `./mvn-wrapper.sh report` | Open the latest test report |
| `./mvn-wrapper.sh clean` | Clean project and old reports |
| `./mvn-wrapper.sh compile` | Compile project only |
| `./mvn-wrapper.sh install` | Install all dependencies |

### 🔧 Direct Maven Commands

#### Run all tests
```bash
mvn clean test
```

#### Run specific test class
```bash
# Run login tests (6 tests)
mvn test -Dtest=LoginTest

# Run product tests (8 tests)
mvn test -Dtest=ProductTest

# Run cart tests (17 tests)
mvn test -Dtest=CartTest
```

#### Run specific test method
```bash
# Run specific login test
mvn test -Dtest=LoginTest#testSuccessfulLogin

# Run specific product test
mvn test -Dtest=ProductTest#testSortProductsLowToHigh

# Run specific cart test
mvn test -Dtest=CartTest#testAddItemToCart
```

### 🌐 Browser-Specific Execution

```bash
# Firefox (default, headless)
mvn test

# Chrome
mvn test -Dbrowser=chrome

# Edge
mvn test -Dbrowser=edge

# Safari
mvn test -Dbrowser=safari
```

### 📊 View Test Reports

Reports automatically open when using `mvn-wrapper.sh` commands.

**Manual opening:**
```bash
# Open latest report (after cleaning, only one exists)
open test-output/reports/TestReport_*.html

# Or use the wrapper
./mvn-wrapper.sh report
```

### 🔧 Development Commands

#### Clean and compile
```bash
mvn clean compile
```

#### Install dependencies
```bash
mvn clean install
```

#### Run with debug information
```bash
mvn test -X
```

### 📋 Environment Setup

#### Set Java path (if needed)
```bash
export PATH="/opt/homebrew/opt/openjdk@11/bin:$PATH"
mvn test
```

### 📁 View Results

#### Check test reports
```bash
# View ExtentReports
open test-output/reports/TestReport_*.html

# View TestNG reports
open target/surefire-reports/index.html

# List all reports
ls -la test-output/reports/
```

### 🎯 Quick Start Examples

```bash
# Quick test run
mvn test -Dtest=SimpleTestRunner

# Full test suite with Chrome
mvn test -Dbrowser=chrome

# Headless testing
mvn test -Dbrowser=chrome -Dheadless=true

# Parallel execution
mvn test -Dparallel=methods -DthreadCount=2
```

## Test Application

This framework tests **SauceDemo** (https://www.saucedemo.com), a fully-featured e-commerce application designed for test automation practice.

### Available Test Users
- **standard_user** - Standard user with full access
- **locked_out_user** - User that has been locked out
- **problem_user** - User experiencing problems (images, sorting)
- **performance_glitch_user** - User with performance issues
- **Password for all users**: `secret_sauce`

## ✅ Test Coverage

**Total: 31 Comprehensive Tests** across 3 core areas

### 🔐 Login Tests (`LoginTest.java`) - 6 Tests
- ✅ Successful login with valid credentials
- ✅ Login failure with invalid username
- ✅ Login failure with invalid password
- ✅ Login failure with empty credentials
- ✅ Login failure with empty password
- ✅ Locked out user validation

### 🛍️ Product Tests (`ProductTest.java`) - 8 Tests
- ✅ Verify all products are displayed
- ✅ Sort products A to Z
- ✅ Sort products Z to A
- ✅ Sort products by price (low to high)
- ✅ Sort products by price (high to low)
- ✅ Verify specific product details
- ✅ Add single product to cart
- ✅ Add multiple products to cart

### 🛒 Cart Tests (`CartTest.java`) - 17 Tests
- ✅ Access cart page
- ✅ Verify empty cart
- ✅ Verify items appear in cart after adding
- ✅ Verify multiple items in cart
- ✅ Remove items from cart
- ✅ Continue shopping functionality
- ✅ Verify item quantities
- ✅ Verify item prices in cart
- ✅ Add/remove multiple items workflow
- ✅ Cart badge count validation
- ✅ Cart state persistence
- ✅ Item details in cart
- ✅ Remove last item from cart
- ✅ Multiple additions/removals
- ✅ Cart navigation
- ✅ Product to cart integration
- ✅ Complete cart workflow

## Configuration

### 🔐 Environment Variables (Recommended for Sensitive Data)

The framework supports `.env` files for managing sensitive data like passwords, API keys, and credentials:

```bash
# Create your .env file
cp .env.example .env

# Edit with your values
nano .env
```

**Available in `.env`:**
- User credentials (passwords, API keys)
- Database credentials
- Remote execution configuration (BrowserStack, Sauce Labs)
- Email/Slack notifications
- Cloud service credentials (AWS, etc.)

See [Environment Setup Guide](docs/ENVIRONMENT_SETUP.md) for complete documentation.

### ⚙️ Configuration Properties

Edit `src/test/resources/config/config.properties` for non-sensitive settings:

- Application URL (default: https://www.saucedemo.com)
- Browser settings
- Timeouts
- Report settings

**Priority:** `.env` values override `config.properties` values.

## Test Reports

After running tests, reports will be generated in:
- **ExtentReports**: `test-output/reports/TestReport_[timestamp].html`
- **Screenshots**: `test-output/screenshots/`
- **TestNG Reports**: `test-output/`

## 🔨 Adding New Tests

1. **Create a new page class** extending `BasePage` (PageFactory handled by BasePage)
2. **Add page elements** using `@FindBy` annotations
3. **Create test methods** in test classes
4. **Update testng.xml** to include new test classes

### Example Page Class
```java
public class NewPage extends BasePage {
    @FindBy(id = "elementId")
    private WebElement element;
    
    public NewPage(WebDriver driver) {
        super(driver); // BasePage handles PageFactory initialization
    }
    
    public void performAction() {
        clickElement(element);
    }
}
```

### Example Test Method
```java
@Test(description = "Test description")
public void testMethod() {
    try {
        WebDriver driver = WebDriverConfig.getDriver();
        // Test steps
        ExtentReportManager.logInfo("Test step executed");
        Assert.assertTrue(condition, "Assertion message");
        ExtentReportManager.logPass("Test passed");
    } catch (Exception e) {
        ExtentReportManager.logFail("Test failed: " + e.getMessage());
        throw e;
    }
}

@AfterMethod
public void tearDown() {
    WebDriverConfig.quitDriver(); // Always use quitDriver()
}
```

## Data-Driven Testing

### Excel Data
Use `ExcelUtils` class to read/write Excel files:
```java
List<Map<String, String>> testData = ExcelUtils.readExcelData("testdata.xlsx", "Sheet1");
```

### JSON Data
Place JSON test data files in `src/test/resources/testdata/` directory.

## Best Practices

1. **Use Page Object Model** - Keep page logic separate from test logic
2. **Use explicit waits** - Avoid Thread.sleep()
3. **Take screenshots** on test failures
4. **Use meaningful test names** and descriptions
5. **Keep tests independent** - Each test should be able to run standalone
6. **Use configuration files** - Avoid hardcoding values
7. **Clean up resources** - Always quit WebDriver in @AfterMethod

## Troubleshooting

### Common Issues

1. **WebDriver not found**
   - Ensure browser is installed
   - Check WebDriverManager setup

2. **Tests failing**
   - Check element locators
   - Verify application is accessible
   - Check timeout settings

3. **Reports not generating**
   - Ensure test-output directory exists
   - Check ExtentReportManager initialization

## Contributing

1. Follow existing code structure
2. Add proper documentation
3. Include test cases for new features
4. Update README if needed

## 📚 Complete Command Reference

### 🛠️ Maven Wrapper Commands (Recommended)
| Command | Description |
|---------|-------------|
| `./mvn-wrapper.sh test` | Run all tests (Firefox) + auto-open report |
| `./mvn-wrapper.sh test-chrome` | Run with Chrome + auto-open report |
| `./mvn-wrapper.sh test-firefox` | Run with Firefox + auto-open report |
| `./mvn-wrapper.sh test-headless` | Run in headless mode + auto-open report |
| `./mvn-wrapper.sh test-only` | Run tests WITHOUT opening report |
| `./mvn-wrapper.sh report` | Open the latest test report only |
| `./mvn-wrapper.sh clean` | Clean project and old reports |
| `./mvn-wrapper.sh compile` | Compile project only |
| `./mvn-wrapper.sh install` | Install all dependencies |
| `./mvn-wrapper.sh help` | Show all available commands |

### 🔧 Direct Maven Commands
| Command | Description |
|---------|-------------|
| `mvn clean test` | Clean and run all tests |
| `mvn test` | Run all tests |
| `mvn test -Dtest=LoginTest` | Run specific test class |
| `mvn test -Dtest=LoginTest#testSuccessfulLogin` | Run specific test method |
| `mvn test -Dbrowser=chrome` | Run with Chrome browser |
| `mvn test -Dbrowser=firefox` | Run with Firefox browser (default) |
| `mvn test -Dbrowser=edge` | Run with Edge browser |
| `mvn clean install` | Install dependencies |
| `mvn clean compile` | Clean and compile only |
| `mvn test -X` | Run with debug information |

### 🌍 Environment Variables
| Variable | Description | Example |
|----------|-------------|---------|
| `JAVA_HOME` | Java installation path | `/opt/homebrew/opt/openjdk@21` |
| `PATH` | Include Java bin directory | `export PATH="/opt/homebrew/opt/openjdk@21/bin:$PATH"` |
| `MAVEN_OPTS` | Maven JVM options | `-Xmx1024m` |

### Report Locations
| Report Type | Location | Description |
|-------------|----------|-------------|
| ExtentReports | `test-output/reports/` | Beautiful HTML reports with screenshots |
| TestNG Reports | `target/surefire-reports/` | Standard TestNG test results |
| Screenshots | `test-output/screenshots/` | Test failure screenshots |
| Logs | `logs/` | Application and test logs |

### Configuration Properties
| Property | Description | Default |
|----------|-------------|---------|
| `app.url` | Application base URL | `https://www.google.com` |
| `browser.name` | Default browser | `chrome` |
| `app.timeout` | Global timeout | `30` |
| `app.implicit.wait` | Implicit wait time | `10` |
| `browser.headless` | Headless mode | `false` |
| `browser.maximize` | Maximize window | `true` |

## 🚀 Quick Start Guide

1. **Navigate to project directory**
   ```bash
   cd "Java Selenium Framework"
   ```

2. **Install dependencies (first time only)**
   ```bash
   ./mvn-wrapper.sh install
   ```

3. **Run all tests (auto-opens report)**
   ```bash
   ./mvn-wrapper.sh test
   ```

That's it! The framework will:
- ✅ Clean old reports
- ✅ Run all 31 tests with Firefox (headless)
- ✅ Generate HTML report with screenshots
- ✅ Automatically open the report in your browser

## 🎯 Common Use Cases

### Development Workflow
```bash
# Run specific test class during development
mvn test -Dtest=LoginTest

# Run specific test method
mvn test -Dtest=LoginTest#testSuccessfulLogin

# Run without auto-opening report
./mvn-wrapper.sh test-only

# Open latest report manually
./mvn-wrapper.sh report
```

### CI/CD Pipeline
```bash
# Run tests in headless mode (default)
./mvn-wrapper.sh test-headless

# Or use Maven directly
mvn clean test
```

### Cross-Browser Testing
```bash
# Firefox (default, headless)
./mvn-wrapper.sh test

# Chrome
./mvn-wrapper.sh test-chrome

# Different browser with Maven
mvn test -Dbrowser=edge
mvn test -Dbrowser=safari
```

## License

This project is for educational and testing purposes.
