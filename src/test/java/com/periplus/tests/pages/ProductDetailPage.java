package com.periplus.tests.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.NoSuchElementException;
import com.periplus.tests.utils.UrlUtils;

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
        } catch (NoSuchElementException e) {
            throw new RuntimeException("Product title element not found on the page", e);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get product title", e);
        }
    }
    
    public String getProductPrice() {
        try {
            WebElement price = wait.until(
                ExpectedConditions.presenceOfElementLocated(productPrice)
            );
            return price.getText().trim();
        } catch (NoSuchElementException e) {
            throw new RuntimeException("Product price element not found on the page", e);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get product price", e);
        }
    }

    public String getProductId() {
        try {
            String currentUrl = driver.getCurrentUrl();
            String productIdFromUrl = UrlUtils.extractProductIdFromUrl(currentUrl);
            if (productIdFromUrl.isEmpty()) {
                throw new RuntimeException("Could not extract product ID from URL: " + currentUrl);
            }
            return productIdFromUrl;
        } catch (Exception e) {
            throw new RuntimeException("Failed to get product ID from current page URL", e);
        }
    }
}