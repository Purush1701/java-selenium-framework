package com.framework.tests;

import com.framework.config.ConfigReader;
import com.framework.config.WebDriverConfig;
import com.framework.pages.CartPage;
import com.framework.pages.LoginPage;
import com.framework.pages.ProductsPage;
import com.framework.reports.ExtentReportManager;
import com.framework.reports.ScreenshotUtils;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.List;

/**
 * Test Suite for Shopping Cart Functionality
 * Tests cart operations including add, remove, and navigation
 */
public class CartTest {

    private WebDriver driver;
    private LoginPage loginPage;
    private ProductsPage productsPage;
    private CartPage cartPage;
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
        cartPage = new CartPage(driver);
        
        // Login before each test
        loginPage.login("standard_user", "secret_sauce");
        loginPage.waitForPageLoad();
    }

    @Test(description = "Verify cart page can be accessed", priority = 1)
    public void testAccessCartPage() {
        ExtentReportManager.createTest("Access Cart Test", "Verify cart page can be accessed from products page");
        
        try {
            ExtentReportManager.logInfo("Clicking on shopping cart icon");
            productsPage.clickShoppingCart();
            productsPage.waitForPageLoad();
            
            ExtentReportManager.logInfo("Verifying cart page is loaded");
            Assert.assertTrue(cartPage.isPageLoaded(), "Cart page should be loaded");
            Assert.assertEquals(cartPage.getPageTitle(), "Your Cart", "Page title should be 'Your Cart'");
            
            ExtentReportManager.logPass("Access cart test passed");
        } catch (Exception e) {
            ExtentReportManager.logFail("Access cart test failed: " + e.getMessage());
            ScreenshotUtils.captureScreenshot(driver, "testAccessCartPage");
            throw e;
        }
    }

    @Test(description = "Verify empty cart displays no items", priority = 2)
    public void testEmptyCart() {
        ExtentReportManager.createTest("Empty Cart Test", "Verify empty cart displays no items");
        
        try {
            ExtentReportManager.logInfo("Navigating to cart page");
            productsPage.clickShoppingCart();
            productsPage.waitForPageLoad();
            
            ExtentReportManager.logInfo("Verifying cart is empty");
            Assert.assertTrue(cartPage.isCartEmpty(), "Cart should be empty");
            Assert.assertEquals(cartPage.getCartItemCount(), 0, "Cart item count should be 0");
            
            ExtentReportManager.logPass("Empty cart test passed");
        } catch (Exception e) {
            ExtentReportManager.logFail("Empty cart test failed: " + e.getMessage());
            ScreenshotUtils.captureScreenshot(driver, "testEmptyCart");
            throw e;
        }
    }

    @Test(description = "Verify item appears in cart after adding", priority = 3)
    public void testItemInCart() {
        ExtentReportManager.createTest("Item in Cart Test", "Verify added item appears in cart");
        
        try {
            String productName = "Sauce Labs Backpack";
            ExtentReportManager.logInfo("Adding product to cart: " + productName);
            productsPage.addProductToCart(productName);
            
            ExtentReportManager.logInfo("Navigating to cart");
            productsPage.clickShoppingCart();
            productsPage.waitForPageLoad();
            
            ExtentReportManager.logInfo("Verifying item is in cart");
            Assert.assertFalse(cartPage.isCartEmpty(), "Cart should not be empty");
            Assert.assertEquals(cartPage.getCartItemCount(), 1, "Cart should have 1 item");
            Assert.assertTrue(cartPage.isItemInCart(productName), "Product should be in cart");
            
            ExtentReportManager.logPass("Item in cart test passed");
        } catch (Exception e) {
            ExtentReportManager.logFail("Item in cart test failed: " + e.getMessage());
            ScreenshotUtils.captureScreenshot(driver, "testItemInCart");
            throw e;
        }
    }

    @Test(description = "Verify multiple items in cart", priority = 4)
    public void testMultipleItemsInCart() {
        ExtentReportManager.createTest("Multiple Items Test", "Verify multiple items appear in cart");
        
        try {
            ExtentReportManager.logInfo("Adding multiple products to cart");
            productsPage.addProductToCart("Sauce Labs Backpack");
            productsPage.addProductToCart("Sauce Labs Bike Light");
            productsPage.addProductToCart("Sauce Labs Bolt T-Shirt");
            
            ExtentReportManager.logInfo("Navigating to cart");
            productsPage.clickShoppingCart();
            productsPage.waitForPageLoad();
            
            ExtentReportManager.logInfo("Verifying all items are in cart");
            Assert.assertEquals(cartPage.getCartItemCount(), 3, "Cart should have 3 items");
            
            List<String> itemNames = cartPage.getItemNames();
            ExtentReportManager.logInfo("Items in cart: " + itemNames);
            Assert.assertTrue(itemNames.contains("Sauce Labs Backpack"), "Backpack should be in cart");
            Assert.assertTrue(itemNames.contains("Sauce Labs Bike Light"), "Bike Light should be in cart");
            Assert.assertTrue(itemNames.contains("Sauce Labs Bolt T-Shirt"), "T-Shirt should be in cart");
            
            ExtentReportManager.logPass("Multiple items test passed");
        } catch (Exception e) {
            ExtentReportManager.logFail("Multiple items test failed: " + e.getMessage());
            ScreenshotUtils.captureScreenshot(driver, "testMultipleItemsInCart");
            throw e;
        }
    }

    @Test(description = "Verify item can be removed from cart", priority = 5)
    public void testRemoveItemFromCart() {
        ExtentReportManager.createTest("Remove Item Test", "Verify item can be removed from cart");
        
        try {
            String productName = "Sauce Labs Backpack";
            ExtentReportManager.logInfo("Adding product to cart");
            productsPage.addProductToCart(productName);
            productsPage.clickShoppingCart();
            productsPage.waitForPageLoad();
            
            ExtentReportManager.logInfo("Removing item from cart: " + productName);
            cartPage.removeItemFromCart(productName);
            cartPage.waitForPageLoad();
            
            ExtentReportManager.logInfo("Verifying item is removed");
            Assert.assertTrue(cartPage.isCartEmpty(), "Cart should be empty after removing item");
            Assert.assertFalse(cartPage.isItemInCart(productName), "Product should not be in cart");
            
            ExtentReportManager.logPass("Remove item test passed");
        } catch (Exception e) {
            ExtentReportManager.logFail("Remove item test failed: " + e.getMessage());
            ScreenshotUtils.captureScreenshot(driver, "testRemoveItemFromCart");
            throw e;
        }
    }

    @Test(description = "Verify continue shopping returns to products page", priority = 6)
    public void testContinueShopping() {
        ExtentReportManager.createTest("Continue Shopping Test", "Verify continue shopping button returns to products page");
        
        try {
            ExtentReportManager.logInfo("Navigating to cart");
            productsPage.clickShoppingCart();
            productsPage.waitForPageLoad();
            
            ExtentReportManager.logInfo("Clicking continue shopping");
            cartPage.clickContinueShopping();
            cartPage.waitForPageLoad();
            
            ExtentReportManager.logInfo("Verifying returned to products page");
            Assert.assertTrue(productsPage.isPageLoaded(), "Should return to products page");
            
            ExtentReportManager.logPass("Continue shopping test passed");
        } catch (Exception e) {
            ExtentReportManager.logFail("Continue shopping test failed: " + e.getMessage());
            ScreenshotUtils.captureScreenshot(driver, "testContinueShopping");
            throw e;
        }
    }

    @Test(description = "Verify item quantities are displayed correctly", priority = 7)
    public void testItemQuantities() {
        ExtentReportManager.createTest("Item Quantities Test", "Verify item quantities are displayed correctly");
        
        try {
            String productName = "Sauce Labs Backpack";
            ExtentReportManager.logInfo("Adding product to cart");
            productsPage.addProductToCart(productName);
            productsPage.clickShoppingCart();
            productsPage.waitForPageLoad();
            
            ExtentReportManager.logInfo("Checking item quantity");
            String quantity = cartPage.getItemQuantity(productName);
            ExtentReportManager.logInfo("Quantity displayed: " + quantity);
            Assert.assertEquals(quantity, "1", "Item quantity should be 1");
            
            ExtentReportManager.logPass("Item quantities test passed");
        } catch (Exception e) {
            ExtentReportManager.logFail("Item quantities test failed: " + e.getMessage());
            ScreenshotUtils.captureScreenshot(driver, "testItemQuantities");
            throw e;
        }
    }

    @Test(description = "Verify item prices are displayed in cart", priority = 8)
    public void testItemPrices() {
        ExtentReportManager.createTest("Item Prices Test", "Verify item prices are displayed correctly in cart");
        
        try {
            String productName = "Sauce Labs Backpack";
            ExtentReportManager.logInfo("Getting price from products page");
            String productPagePrice = productsPage.getProductPrice(productName);
            
            productsPage.addProductToCart(productName);
            productsPage.clickShoppingCart();
            productsPage.waitForPageLoad();
            
            ExtentReportManager.logInfo("Getting price from cart");
            String cartPrice = cartPage.getItemPrice(productName);
            
            ExtentReportManager.logInfo("Product page price: " + productPagePrice);
            ExtentReportManager.logInfo("Cart price: " + cartPrice);
            Assert.assertEquals(cartPrice, productPagePrice, "Cart price should match product page price");
            
            ExtentReportManager.logPass("Item prices test passed");
        } catch (Exception e) {
            ExtentReportManager.logFail("Item prices test failed: " + e.getMessage());
            ScreenshotUtils.captureScreenshot(driver, "testItemPrices");
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

