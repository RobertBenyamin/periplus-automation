package com.periplus.tests.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.NoSuchElementException;

public class LoginPage extends BasePage {
    // Locators
    private By emailField = By.name("email");
    private By passwordField = By.name("password");
    private By signInButton = By.id("button-login");
    private By personalInfoHeader = By.xpath("//h4[text()='Personal Information']");
    
    public LoginPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public void login(String email, String password) {
        try {
            // Validate inputs
            if (email == null || email.isEmpty()) {
                throw new IllegalArgumentException("Email cannot be null or empty");
            }
            
            if (password == null || password.isEmpty()) {
                throw new IllegalArgumentException("Password cannot be null or empty");
            }
            
            // Enter email
            WebElement emailElement = wait.until(
                ExpectedConditions.presenceOfElementLocated(emailField)
            );
            emailElement.clear();
            emailElement.sendKeys(email);
            
            // Enter password
            WebElement passwordElement = wait.until(
                ExpectedConditions.presenceOfElementLocated(passwordField)
            );
            passwordElement.clear();
            passwordElement.sendKeys(password);
            
            // Click sign in
            WebElement signInBtn = wait.until(
                ExpectedConditions.elementToBeClickable(signInButton)
            );
            signInBtn.click();
            
            // Wait for login to complete
            wait.until(ExpectedConditions.presenceOfElementLocated(personalInfoHeader));
        } catch (NoSuchElementException e) {
            throw new RuntimeException("Login element not found: " + e.getMessage(), e);
        } catch (IllegalArgumentException e) {
            throw e; // Re-throw validation errors
        } catch (Exception e) {
            throw new RuntimeException("Login failed: " + e.getMessage(), e);
        }
    }
}