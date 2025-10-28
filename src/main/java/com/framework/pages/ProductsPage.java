package com.framework.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Page Object for SauceDemo Products Page
 */
public class ProductsPage extends BasePage {

    @FindBy(className = "title")
    private WebElement pageTitle;

    @FindBy(className = "shopping_cart_link")
    private WebElement shoppingCartLink;

    @FindBy(className = "shopping_cart_badge")
    private WebElement cartBadge;

    @FindBy(id = "react-burger-menu-btn")
    private WebElement menuButton;

    @FindBy(id = "logout_sidebar_link")
    private WebElement logoutLink;

    @FindBy(className = "product_sort_container")
    private WebElement sortDropdown;

    @FindBy(className = "inventory_item")
    private List<WebElement> productItems;

    @FindBy(className = "inventory_item_name")
    private List<WebElement> productNames;

    @FindBy(className = "inventory_item_price")
    private List<WebElement> productPrices;

    public ProductsPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Check if products page is loaded
     */
    public boolean isPageLoaded() {
        return isElementDisplayed(pageTitle) && getPageTitle().equals("Products");
    }

    /**
     * Get page title text
     */
    public String getPageTitle() {
        return getElementText(pageTitle);
    }

    /**
     * Get count of products displayed
     */
    public int getProductCount() {
        return productItems.size();
    }

    /**
     * Add product to cart by name
     */
    public void addProductToCart(String productName) {
        String xpath = String.format("//div[text()='%s']/ancestor::div[@class='inventory_item']//button[text()='Add to cart']", productName);
        WebElement addButton = driver.findElement(By.xpath(xpath));
        clickElement(addButton);
    }

    /**
     * Add product to cart by index
     */
    public void addProductToCartByIndex(int index) {
        if (index >= 0 && index < productItems.size()) {
            WebElement product = productItems.get(index);
            WebElement addButton = product.findElement(By.tagName("button"));
            clickElement(addButton);
        }
    }

    /**
     * Remove product from cart by name
     */
    public void removeProductFromCart(String productName) {
        String xpath = String.format("//div[text()='%s']/ancestor::div[@class='inventory_item']//button[text()='Remove']", productName);
        WebElement removeButton = driver.findElement(By.xpath(xpath));
        clickElement(removeButton);
    }

    /**
     * Click on product name to view details
     */
    public void clickProductByName(String productName) {
        String xpath = String.format("//div[text()='%s']", productName);
        WebElement product = driver.findElement(By.xpath(xpath));
        clickElement(product);
    }

    /**
     * Get cart badge count
     */
    public String getCartBadgeCount() {
        if (isElementDisplayed(cartBadge)) {
            return getElementText(cartBadge);
        }
        return "0";
    }

    /**
     * Check if cart badge is displayed
     */
    public boolean isCartBadgeDisplayed() {
        return isElementDisplayed(cartBadge);
    }

    /**
     * Click shopping cart icon
     */
    public void clickShoppingCart() {
        clickElement(shoppingCartLink);
    }

    /**
     * Sort products by option
     * Options: "az", "za", "lohi", "hilo"
     */
    public void sortProducts(String sortOption) {
        Select select = new Select(sortDropdown);
        select.selectByValue(sortOption);
        waitForPageLoad();
    }

    /**
     * Get all product names
     */
    public List<String> getAllProductNames() {
        return productNames.stream()
                .map(this::getElementText)
                .collect(Collectors.toList());
    }

    /**
     * Get all product prices
     */
    public List<String> getAllProductPrices() {
        return productPrices.stream()
                .map(this::getElementText)
                .collect(Collectors.toList());
    }

    /**
     * Open menu
     */
    public void openMenu() {
        clickElement(menuButton);
        waitForElement(logoutLink, 5);
    }

    /**
     * Logout from application
     */
    public void logout() {
        openMenu();
        clickElement(logoutLink);
    }

    /**
     * Check if product exists by name
     */
    public boolean isProductDisplayed(String productName) {
        String xpath = String.format("//div[text()='%s']", productName);
        try {
            WebElement product = driver.findElement(By.xpath(xpath));
            return product.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Get product price by name
     */
    public String getProductPrice(String productName) {
        String xpath = String.format("//div[text()='%s']/ancestor::div[@class='inventory_item']//div[@class='inventory_item_price']", productName);
        WebElement priceElement = driver.findElement(By.xpath(xpath));
        return getElementText(priceElement);
    }
}

