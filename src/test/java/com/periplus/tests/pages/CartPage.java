package com.periplus.tests.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.NoSuchElementException;
import com.periplus.tests.utils.UrlUtils;
import java.util.List;

public class CartPage extends BasePage {
    
    private By cartProductRows = By.xpath("//div[@class='row row-cart-product']");
    private By subTotal = By.id("sub_total");
    private By cartCounter = By.id("cart_total_mobile");
    
    public CartPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public boolean isProductInCart(String productId) {
        validateProductId(productId);

        try {
            WebElement productRow = getProductRowByProductId(productId);
            return productRow != null;
        } catch (Exception e) {
            throw new RuntimeException("Error checking if product " + productId + " is in cart", e);
        }
    }

    public String getProductTitle(String productId) {
        validateProductId(productId);
        
        try {
            WebElement productRow = getProductRowByProductId(productId);
            if (productRow == null) {
                throw new NoSuchElementException("Product with ID " + productId + " not found in cart");
            }
            
            WebElement titleElement = productRow.findElement(
                By.xpath(".//p[@class='product-name limit-lines']//a")
            );
            return titleElement.getText().trim();
        } catch (NoSuchElementException e) {
            throw new RuntimeException("Could not find title element for product " + productId, e);
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving product title for product " + productId, e);
        }
    }

    public String getProductPrice(String productId) {
        validateProductId(productId);
        
        try {
            WebElement productRow = getProductRowByProductId(productId);
            if (productRow == null) {
                throw new NoSuchElementException("Product with ID " + productId + " not found in cart");
            }
            
            List<WebElement> priceElements = productRow.findElements(
                By.xpath(".//div[@class='row'][contains(text(),'Rp')]")
            );
            if (priceElements.isEmpty()) {
                throw new NoSuchElementException("Price element not found for product " + productId);
            }
            
            String priceText = priceElements.get(0).getText().trim();
            // Extract the price
            if (priceText.contains("or")) {
                return priceText.split("or")[0].trim();
            }
            return priceText;
        } catch (NoSuchElementException e) {
            throw new RuntimeException("Could not find price element for product " + productId, e);
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving product price for product " + productId, e);
        }
    }

    public String getProductQuantity(String productId) {
        validateProductId(productId);
        
        try {
            WebElement productRow = getProductRowByProductId(productId);
            if (productRow == null) {
                throw new NoSuchElementException("Product with ID " + productId + " not found in cart");
            }
            
            WebElement quantityInput = productRow.findElement(
                By.xpath(".//input[@class='input-number text-center']")
            );
            return quantityInput.getAttribute("value");
        } catch (NoSuchElementException e) {
            throw new RuntimeException("Could not find quantity element for product " + productId, e);
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving product quantity for product " + productId, e);
        }
    }

    private WebElement getProductRowByProductId(String productId) {
        validateProductId(productId);
        
        List<WebElement> productRows = driver.findElements(cartProductRows);
        
        return productRows.stream()
            .filter(row -> matchesProductId(row, productId))
            .findFirst()
            .orElse(null);
    }

    private boolean matchesProductId(WebElement row, String productId) {
        try {
            WebElement productLink = row.findElement(By.xpath(".//p[@class='product-name limit-lines']//a"));
            String href = productLink.getAttribute("href");
            return productId.equals(UrlUtils.extractProductIdFromUrl(href));
        } catch (Exception e) {
            return false;
        }
    }

    private void validateProductId(String productId) {
        if (productId == null || productId.isEmpty()) {
            throw new IllegalArgumentException("Product ID cannot be null or empty");
        }
    }

    public String getSubTotal() {
        try {
            WebElement total = wait.until(
                ExpectedConditions.presenceOfElementLocated(subTotal)
            );
            return total.getText().trim();
        } catch (NoSuchElementException e) {
            throw new RuntimeException("Subtotal element not found on the page", e);
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving subtotal", e);
        }
    }

    public int getCartItemCount() {
        try {
            WebElement counter = wait.until(
                ExpectedConditions.presenceOfElementLocated(cartCounter)
            );
            return Integer.parseInt(counter.getAttribute("innerHTML").trim());
        } catch (NoSuchElementException e) {
            throw new RuntimeException("Cart item count element not found on the page", e);
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving cart item count", e);
        }
    }
}