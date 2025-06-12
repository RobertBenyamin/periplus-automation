package com.periplus.tests.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.List;

public class CartPage extends BasePage {
    
    private By cartProductRows = By.xpath("//div[@class='row row-cart-product']");
    private By subTotal = By.id("sub_total");
    
    public CartPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public String extractProductIdFromUrl(String productUrl) {
        try {
            if (productUrl.contains("/p/")) {
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

    public boolean isProductInCart(String productId) {
        try {
            WebElement productRow = getProductRowByProductId(productId);
            return productRow != null;
        } catch (Exception e) {
            return false;
        }
    }

    public String getProductTitle(String productId) {
        try {
            WebElement productRow = getProductRowByProductId(productId);
            if (productRow != null) {
                WebElement titleElement = productRow.findElement(
                    By.xpath(".//p[@class='product-name limit-lines']//a")
                );
                return titleElement.getText().trim();
            }
            return "";
        } catch (Exception e) {
            return "";
        }
    }

    public String getProductPrice(String productId) {
        try {
            WebElement productRow = getProductRowByProductId(productId);
            if (productRow != null) {
                List<WebElement> priceElements = productRow.findElements(
                    By.xpath(".//div[@class='row'][contains(text(),'Rp')]")
                );
                if (!priceElements.isEmpty()) {
                    String priceText = priceElements.get(0).getText().trim();
                    // Extract the price
                    if (priceText.contains("or")) {
                        return priceText.split("or")[0].trim();
                    }
                    return priceText;
                }
            }
            return "";
        } catch (Exception e) {
            return "";
        }
    }

    public String getProductQuantity(String productId) {
        try {
            WebElement productRow = getProductRowByProductId(productId);
            if (productRow != null) {
                WebElement quantityInput = productRow.findElement(
                    By.xpath(".//input[@class='input-number text-center']")
                );
                return quantityInput.getAttribute("value");
            }
            return "0";
        } catch (Exception e) {
            return "0";
        }
    }

    private WebElement getProductRowByProductId(String productId) {
        try {
            List<WebElement> productRows = driver.findElements(cartProductRows);
            
            for (WebElement row : productRows) {
                // Find the product link within this row
                List<WebElement> productLinks = row.findElements(
                    By.xpath(".//p[@class='product-name limit-lines']//a")
                );
                
                if (!productLinks.isEmpty()) {
                    String href = productLinks.get(0).getAttribute("href");
                    if (href != null) {
                        String rowProductId = extractProductIdFromUrl(href);
                        if (productId.equals(rowProductId)) {
                            return row;
                        }
                    }
                }
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }
    
    public String getSubTotal() {
        try {
            WebElement total = wait.until(
                ExpectedConditions.presenceOfElementLocated(subTotal)
            );
            return total.getText().trim();
        } catch (Exception e) {
            return "";
        }
    }
}