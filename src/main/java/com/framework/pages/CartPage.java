package com.framework.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Page Object for SauceDemo Cart Page
 */
public class CartPage extends BasePage {

    @FindBy(className = "title")
    private WebElement pageTitle;

    @FindBy(id = "continue-shopping")
    private WebElement continueShoppingButton;

    @FindBy(id = "checkout")
    private WebElement checkoutButton;

    @FindBy(className = "cart_item")
    private List<WebElement> cartItems;

    @FindBy(className = "inventory_item_name")
    private List<WebElement> itemNames;

    @FindBy(className = "inventory_item_price")
    private List<WebElement> itemPrices;

    @FindBy(className = "cart_quantity")
    private List<WebElement> itemQuantities;

    public CartPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Check if cart page is loaded
     */
    public boolean isPageLoaded() {
        return isElementDisplayed(pageTitle) && getPageTitle().equals("Your Cart");
    }

    /**
     * Get page title
     */
    public String getPageTitle() {
        return getElementText(pageTitle);
    }

    /**
     * Get number of items in cart
     */
    public int getCartItemCount() {
        return cartItems.size();
    }

    /**
     * Check if cart is empty
     */
    public boolean isCartEmpty() {
        return cartItems.isEmpty();
    }

    /**
     * Get all item names in cart
     */
    public List<String> getItemNames() {
        return itemNames.stream()
                .map(this::getElementText)
                .collect(Collectors.toList());
    }

    /**
     * Get all item prices in cart
     */
    public List<String> getItemPrices() {
        return itemPrices.stream()
                .map(this::getElementText)
                .collect(Collectors.toList());
    }

    /**
     * Check if item exists in cart by name
     */
    public boolean isItemInCart(String itemName) {
        return getItemNames().contains(itemName);
    }

    /**
     * Remove item from cart by name
     */
    public void removeItemFromCart(String itemName) {
        String xpath = String.format("//div[text()='%s']/ancestor::div[@class='cart_item']//button[text()='Remove']", itemName);
        WebElement removeButton = driver.findElement(By.xpath(xpath));
        clickElement(removeButton);
    }

    /**
     * Remove all items from cart
     */
    public void removeAllItems() {
        while (!cartItems.isEmpty()) {
            WebElement removeButton = cartItems.get(0).findElement(By.tagName("button"));
            clickElement(removeButton);
            waitForPageLoad();
        }
    }

    /**
     * Click continue shopping button
     */
    public void clickContinueShopping() {
        clickElement(continueShoppingButton);
    }

    /**
     * Click checkout button
     */
    public void clickCheckout() {
        clickElement(checkoutButton);
    }

    /**
     * Get item quantity by name
     */
    public String getItemQuantity(String itemName) {
        String xpath = String.format("//div[text()='%s']/ancestor::div[@class='cart_item']//div[@class='cart_quantity']", itemName);
        WebElement quantityElement = driver.findElement(By.xpath(xpath));
        return getElementText(quantityElement);
    }

    /**
     * Get item price by name
     */
    public String getItemPrice(String itemName) {
        String xpath = String.format("//div[text()='%s']/ancestor::div[@class='cart_item']//div[@class='inventory_item_price']", itemName);
        WebElement priceElement = driver.findElement(By.xpath(xpath));
        return getElementText(priceElement);
    }

    /**
     * Check if continue shopping button is displayed
     */
    public boolean isContinueShoppingButtonDisplayed() {
        return isElementDisplayed(continueShoppingButton);
    }

    /**
     * Check if checkout button is displayed
     */
    public boolean isCheckoutButtonDisplayed() {
        return isElementDisplayed(checkoutButton);
    }
}

