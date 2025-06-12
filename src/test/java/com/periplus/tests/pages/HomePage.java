package com.periplus.tests.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.NoSuchElementException;

public class HomePage extends BasePage {
    private By searchBox = By.name("filter_name");
    private By loginLinkByText = By.xpath("//a[contains(text(),'Sign In')]");
    
    public HomePage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }
    
    public void navigateToLoginPage() {
        try {
            WebElement login = wait.until(
                ExpectedConditions.elementToBeClickable(loginLinkByText)
            );
            login.click();
        } catch (NoSuchElementException e) {
            throw new RuntimeException("Login link not found on the page", e);
        } catch (Exception e) {
            throw new RuntimeException("Failed to navigate to login page: " + e.getMessage(), e);
        }
    }
    
    public void searchProduct(String productName) {
        try {
            if (productName == null || productName.isEmpty()) {
                throw new IllegalArgumentException("Product name cannot be null or empty");
            }
            
            WebElement search = wait.until(
                ExpectedConditions.presenceOfElementLocated(searchBox)
            );
            search.clear();
            search.sendKeys(productName);
            search.submit();
        } catch (NoSuchElementException e) {
            throw new RuntimeException("Search box not found on the page", e);
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to search for product: " + e.getMessage(), e);
        }
    }
}