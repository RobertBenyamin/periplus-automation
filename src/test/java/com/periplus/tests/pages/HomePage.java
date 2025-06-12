package com.periplus.tests.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage extends BasePage {
    private By searchBox = By.name("filter_name");
    private By loginLinkByText = By.xpath("//a[contains(text(),'Sign In')]");
    private By cartCounter = By.id("cart_total");
    
    public HomePage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }
    
    public void navigateToLoginPage() {
        WebElement login = wait.until(
            ExpectedConditions.elementToBeClickable(loginLinkByText)
        );
        login.click();
    }
    
    public void searchProduct(String productName) {
        WebElement search = wait.until(
            ExpectedConditions.presenceOfElementLocated(searchBox)
        );
        search.clear();
        search.sendKeys(productName);
        search.submit();
    }
    
    public int getCartItemCount() {
        try {
            WebElement counter = wait.until(
                ExpectedConditions.presenceOfElementLocated(cartCounter)
            );
            return Integer.parseInt(counter.getText());
        } catch (Exception e) {
            return 0;
        }
    }
}