package com.framework.config;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class WebDriverConfig {
    private static WebDriver driver;
    private static final String BROWSER = System.getProperty("browser", "firefox");
    private static final String GRID_URL = System.getProperty("gridUrl", "http://localhost:4444");
    private static boolean driversSetup = false;
    
    // Setup drivers once during class loading
    static {
        setupDrivers();
    }
    
    private static synchronized void setupDrivers() {
        if (!driversSetup) {
            try {
                switch (BROWSER.toLowerCase()) {
                    case "chrome":
                        WebDriverManager.chromedriver().setup();
                        break;
                    case "firefox":
                        WebDriverManager.firefoxdriver().setup();
                        break;
                    case "edge":
                        WebDriverManager.edgedriver().setup();
                        break;
                    default:
                        // Let Selenium Manager handle it
                        break;
                }
                driversSetup = true;
            } catch (Exception e) {
                System.err.println("Warning: Error during driver setup: " + e.getMessage());
                // Continue anyway, Selenium Manager will handle it
            }
        }
    }

    public static WebDriver getDriver() {
        if (driver == null || !isDriverActive()) {
            driver = createDriver();
        }
        return driver;
    }
    
    private static boolean isDriverActive() {
        if (driver == null) {
            return false;
        }
        try {
            // Try to get current URL to check if driver is still active
            driver.getCurrentUrl();
            return true;
        } catch (Exception e) {
            // Driver is not active, set to null for cleanup
            driver = null;
            return false;
        }
    }

    private static WebDriver createDriver() {
        switch (BROWSER.toLowerCase()) {
            case "chrome":
                return createChromeDriver();
            case "firefox":
                return createFirefoxDriver();
            case "edge":
                return createEdgeDriver();
            case "safari":
                return createSafariDriver();
            case "remote":
                return createRemoteDriver();
            default:
                throw new IllegalArgumentException("Unsupported browser: " + BROWSER);
        }
    }

    private static WebDriver createChromeDriver() {
        ChromeOptions options = new ChromeOptions();
        
        // Run in headless mode to avoid GUI issues
        options.addArguments("--headless=new");
        
        // Stability options to prevent macOS registration crashes
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");
        options.addArguments("--remote-debugging-port=0");
        
        // Window and display options
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--disable-extensions");
        
        // Disable password manager and autofill popups
        options.addArguments("--disable-save-password-bubble");
        options.addArguments("--disable-blink-features=AutomationControlled");
        options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
        options.setExperimentalOption("useAutomationExtension", false);
        
        // Disable password and autofill features
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        prefs.put("autofill.profile_enabled", false);
        prefs.put("autofill.credit_card_enabled", false);
        options.setExperimentalOption("prefs", prefs);
        
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
        return driver;
    }

    private static WebDriver createFirefoxDriver() {
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
        driver.manage().window().maximize();
        return driver;
    }

    private static WebDriver createEdgeDriver() {
        EdgeOptions options = new EdgeOptions();
        options.addArguments("--start-maximized");
        
        driver = new EdgeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
        return driver;
    }

    private static WebDriver createSafariDriver() {
        driver = new SafariDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
        return driver;
    }

    private static WebDriver createRemoteDriver() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setBrowserName(BROWSER);
        
        try {
            URI gridUri = new URI(GRID_URL);
            driver = new RemoteWebDriver(gridUri.toURL(), capabilities);
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
        } catch (URISyntaxException | MalformedURLException e) {
            throw new RuntimeException("Invalid Grid URL: " + GRID_URL, e);
        }
        return driver;
    }

    public static void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }

    public static void closeDriver() {
        if (driver != null) {
            driver.close();
        }
    }
}
