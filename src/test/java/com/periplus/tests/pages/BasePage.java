package com.periplus.tests.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;
    
    private By preloader = By.xpath("//div[@class='preloader']");
    
    public BasePage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }
    
    public void waitForPageLoad() {
        try {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(preloader));
        } catch (Exception e) {
            throw new RuntimeException("Error waiting for page to load: " + e.getMessage(), e);
        }
    }
}