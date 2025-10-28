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

import java.util.List;

/**
 * Test Suite for Product Functionality
 * Tests product listing, sorting, and filtering
 */
public class ProductTest {

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
        
        // Login before each test
        loginPage.login("standard_user", "secret_sauce");
        loginPage.waitForPageLoad();
    }

    @Test(description = "Verify products page displays all products", priority = 1)
    public void testProductsDisplayed() {
        ExtentReportManager.createTest("Products Display Test", "Verify all products are displayed on products page");
        
        try {
            ExtentReportManager.logInfo("Verifying products page is loaded");
            Assert.assertTrue(productsPage.isPageLoaded(), "Products page should be loaded");
            
            int productCount = productsPage.getProductCount();
            ExtentReportManager.logInfo("Number of products displayed: " + productCount);
            Assert.assertTrue(productCount > 0, "At least one product should be displayed");
            Assert.assertEquals(productCount, 6, "Should display 6 products");
            
            ExtentReportManager.logPass("Products display test passed");
        } catch (Exception e) {
            ExtentReportManager.logFail("Products display test failed: " + e.getMessage());
            ScreenshotUtils.captureScreenshot(driver, "testProductsDisplayed");
            throw e;
        }
    }

    @Test(description = "Verify products can be sorted A to Z", priority = 2)
    public void testSortProductsAtoZ() {
        ExtentReportManager.createTest("Sort Products A-Z Test", "Verify products can be sorted alphabetically A to Z");
        
        try {
            ExtentReportManager.logInfo("Sorting products A to Z");
            productsPage.sortProducts("az");
            
            List<String> productNames = productsPage.getAllProductNames();
            ExtentReportManager.logInfo("Products in order: " + productNames);
            
            // Verify first product
            Assert.assertEquals(productNames.get(0), "Sauce Labs Backpack", 
                "First product should be 'Sauce Labs Backpack'");
            
            ExtentReportManager.logPass("Sort A-Z test passed");
        } catch (Exception e) {
            ExtentReportManager.logFail("Sort A-Z test failed: " + e.getMessage());
            ScreenshotUtils.captureScreenshot(driver, "testSortProductsAtoZ");
            throw e;
        }
    }

    @Test(description = "Verify products can be sorted Z to A", priority = 3)
    public void testSortProductsZtoA() {
        ExtentReportManager.createTest("Sort Products Z-A Test", "Verify products can be sorted alphabetically Z to A");
        
        try {
            ExtentReportManager.logInfo("Sorting products Z to A");
            productsPage.sortProducts("za");
            
            List<String> productNames = productsPage.getAllProductNames();
            ExtentReportManager.logInfo("Products in order: " + productNames);
            
            // Verify first product (should start with T)
            Assert.assertTrue(productNames.get(0).startsWith("Test.allTheThings()"), 
                "First product should start with 'Test.allTheThings()'");
            
            ExtentReportManager.logPass("Sort Z-A test passed");
        } catch (Exception e) {
            ExtentReportManager.logFail("Sort Z-A test failed: " + e.getMessage());
            ScreenshotUtils.captureScreenshot(driver, "testSortProductsZtoA");
            throw e;
        }
    }

    @Test(description = "Verify products can be sorted by price low to high", priority = 4)
    public void testSortProductsLowToHigh() {
        ExtentReportManager.createTest("Sort Products Low-High Test", "Verify products can be sorted by price low to high");
        
        try {
            ExtentReportManager.logInfo("Sorting products low to high");
            productsPage.sortProducts("lohi");
            
            List<String> productPrices = productsPage.getAllProductPrices();
            ExtentReportManager.logInfo("Product prices in order: " + productPrices);
            
            // Verify first price is lowest
            Assert.assertTrue(productPrices.get(0).contains("7.99") || productPrices.get(0).contains("9.99"), 
                "First product should have the lowest price");
            
            ExtentReportManager.logPass("Sort low-high test passed");
        } catch (Exception e) {
            ExtentReportManager.logFail("Sort low-high test failed: " + e.getMessage());
            ScreenshotUtils.captureScreenshot(driver, "testSortProductsLowToHigh");
            throw e;
        }
    }

    @Test(description = "Verify products can be sorted by price high to low", priority = 5)
    public void testSortProductsHighToLow() {
        ExtentReportManager.createTest("Sort Products High-Low Test", "Verify products can be sorted by price high to low");
        
        try {
            ExtentReportManager.logInfo("Sorting products high to low");
            productsPage.sortProducts("hilo");
            
            List<String> productPrices = productsPage.getAllProductPrices();
            ExtentReportManager.logInfo("Product prices in order: " + productPrices);
            
            // Verify first price is highest
            Assert.assertTrue(productPrices.get(0).contains("49.99"), 
                "First product should have the highest price");
            
            ExtentReportManager.logPass("Sort high-low test passed");
        } catch (Exception e) {
            ExtentReportManager.logFail("Sort high-low test failed: " + e.getMessage());
            ScreenshotUtils.captureScreenshot(driver, "testSortProductsHighToLow");
            throw e;
        }
    }

    @Test(description = "Verify specific product is displayed", priority = 6)
    public void testSpecificProductDisplayed() {
        ExtentReportManager.createTest("Specific Product Test", "Verify specific product is displayed");
        
        try {
            String productName = "Sauce Labs Backpack";
            ExtentReportManager.logInfo("Checking if product exists: " + productName);
            
            Assert.assertTrue(productsPage.isProductDisplayed(productName), 
                "Product '" + productName + "' should be displayed");
            
            String price = productsPage.getProductPrice(productName);
            ExtentReportManager.logInfo("Product price: " + price);
            Assert.assertFalse(price.isEmpty(), "Product should have a price");
            
            ExtentReportManager.logPass("Specific product test passed");
        } catch (Exception e) {
            ExtentReportManager.logFail("Specific product test failed: " + e.getMessage());
            ScreenshotUtils.captureScreenshot(driver, "testSpecificProductDisplayed");
            throw e;
        }
    }

    @Test(description = "Verify add to cart functionality", priority = 7)
    public void testAddToCart() {
        ExtentReportManager.createTest("Add to Cart Test", "Verify product can be added to cart");
        
        try {
            String productName = "Sauce Labs Backpack";
            ExtentReportManager.logInfo("Adding product to cart: " + productName);
            
            Assert.assertFalse(productsPage.isCartBadgeDisplayed(), "Cart should be empty initially");
            
            productsPage.addProductToCart(productName);
            productsPage.waitForPageLoad();
            
            ExtentReportManager.logInfo("Verifying cart badge is displayed");
            Assert.assertTrue(productsPage.isCartBadgeDisplayed(), "Cart badge should be displayed");
            Assert.assertEquals(productsPage.getCartBadgeCount(), "1", "Cart should show 1 item");
            
            ExtentReportManager.logPass("Add to cart test passed");
        } catch (Exception e) {
            ExtentReportManager.logFail("Add to cart test failed: " + e.getMessage());
            ScreenshotUtils.captureScreenshot(driver, "testAddToCart");
            throw e;
        }
    }

    @Test(description = "Verify multiple products can be added to cart", priority = 8)
    public void testAddMultipleProductsToCart() {
        ExtentReportManager.createTest("Add Multiple Products Test", "Verify multiple products can be added to cart");
        
        try {
            ExtentReportManager.logInfo("Adding multiple products to cart");
            
            productsPage.addProductToCart("Sauce Labs Backpack");
            productsPage.addProductToCart("Sauce Labs Bike Light");
            productsPage.addProductToCart("Sauce Labs Bolt T-Shirt");
            productsPage.waitForPageLoad();
            
            ExtentReportManager.logInfo("Verifying cart badge count");
            Assert.assertEquals(productsPage.getCartBadgeCount(), "3", "Cart should show 3 items");
            
            ExtentReportManager.logPass("Add multiple products test passed");
        } catch (Exception e) {
            ExtentReportManager.logFail("Add multiple products test failed: " + e.getMessage());
            ScreenshotUtils.captureScreenshot(driver, "testAddMultipleProductsToCart");
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

