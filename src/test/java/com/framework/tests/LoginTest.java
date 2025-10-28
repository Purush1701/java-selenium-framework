package com.framework.tests;

import com.framework.config.ConfigReader;
import com.framework.config.WebDriverConfig;
import com.framework.pages.LoginPage;
import com.framework.pages.ProductsPage;
import com.framework.reports.ExtentReportManager;
import com.framework.reports.ScreenshotUtils;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;

/**
 * Test Suite for Login Functionality
 * Tests various login scenarios including valid and invalid credentials
 */
public class LoginTest {

    private WebDriver driver;
    private LoginPage loginPage;
    private ProductsPage productsPage;
    private String baseUrl;

    @BeforeClass
    public void setupClass() {
        ExtentReportManager.initReport();
        baseUrl = ConfigReader.getProperty("app.url");
    }

    @BeforeMethod
    public void setup() {
        driver = WebDriverConfig.getDriver();
        driver.get(baseUrl);
        loginPage = new LoginPage(driver);
        productsPage = new ProductsPage(driver);
    }

    @Test(description = "Verify successful login with valid credentials", priority = 1)
    public void testSuccessfulLogin() {
        ExtentReportManager.createTest("Successful Login Test", "Verify user can login with valid credentials");
        
        try {
            ExtentReportManager.logInfo("Navigating to login page: " + baseUrl);
            Assert.assertTrue(loginPage.isPageLoaded(), "Login page should be loaded");
            
            ExtentReportManager.logInfo("Entering username: standard_user");
            loginPage.enterUsername("standard_user");
            
            ExtentReportManager.logInfo("Entering password");
            loginPage.enterPassword("secret_sauce");
            
            ExtentReportManager.logInfo("Clicking login button");
            loginPage.clickLoginButton();
            
            ExtentReportManager.logInfo("Waiting for products page to load");
            loginPage.waitForPageLoad();
            
            ExtentReportManager.logInfo("Verifying products page is displayed");
            Assert.assertTrue(productsPage.isPageLoaded(), "Products page should be displayed after successful login");
            Assert.assertEquals(productsPage.getPageTitle(), "Products", "Page title should be 'Products'");
            
            ExtentReportManager.logPass("Login test passed successfully");
        } catch (Exception e) {
            ExtentReportManager.logFail("Login test failed: " + e.getMessage());
            ScreenshotUtils.captureScreenshot(driver, "testSuccessfulLogin");
            throw e;
        }
    }

    @Test(description = "Verify login fails with invalid username", priority = 2)
    public void testLoginWithInvalidUsername() {
        ExtentReportManager.createTest("Invalid Username Test", "Verify login fails with invalid username");
        
        try {
            ExtentReportManager.logInfo("Attempting login with invalid username");
            loginPage.login("invalid_user", "secret_sauce");
            
            ExtentReportManager.logInfo("Verifying error message is displayed");
            Assert.assertTrue(loginPage.isErrorMessageDisplayed(), "Error message should be displayed");
            
            String errorMessage = loginPage.getErrorMessage();
            ExtentReportManager.logInfo("Error message: " + errorMessage);
            Assert.assertTrue(errorMessage.contains("Username and password do not match"), 
                "Error message should indicate invalid credentials");
            
            ExtentReportManager.logPass("Invalid username test passed");
        } catch (Exception e) {
            ExtentReportManager.logFail("Invalid username test failed: " + e.getMessage());
            ScreenshotUtils.captureScreenshot(driver, "testLoginWithInvalidUsername");
            throw e;
        }
    }

    @Test(description = "Verify login fails with invalid password", priority = 3)
    public void testLoginWithInvalidPassword() {
        ExtentReportManager.createTest("Invalid Password Test", "Verify login fails with invalid password");
        
        try {
            ExtentReportManager.logInfo("Attempting login with invalid password");
            loginPage.login("standard_user", "wrong_password");
            
            ExtentReportManager.logInfo("Verifying error message is displayed");
            Assert.assertTrue(loginPage.isErrorMessageDisplayed(), "Error message should be displayed");
            
            String errorMessage = loginPage.getErrorMessage();
            ExtentReportManager.logInfo("Error message: " + errorMessage);
            Assert.assertTrue(errorMessage.contains("Username and password do not match"), 
                "Error message should indicate invalid credentials");
            
            ExtentReportManager.logPass("Invalid password test passed");
        } catch (Exception e) {
            ExtentReportManager.logFail("Invalid password test failed: " + e.getMessage());
            ScreenshotUtils.captureScreenshot(driver, "testLoginWithInvalidPassword");
            throw e;
        }
    }

    @Test(description = "Verify login fails with empty credentials", priority = 4)
    public void testLoginWithEmptyCredentials() {
        ExtentReportManager.createTest("Empty Credentials Test", "Verify login fails with empty username and password");
        
        try {
            ExtentReportManager.logInfo("Attempting login with empty credentials");
            loginPage.clickLoginButton();
            
            ExtentReportManager.logInfo("Verifying error message is displayed");
            Assert.assertTrue(loginPage.isErrorMessageDisplayed(), "Error message should be displayed");
            
            String errorMessage = loginPage.getErrorMessage();
            ExtentReportManager.logInfo("Error message: " + errorMessage);
            Assert.assertTrue(errorMessage.contains("Username is required"), 
                "Error message should indicate username is required");
            
            ExtentReportManager.logPass("Empty credentials test passed");
        } catch (Exception e) {
            ExtentReportManager.logFail("Empty credentials test failed: " + e.getMessage());
            ScreenshotUtils.captureScreenshot(driver, "testLoginWithEmptyCredentials");
            throw e;
        }
    }

    @Test(description = "Verify login fails with empty password", priority = 5)
    public void testLoginWithEmptyPassword() {
        ExtentReportManager.createTest("Empty Password Test", "Verify login fails with empty password");
        
        try {
            ExtentReportManager.logInfo("Entering username only");
            loginPage.enterUsername("standard_user");
            loginPage.clickLoginButton();
            
            ExtentReportManager.logInfo("Verifying error message is displayed");
            Assert.assertTrue(loginPage.isErrorMessageDisplayed(), "Error message should be displayed");
            
            String errorMessage = loginPage.getErrorMessage();
            ExtentReportManager.logInfo("Error message: " + errorMessage);
            Assert.assertTrue(errorMessage.contains("Password is required"), 
                "Error message should indicate password is required");
            
            ExtentReportManager.logPass("Empty password test passed");
        } catch (Exception e) {
            ExtentReportManager.logFail("Empty password test failed: " + e.getMessage());
            ScreenshotUtils.captureScreenshot(driver, "testLoginWithEmptyPassword");
            throw e;
        }
    }

    @Test(description = "Verify login with locked out user", priority = 6)
    public void testLoginWithLockedOutUser() {
        ExtentReportManager.createTest("Locked Out User Test", "Verify login fails for locked out user");
        
        try {
            ExtentReportManager.logInfo("Attempting login with locked out user");
            loginPage.login("locked_out_user", "secret_sauce");
            
            ExtentReportManager.logInfo("Verifying error message is displayed");
            Assert.assertTrue(loginPage.isErrorMessageDisplayed(), "Error message should be displayed");
            
            String errorMessage = loginPage.getErrorMessage();
            ExtentReportManager.logInfo("Error message: " + errorMessage);
            Assert.assertTrue(errorMessage.contains("Sorry, this user has been locked out"), 
                "Error message should indicate user is locked out");
            
            ExtentReportManager.logPass("Locked out user test passed");
        } catch (Exception e) {
            ExtentReportManager.logFail("Locked out user test failed: " + e.getMessage());
            ScreenshotUtils.captureScreenshot(driver, "testLoginWithLockedOutUser");
            throw e;
        }
    }

    @AfterMethod
    public void teardown() {
        WebDriverConfig.quitDriver();
    }

    @AfterClass
    public void teardownClass() {
        ExtentReportManager.flushReport();
    }
}

