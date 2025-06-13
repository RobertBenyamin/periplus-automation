package com.periplus.tests;

import com.periplus.tests.pages.HomePage;
import com.periplus.tests.pages.LoginPage;
import com.periplus.tests.pages.ProductDetailPage;
import com.periplus.tests.pages.SearchResultsPage;
import com.periplus.tests.config.ConfigManager;
import com.periplus.tests.pages.CartPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class PeriplusCartTest extends BaseTest {
    
    private static final Logger logger = LoggerFactory.getLogger(PeriplusCartTest.class);
    
    private ConfigManager configManager;
    private String baseUrl;
    private String testEmail;
    private String testPassword;
    private String searchKeyword;
    
    @BeforeClass
    public void loadConfiguration() {
        configManager = ConfigManager.getInstance();
        
        baseUrl = configManager.getBaseUrl();
        testEmail = configManager.getTestEmail();
        testPassword = configManager.getTestPassword();
        searchKeyword = configManager.getSearchKeyword();
    }

    @Test(priority = 1, description = "Test adding a product to cart with login")
    public void testAddProductToCartWithLogin() {
        logger.info("Starting test: Add Product to Cart");
        
        try {
            // Step 1: Navigate to website
            logger.info("Step 1: Navigating to {}", baseUrl);
            driver.get(baseUrl);
            
            // Initialize page objects
            HomePage homePage = new HomePage(driver, wait);
            homePage.waitForPageLoad();

            LoginPage loginPage = new LoginPage(driver, wait);
            SearchResultsPage searchPage = new SearchResultsPage(driver, wait);
            ProductDetailPage productDetailPage = new ProductDetailPage(driver, wait);
            CartPage cartPage = new CartPage(driver, wait);
            
            // Step 2: Navigate to login page
            logger.info("Step 2: Navigating to login page");
            homePage.navigateToLoginPage();
            loginPage.waitForPageLoad();
            
            // Step 3: Login
            logger.info("Step 3: Logging in with test credentials");
            loginPage.login(testEmail, testPassword);
            
            // Step 4: Search for product
            logger.info("Step 4: Searching for: {}", searchKeyword);
            homePage.searchProduct(searchKeyword);
            searchPage.waitForPageLoad();
            
            // Step 5: Click on first product to go to detail page
            logger.info("Step 5: Clicking on first product");
            searchPage.clickFirstProduct();
            productDetailPage.waitForPageLoad();
            
            // Step 6: Save product details from product detail page
            logger.info("Step 6: Saving product details from product detail page");
            String expectedProductId = productDetailPage.getProductId();
            String expectedProductTitle = productDetailPage.getProductTitle();
            String expectedProductPrice = productDetailPage.getProductPrice();
            
            logger.info("Product - ID: {}, Title: {}, Price: {}", 
                   expectedProductId, expectedProductTitle, expectedProductPrice);
            
            // Step 7: Add product to cart
            logger.info("Step 7: Adding product to cart");
            productDetailPage.addToCart();
            
            // Step 8: Navigate to cart page
            logger.info("Step 8: Navigating to cart page");
            driver.get(baseUrl + "/checkout/cart");
            cartPage.waitForPageLoad();
            
            // Step 9: Verify product exists in cart
            boolean productInCart = cartPage.isProductInCart(expectedProductId);
            String actualProductTitle = cartPage.getProductTitle(expectedProductId);
            String actualProductPrice = cartPage.getProductPrice(expectedProductId);
            String actualQuantity = cartPage.getProductQuantity(expectedProductId);
            String actualSubTotal = cartPage.getSubTotal();
            
            logger.info("Cart verification - Product found: {}, Title: {}, Price: {}, Quantity: {}, Subtotal: {}", 
                    productInCart, 
                    actualProductTitle,
                    actualProductPrice,
                    actualQuantity, 
                    actualSubTotal);
            
            // Assertions
            Assert.assertTrue(productInCart, "Product with ID " + expectedProductId + " should be present in cart");
            Assert.assertEquals(actualProductTitle, expectedProductTitle, "Product title mismatch");
            Assert.assertEquals(actualProductPrice, expectedProductPrice, "Product price mismatch");
            Assert.assertEquals(actualQuantity, "1", "Product quantity should be 1");
            Assert.assertEquals(actualSubTotal, expectedProductPrice, "Subtotal should match product price");
        
            logger.info("Test PASSED: Product successfully added to cart with correct details!");
            
        } catch (Exception e) {
            logger.error("Test FAILED: {}", e.getMessage(), e);
            Assert.fail("Test failed due to exception: " + e.getMessage());
        }
    }
}