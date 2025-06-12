package com.periplus.tests.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ProductDetailPage extends BasePage {
    private By addToCartButton = By.xpath("//button[@class='btn btn-add-to-cart']");
    private By productTitle = By.xpath("//h1[@class='product-title'] | //h1");
    
    public ProductDetailPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public void addToCart() {
        // Wait for add to cart button to be clickable
        WebElement addButton = wait.until(
            ExpectedConditions.elementToBeClickable(addToCartButton)
        );
        addButton.click();
    }
    
    public String getProductTitle() {
        try {
            WebElement title = wait.until(
                ExpectedConditions.presenceOfElementLocated(productTitle)
            );
            return title.getText();
        } catch (Exception e) {
            return "";
        }
    }
}