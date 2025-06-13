package com.periplus.tests.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.util.Properties;

public class ConfigManager {
    private static final Logger logger = LoggerFactory.getLogger(ConfigManager.class);
    private static ConfigManager instance;
    private Properties config;
    
    private ConfigManager() {
        loadConfiguration();
    }
    
    public static ConfigManager getInstance() {
        if (instance == null) {
            instance = new ConfigManager();
        }
        return instance;
    }
    
    private void loadConfiguration() {
        config = new Properties();

        try (var inputStream = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            if (inputStream == null) {
                logger.error("Config file not found in classpath");
                throw new RuntimeException("Failed to find config.properties file in classpath");
            }
            config.load(inputStream);
            logger.info("Configuration loaded successfully");
        } catch (IOException e) {
            logger.error("Error reading config file: {}", e.getMessage());
            throw new RuntimeException("Failed to load configuration from config.properties", e);
        }
    }
    
    public String getRequiredProperty(String key) {
        String value = config.getProperty(key);
        if (value == null || value.isEmpty()) {
            throw new RuntimeException(key + " property is missing or empty in config.properties");
        }
        return value;
    }
    
    public String getBaseUrl() {
        return getRequiredProperty("base.url");
    }
    
    public String getTestEmail() {
        return getRequiredProperty("test.email");
    }
    
    public String getTestPassword() {
        return getRequiredProperty("test.password");
    }
    
    public String getSearchKeyword() {
        return getRequiredProperty("search.keyword");
    }
}