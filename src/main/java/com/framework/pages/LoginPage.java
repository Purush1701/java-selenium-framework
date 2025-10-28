package com.framework.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Page Object for SauceDemo Login Page
 * URL: https://www.saucedemo.com
 */
public class LoginPage extends BasePage {

    @FindBy(id = "user-name")
    private WebElement usernameField;

    @FindBy(id = "password")
    private WebElement passwordField;

    @FindBy(id = "login-button")
    private WebElement loginButton;

    @FindBy(xpath = "//h3[@data-test='error']")
    private WebElement errorMessage;

    @FindBy(className = "login_logo")
    private WebElement loginLogo;

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Check if login page is loaded
     */
    public boolean isPageLoaded() {
        return isElementDisplayed(loginLogo) && isElementDisplayed(loginButton);
    }

    /**
     * Perform login with username and password
     */
    public void login(String username, String password) {
        enterText(usernameField, username);
        enterText(passwordField, password);
        clickElement(loginButton);
    }

    /**
     * Enter username only
     */
    public void enterUsername(String username) {
        enterText(usernameField, username);
    }

    /**
     * Enter password only
     */
    public void enterPassword(String password) {
        enterText(passwordField, password);
    }

    /**
     * Click login button
     */
    public void clickLoginButton() {
        clickElement(loginButton);
    }

    /**
     * Get error message text
     */
    public String getErrorMessage() {
        return getElementText(errorMessage);
    }

    /**
     * Check if error message is displayed
     */
    public boolean isErrorMessageDisplayed() {
        return isElementDisplayed(errorMessage);
    }

    /**
     * Clear username field
     */
    public void clearUsername() {
        usernameField.clear();
    }

    /**
     * Clear password field
     */
    public void clearPassword() {
        passwordField.clear();
    }

    /**
     * Check if username field is displayed
     */
    public boolean isUsernameFieldDisplayed() {
        return isElementDisplayed(usernameField);
    }

    /**
     * Check if password field is displayed
     */
    public boolean isPasswordFieldDisplayed() {
        return isElementDisplayed(passwordField);
    }

    /**
     * Check if login button is enabled
     */
    public boolean isLoginButtonEnabled() {
        return loginButton.isEnabled();
    }
}

