package com.periplus.tests.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ProductDetailPage extends BasePage {
    private By addToCartButton = By.xpath("//button[@class='btn btn-add-to-cart']");
    private By productTitle = By.xpath("//div[@class='col-lg-5 col-md-5 col-12 quickview-content']//h2");
    private By productPrice = By.xpath("//div[@class='quickview-price']//span[@class='special']");
    
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
    
    public String getProductPrice() {
        try {
            WebElement price = wait.until(
                ExpectedConditions.presenceOfElementLocated(productPrice)
            );
            return price.getText().trim();
        } catch (Exception e) {
            return "";
        }
    }

    public String getProductId() {
        try {
            String currentUrl = driver.getCurrentUrl();
            String productIdFromUrl = extractProductIdFromUrl(currentUrl);
            if (!productIdFromUrl.isEmpty()) {
                return productIdFromUrl;
            }
            
            return "";
        } catch (Exception e) {
            return "";
        }
    }

    private String extractProductIdFromUrl(String productUrl) {
        try {
            if (productUrl != null && productUrl.contains("/p/")) {
                String[] parts = productUrl.split("/p/");
                if (parts.length > 1) {
                    String afterP = parts[1];
                    String[] urlParts = afterP.split("/");
                    if (urlParts.length > 0) {
                        return urlParts[0];
                    }
                }
            }
            return "";
        } catch (Exception e) {
            return "";
        }
    }
}