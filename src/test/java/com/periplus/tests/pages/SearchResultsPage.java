package com.periplus.tests.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.NoSuchElementException;
import java.util.List;

public class SearchResultsPage extends BasePage {
    private By productItems = By.xpath("//div[@class='single-product']");
    private By productLink = By.xpath("//div[@class='product-content product-contents']//h3//a");

    public SearchResultsPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }
    
    public void clickFirstProduct() {
        try {
            // Wait for products to load
            wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(productItems));
            
            // Find and click first product link
            List<WebElement> productLinks = driver.findElements(productLink);
            if (productLinks.isEmpty()) {
                throw new NoSuchElementException("No products found on search results page");
            }
            
            WebElement firstProductLink = productLinks.get(0);
            wait.until(ExpectedConditions.elementToBeClickable(firstProductLink));
            firstProductLink.click();
        } catch (NoSuchElementException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to click on first product: " + e.getMessage(), e);
        }
    }
}