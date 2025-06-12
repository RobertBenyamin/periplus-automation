package com.periplus.tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import java.time.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaseTest {
    protected WebDriver driver;
    protected WebDriverWait wait;
    
    private static final Logger logger = LoggerFactory.getLogger(BaseTest.class);
    
    @BeforeMethod
    public void setUp() {
        try {
            // Setup ChromeDriver using WebDriverManager
            WebDriverManager.chromedriver().setup();
            
            // Configure Chrome options
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--start-maximized");
            options.addArguments("--disable-notifications");
            options.addArguments("--disable-popup-blocking");
            
            // Initialize driver
            driver = new ChromeDriver(options);
    
            // Configure timeouts
            driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
            driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(30));
            
            wait = new WebDriverWait(driver, Duration.ofSeconds(15));
            
            logger.info("WebDriver initialized successfully");
        } catch (Exception e) {
            logger.error("Failed to initialize WebDriver: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to initialize WebDriver", e);
        }
    }
    
    @AfterMethod
    public void tearDown() {
        try {
            if (driver != null) {
                driver.quit();
                logger.info("WebDriver closed successfully");
            }
        } catch (Exception e) {
            logger.error("Failed to close WebDriver: {}", e.getMessage(), e);
        } finally {
            driver = null;
        }
    }
}