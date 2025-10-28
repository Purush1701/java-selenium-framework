# Environment Configuration Guide

This framework uses `.env` files to manage sensitive data and environment-specific configuration, similar to Cypress and Playwright frameworks.

## 📁 Files Overview

### `.env.example`
- Template file with all available configuration options
- **Safe to commit** to version control
- Use this as a reference for setting up your `.env` file

### `.env`
- Contains actual sensitive data (passwords, API keys, etc.)
- **NEVER commit** to version control (already in `.gitignore`)
- Created by copying `.env.example` and filling in real values

## 🚀 Quick Setup

### 1. Create Your `.env` File

```bash
# Copy the example file
cp .env.example .env

# Edit with your actual values
nano .env
# or
code .env
```

### 2. Add Your Credentials

```properties
# Example .env file
TEST_USER_STANDARD=standard_user
TEST_USER_PASSWORD=secret_sauce
BROWSER=chrome
APP_URL=https://www.saucedemo.com
```

### 3. Use in Tests

```java
import com.framework.config.EnvironmentConfig;

public class MyTest {
    @Test
    public void testLogin() {
        String username = EnvironmentConfig.getStandardUser();
        String password = EnvironmentConfig.getStandardPassword();
        
        loginPage.login(username, password);
    }
}
```

## 📋 Available Configuration Options

### Application URLs
```properties
APP_URL=https://www.saucedemo.com
APP_URL_STAGING=https://staging.saucedemo.com
APP_URL_PRODUCTION=https://www.saucedemo.com
```

### Test User Credentials
```properties
TEST_USER_STANDARD=standard_user
TEST_USER_PASSWORD=secret_sauce
TEST_USER_LOCKED=locked_out_user
TEST_USER_PROBLEM=problem_user
TEST_USER_PERFORMANCE=performance_glitch_user
```

### Browser Configuration
```properties
BROWSER=chrome
HEADLESS=false
BROWSER_MAXIMIZE=true
```

### Timeouts
```properties
IMPLICIT_WAIT=10
EXPLICIT_WAIT=30
PAGE_LOAD_TIMEOUT=60
```

### Remote Execution (Selenium Grid / Cloud Providers)
```properties
REMOTE_EXECUTION=false
GRID_URL=http://localhost:4444

# BrowserStack
BROWSERSTACK_USERNAME=your_username
BROWSERSTACK_ACCESS_KEY=your_access_key
BROWSERSTACK_BUILD_NAME=My Test Build

# Sauce Labs
SAUCE_LABS_USERNAME=your_username
SAUCE_LABS_ACCESS_KEY=your_access_key
```

### Database Configuration
```properties
DB_HOST=localhost
DB_PORT=3306
DB_NAME=testdb
DB_USERNAME=testuser
DB_PASSWORD=testpass
DB_URL=jdbc:mysql://localhost:3306/testdb
```

### API Configuration
```properties
API_BASE_URL=https://api.example.com
API_KEY=your_api_key
API_SECRET=your_api_secret
API_TIMEOUT=30
```

### Reporting
```properties
REPORT_PATH=test-output/reports/
SCREENSHOT_PATH=test-output/screenshots/
CAPTURE_SCREENSHOT_ON_FAILURE=true
CAPTURE_SCREENSHOT_ON_SUCCESS=false
```

### Notifications
```properties
# Email
EMAIL_ENABLED=false
EMAIL_HOST=smtp.gmail.com
EMAIL_PORT=587
EMAIL_USERNAME=your_email@gmail.com
EMAIL_PASSWORD=your_app_password
EMAIL_RECIPIENTS=team@example.com

# Slack
SLACK_ENABLED=false
SLACK_WEBHOOK_URL=https://hooks.slack.com/services/YOUR/WEBHOOK/URL
SLACK_CHANNEL=#test-automation
```

### Parallel Execution
```properties
PARALLEL_EXECUTION=false
THREAD_COUNT=2
```

## 💻 Usage Examples

### Basic Usage
```java
// Get simple values
String url = EnvironmentConfig.getAppUrl();
String browser = EnvironmentConfig.getBrowser();
boolean headless = EnvironmentConfig.isHeadless();

// Get with default value
String apiKey = EnvironmentConfig.get("API_KEY", "default_key");
```

### Test Credentials
```java
@Test
public void testStandardUserLogin() {
    String username = EnvironmentConfig.getStandardUser();
    String password = EnvironmentConfig.getStandardPassword();
    
    driver.get(EnvironmentConfig.getAppUrl());
    loginPage.login(username, password);
}

@Test
public void testLockedUserLogin() {
    String username = EnvironmentConfig.getLockedUser();
    String password = EnvironmentConfig.getStandardPassword();
    
    loginPage.login(username, password);
    Assert.assertTrue(loginPage.isErrorMessageDisplayed());
}
```

### Environment-Specific Configuration
```java
@BeforeClass
public void setup() {
    String environment = EnvironmentConfig.getEnvironment();
    
    switch (environment) {
        case "staging":
            baseUrl = EnvironmentConfig.getAppUrlStaging();
            break;
        case "production":
            baseUrl = EnvironmentConfig.getAppUrlProduction();
            break;
        default:
            baseUrl = EnvironmentConfig.getAppUrl();
    }
    
    driver.get(baseUrl);
}
```

### Remote Execution
```java
@BeforeClass
public void setupRemote() {
    if (EnvironmentConfig.isRemoteExecution()) {
        String gridUrl = EnvironmentConfig.getGridUrl();
        // Configure remote driver
        driver = new RemoteWebDriver(new URL(gridUrl), capabilities);
    } else {
        driver = new ChromeDriver();
    }
}
```

## 🔒 Security Best Practices

### 1. Never Commit Sensitive Data
```bash
# Always verify .env is in .gitignore
cat .gitignore | grep .env

# Should see:
# .env
# .env.local
# .env.*.local
```

### 2. Use Different .env Files for Different Environments
```bash
# Development
.env

# Staging
.env.staging

# Production
.env.production

# Load specific environment
cp .env.staging .env
```

### 3. Rotate Credentials Regularly
- Change passwords periodically
- Rotate API keys
- Update access tokens

### 4. Use Environment-Specific Credentials
- Don't use production credentials in testing
- Use test accounts with limited permissions
- Separate API keys per environment

## 🔧 Maven Integration

### Run tests with environment variables from command line
```bash
# Override specific values
mvn test -DTEST_USER_STANDARD=custom_user -DAPP_URL=https://custom-url.com

# Set environment
mvn test -DENVIRONMENT=staging

# Enable remote execution
mvn test -DREMOTE_EXECUTION=true -DGRID_URL=http://localhost:4444
```

## 🐛 Troubleshooting

### .env File Not Loading
```java
// Check if .env file exists
File envFile = new File(".env");
System.out.println("Does .env exist? " + envFile.exists());

// Print loaded config (for debugging only!)
EnvironmentConfig.printAllVariables();
```

### Wrong Values Being Used
Priority order:
1. `.env` file
2. System environment variables
3. System properties (from Maven `-D` flags)
4. Default values

### File Not Found in CI/CD
- Create `.env` file in CI/CD pipeline
- Use CI/CD secrets management
- Set environment variables in CI/CD configuration

## 🚀 CI/CD Integration

### GitHub Actions
```yaml
- name: Create .env file
  run: |
    echo "APP_URL=${{ secrets.APP_URL }}" >> .env
    echo "TEST_USER_PASSWORD=${{ secrets.TEST_PASSWORD }}" >> .env
    
- name: Run tests
  run: mvn test
```

### Jenkins
```groovy
withCredentials([string(credentialsId: 'app-url', variable: 'APP_URL'),
                 string(credentialsId: 'test-password', variable: 'TEST_USER_PASSWORD')]) {
    sh '''
        echo "APP_URL=${APP_URL}" > .env
        echo "TEST_USER_PASSWORD=${TEST_USER_PASSWORD}" >> .env
        mvn test
    '''
}
```

### GitLab CI
```yaml
before_script:
  - echo "APP_URL=${APP_URL}" >> .env
  - echo "TEST_USER_PASSWORD=${TEST_USER_PASSWORD}" >> .env

test:
  script:
    - mvn test
```

## 📚 Additional Resources

- [dotenv-java Documentation](https://github.com/cdimascio/dotenv-java)
- [12-Factor App Config](https://12factor.net/config)
- [OWASP Secrets Management](https://cheatsheetseries.owasp.org/cheatsheets/Secrets_Management_Cheat_Sheet.html)

