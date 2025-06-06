package com.periplus.tests;

import com.periplus.tests.pages.HomePage;
import com.periplus.tests.pages.LoginPage;
import com.periplus.tests.pages.ProductDetailPage;
import com.periplus.tests.pages.SearchResultsPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PeriplusCartTest extends BaseTest {
    
    private static final Logger logger = LoggerFactory.getLogger(PeriplusCartTest.class);
    
    private Properties config;
    private String baseUrl;
    private String testEmail;
    private String testPassword;
    private String searchKeyword;
    
    @BeforeClass
    public void loadConfiguration() {
        config = new Properties();
        try {
            config.load(new FileInputStream("src/test/resources/config.properties"));
            baseUrl = config.getProperty("base.url");
            testEmail = config.getProperty("test.email");
            testPassword = config.getProperty("test.password");
            searchKeyword = config.getProperty("search.keyword");
            logger.info("Configuration loaded successfully");
        } catch (IOException e) {
            logger.error("Failed to load configuration: {}", e.getMessage());
        }
    }
    
    @Test(priority = 1, description = "Test adding a product to cart with login")
    public void testAddProductToCartWithLogin() {
        logger.info("Starting test: Add Product to Cart");
        
        try {
            // Step 1: Navigate to website
            logger.info("Step 1: Navigating to {}", baseUrl);
            driver.get(baseUrl);
            Thread.sleep(2000);
            
            // Initialize page objects
            HomePage homePage = new HomePage(driver, wait);
            LoginPage loginPage = new LoginPage(driver, wait);
            SearchResultsPage searchPage = new SearchResultsPage(driver, wait);
            ProductDetailPage productDetailPage = new ProductDetailPage(driver, wait);
            
            // Step 2: Navigate to login page
            logger.info("Step 2: Navigating to login page");
            homePage.navigateToLoginPage();
            
            // Step 3: Login
            logger.info("Step 3: Logging in with test credentials");
            loginPage.login(testEmail, testPassword);
            Thread.sleep(2000);
            
            // Step 4: Search for product
            logger.info("Step 4: Searching for: {}", searchKeyword);
            homePage.searchProduct(searchKeyword);
            Thread.sleep(2000);
            
            // Get initial cart count
            int initialCartCount = homePage.getCartItemCount();
            logger.info("Initial cart count: {}", initialCartCount);
            
            // Step 5: Click on first product to go to detail page
            logger.info("Step 5: Clicking on first product");
            String productName = searchPage.getFirstProductName();
            logger.info("Product to view: {}", productName);
            searchPage.clickFirstProduct();
            Thread.sleep(2000);
            
            // Step 6: Add product to cart from detail page
            logger.info("Step 6: Adding product to cart from detail page");
            productDetailPage.addToCart();
            
            // Wait for cart to update
            Thread.sleep(3000);
            
            // Step 7: Navigate back to home to verify cart count
            logger.info("Step 7: Navigating back to verify cart count");
            driver.get(baseUrl);
            Thread.sleep(2000);
            
            // Step 8: Verify cart count increased
            logger.info("Step 8: Verifying product was added to cart");
            int newCartCount = homePage.getCartItemCount();
            logger.info("New cart count: {}", newCartCount);
            
            Assert.assertTrue(newCartCount > initialCartCount, 
                "Cart count should increase after adding product");
            
            logger.info("✓ Test PASSED: Product successfully added to cart!");
            
        } catch (Exception e) {
            logger.error("✗ Test FAILED: {}", e.getMessage(), e);
            Assert.fail("Test failed due to exception: " + e.getMessage());
        }
    }
}