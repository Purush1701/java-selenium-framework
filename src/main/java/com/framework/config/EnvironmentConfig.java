package com.framework.config;

import io.github.cdimascio.dotenv.Dotenv;

/**
 * Environment Configuration Manager
 * Loads environment variables from .env file for sensitive data management
 * Similar to Cypress/Playwright environment file handling
 */
public class EnvironmentConfig {
    
    private static Dotenv dotenv;
    
    static {
        try {
            // Load .env file from project root
            dotenv = Dotenv.configure()
                    .directory("./")
                    .ignoreIfMissing()
                    .load();
        } catch (Exception e) {
            System.out.println("Warning: .env file not found. Using system environment variables.");
            dotenv = null;
        }
    }
    
    /**
     * Get environment variable value
     * First checks .env file, then system environment variables, then returns default value
     */
    public static String get(String key) {
        return get(key, null);
    }
    
    /**
     * Get environment variable value with default
     */
    public static String get(String key, String defaultValue) {
        String value = null;
        
        // Try to get from .env file
        if (dotenv != null) {
            value = dotenv.get(key);
        }
        
        // Fall back to system environment variable
        if (value == null) {
            value = System.getenv(key);
        }
        
        // Fall back to system property
        if (value == null) {
            value = System.getProperty(key);
        }
        
        // Return default if still null
        return value != null ? value : defaultValue;
    }
    
    /**
     * Get boolean environment variable
     */
    public static boolean getBoolean(String key, boolean defaultValue) {
        String value = get(key);
        if (value == null) {
            return defaultValue;
        }
        return Boolean.parseBoolean(value);
    }
    
    /**
     * Get integer environment variable
     */
    public static int getInt(String key, int defaultValue) {
        String value = get(key);
        if (value == null) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
    
    /**
     * Check if environment variable exists
     */
    public static boolean has(String key) {
        return get(key) != null;
    }
    
    /**
     * Get required environment variable (throws exception if not found)
     */
    public static String getRequired(String key) {
        String value = get(key);
        if (value == null) {
            throw new RuntimeException("Required environment variable not found: " + key);
        }
        return value;
    }
    
    // Application URLs
    public static String getAppUrl() {
        return get("APP_URL", ConfigReader.getProperty("app.url"));
    }
    
    public static String getAppUrlStaging() {
        return get("APP_URL_STAGING", getAppUrl());
    }
    
    public static String getAppUrlProduction() {
        return get("APP_URL_PRODUCTION", getAppUrl());
    }
    
    // Test User Credentials
    public static String getStandardUser() {
        return get("TEST_USER_STANDARD", "standard_user");
    }
    
    public static String getStandardPassword() {
        return get("TEST_USER_PASSWORD", "secret_sauce");
    }
    
    public static String getLockedUser() {
        return get("TEST_USER_LOCKED", "locked_out_user");
    }
    
    public static String getProblemUser() {
        return get("TEST_USER_PROBLEM", "problem_user");
    }
    
    public static String getPerformanceUser() {
        return get("TEST_USER_PERFORMANCE", "performance_glitch_user");
    }
    
    // Browser Configuration
    public static String getBrowser() {
        return get("BROWSER", ConfigReader.getProperty("browser.name", "chrome"));
    }
    
    public static boolean isHeadless() {
        return getBoolean("HEADLESS", false);
    }
    
    public static boolean shouldMaximizeBrowser() {
        return getBoolean("BROWSER_MAXIMIZE", true);
    }
    
    // Timeouts
    public static int getImplicitWait() {
        return getInt("IMPLICIT_WAIT", 10);
    }
    
    public static int getExplicitWait() {
        return getInt("EXPLICIT_WAIT", 30);
    }
    
    public static int getPageLoadTimeout() {
        return getInt("PAGE_LOAD_TIMEOUT", 60);
    }
    
    // Test Environment
    public static String getEnvironment() {
        return get("ENVIRONMENT", "test");
    }
    
    public static String getTestEnv() {
        return get("TEST_ENV", "local");
    }
    
    // Remote Execution
    public static boolean isRemoteExecution() {
        return getBoolean("REMOTE_EXECUTION", false);
    }
    
    public static String getGridUrl() {
        return get("GRID_URL", "http://localhost:4444");
    }
    
    public static String getBrowserStackUsername() {
        return get("BROWSERSTACK_USERNAME");
    }
    
    public static String getBrowserStackAccessKey() {
        return get("BROWSERSTACK_ACCESS_KEY");
    }
    
    public static String getBrowserStackBuildName() {
        return get("BROWSERSTACK_BUILD_NAME", "SauceDemo Tests");
    }
    
    public static String getSauceLabsUsername() {
        return get("SAUCE_LABS_USERNAME");
    }
    
    public static String getSauceLabsAccessKey() {
        return get("SAUCE_LABS_ACCESS_KEY");
    }
    
    // Database Configuration
    public static String getDbHost() {
        return get("DB_HOST", "localhost");
    }
    
    public static int getDbPort() {
        return getInt("DB_PORT", 3306);
    }
    
    public static String getDbName() {
        return get("DB_NAME", "testdb");
    }
    
    public static String getDbUsername() {
        return get("DB_USERNAME", "testuser");
    }
    
    public static String getDbPassword() {
        return get("DB_PASSWORD", "testpass");
    }
    
    public static String getDbUrl() {
        return get("DB_URL", "jdbc:mysql://localhost:3306/testdb");
    }
    
    // API Configuration
    public static String getApiBaseUrl() {
        return get("API_BASE_URL", "https://api.example.com");
    }
    
    public static String getApiKey() {
        return get("API_KEY");
    }
    
    public static String getApiSecret() {
        return get("API_SECRET");
    }
    
    public static int getApiTimeout() {
        return getInt("API_TIMEOUT", 30);
    }
    
    // Reporting
    public static String getReportPath() {
        return get("REPORT_PATH", "test-output/reports/");
    }
    
    public static String getScreenshotPath() {
        return get("SCREENSHOT_PATH", "test-output/screenshots/");
    }
    
    public static String getReportTitle() {
        return get("REPORT_TITLE", "SauceDemo E2E Test Report");
    }
    
    public static String getReportName() {
        return get("REPORT_NAME", "E-Commerce Automation Test Results");
    }
    
    public static boolean captureScreenshotOnFailure() {
        return getBoolean("CAPTURE_SCREENSHOT_ON_FAILURE", true);
    }
    
    public static boolean captureScreenshotOnSuccess() {
        return getBoolean("CAPTURE_SCREENSHOT_ON_SUCCESS", false);
    }
    
    // Email Configuration
    public static boolean isEmailEnabled() {
        return getBoolean("EMAIL_ENABLED", false);
    }
    
    public static String getEmailHost() {
        return get("EMAIL_HOST", "smtp.gmail.com");
    }
    
    public static int getEmailPort() {
        return getInt("EMAIL_PORT", 587);
    }
    
    public static String getEmailUsername() {
        return get("EMAIL_USERNAME");
    }
    
    public static String getEmailPassword() {
        return get("EMAIL_PASSWORD");
    }
    
    public static String getEmailRecipients() {
        return get("EMAIL_RECIPIENTS", "test@example.com");
    }
    
    // Slack Integration
    public static boolean isSlackEnabled() {
        return getBoolean("SLACK_ENABLED", false);
    }
    
    public static String getSlackWebhookUrl() {
        return get("SLACK_WEBHOOK_URL");
    }
    
    public static String getSlackChannel() {
        return get("SLACK_CHANNEL", "#test-automation");
    }
    
    // Parallel Execution
    public static boolean isParallelExecution() {
        return getBoolean("PARALLEL_EXECUTION", false);
    }
    
    public static int getThreadCount() {
        return getInt("THREAD_COUNT", 2);
    }
    
    // Logging
    public static String getLogLevel() {
        return get("LOG_LEVEL", "INFO");
    }
    
    public static boolean isVideoRecordingEnabled() {
        return getBoolean("ENABLE_VIDEO_RECORDING", false);
    }
    
    // Cloud Services
    public static String getAwsAccessKey() {
        return get("AWS_ACCESS_KEY");
    }
    
    public static String getAwsSecretKey() {
        return get("AWS_SECRET_KEY");
    }
    
    public static String getAwsS3Bucket() {
        return get("AWS_S3_BUCKET");
    }
    
    /**
     * Print all loaded environment variables (for debugging)
     * WARNING: This will expose sensitive data!
     */
    public static void printAllVariables() {
        System.out.println("=== Environment Configuration ===");
        System.out.println("APP_URL: " + getAppUrl());
        System.out.println("BROWSER: " + getBrowser());
        System.out.println("HEADLESS: " + isHeadless());
        System.out.println("ENVIRONMENT: " + getEnvironment());
        System.out.println("REMOTE_EXECUTION: " + isRemoteExecution());
        System.out.println("================================");
    }
}

