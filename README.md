# 🛒 Periplus Test Automation Framework

A comprehensive **Selenium WebDriver** test automation framework for testing the Periplus online bookstore. This project demonstrates advanced test automation practices using the **Page Object Model (POM)** design pattern with **Java**, **TestNG**, and **Maven**.

[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://www.oracle.com/java/)
[![Selenium](https://img.shields.io/badge/Selenium-4.33.0-green.svg)](https://selenium.dev/)
[![TestNG](https://img.shields.io/badge/TestNG-7.8.0-blue.svg)](https://testng.org/)
[![Maven](https://img.shields.io/badge/Maven-Build_Tool-red.svg)](https://maven.apache.org/)

## 🎯 Project Overview

This automation framework tests critical e-commerce functionality on the **Periplus website** (https://www.periplus.com/), focusing on:

- **User Authentication** - Login/logout workflows
- **Product Search** - Search functionality and result validation
- **Shopping Cart** - Add to cart and cart management
- **End-to-End Workflows** - Complete user journeys

## 🛠️ Technology Stack

| Component             | Technology         | Version | Purpose                       |
| --------------------- | ------------------ | ------- | ----------------------------- |
| **Language**          | Java               | 21      | Core programming language     |
| **Automation**        | Selenium WebDriver | 4.33.0  | Web browser automation        |
| **Testing Framework** | TestNG             | 7.8.0   | Test execution and reporting  |
| **Build Tool**        | Maven              | 3.9.9   | Dependency management & build |
| **Driver Management** | WebDriverManager   | 5.9.2   | Automatic driver downloads    |
| **Logging**           | SLF4J              | 2.0.13  | Structured logging            |
| **Browser**           | Chrome             | 137     | Target browser for testing    |

## 📁 Project Structure

```
periplus-automation/
├── src/test/java/com/periplus/tests/
│   ├── BaseTest.java                 # Base test configuration
│   ├── PeriplusCartTest.java        # Main test scenarios
│   └── pages/                       # Page Object Model classes
│       ├── HomePage.java            # Home page interactions
│       ├── LoginPage.java           # Login page actions
│       ├── SearchResultsPage.java   # Search results handling
│       └── ProductDetailPage.java   # Product detail operations
├── src/test/resources/
│   ├── config.properties            # Test configuration (⚠️ Contains credentials)
│   └── testng.xml                   # TestNG suite configuration
├── target/                          # Build artifacts
│   ├── test-classes/               # Compiled test classes
│   └── surefire-reports/           # Test execution reports
├── .gitignore                       # Excludes config.properties for security
├── pom.xml                          # Maven dependencies
└── README.md                        # Project documentation
```

> **🔒 Security Note**: `config.properties` is excluded from version control via `.gitignore` to protect test credentials.

## ⚡ Quick Start

### Prerequisites

- **Java 21** or higher
- **Maven 3.6+**
- **Google Chrome** browser
- **Visual Studio Code** (recommended) or IntelliJ IDEA

### 🔧 Installation & Setup

1. **Clone the repository**

```bash
git clone <repository-url>
cd periplus-automation
```

2. **Install dependencies**

```bash
mvn clean install
```

3. **⚠️ IMPORTANT: Create Test Account**
   
   Before running tests, you **must** register a test account:
   
   - Navigate to https://www.periplus.com/
   - Click "Sign In" → "Create Account"
   - Register with a valid email address
   - Remember your credentials for the next step

4. **Configure test credentials** (**REQUIRED**)

```properties
# src/test/resources/config.properties
base.url=https://www.periplus.com/
test.email=your-test-email@example.com    # ⚠️ UPDATE THIS
test.password=your-secure-password        # ⚠️ UPDATE THIS  
search.keyword=Naruto
browser.timeout=15
```

> **🔐 Security Note**: The `config.properties` file is included in `.gitignore` to prevent accidentally committing sensitive credentials to version control.

5. **Run tests**

```bash
# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=PeriplusCartTest
```

> **💡 Important**: Ensure you have updated the test credentials in `config.properties` before running tests, otherwise authentication will fail.

## 🧪 Test Scenarios

### 🛍️ **E-commerce Cart Test Flow**

```java
@Test(description = "Test adding a product to cart with login")
public void testAddProductToCartWithLogin() {
    // 1. Navigate to Periplus website
    // 2. Login with test credentials
    // 3. Search for products (e.g., "Naruto")
    // 4. Select first product from results
    // 5. Add product to cart from detail page
    // 6. Verify cart count increased
}
```

## 📊 Test Reports

After test execution, find comprehensive reports at:

- **HTML Report**: `target/surefire-reports/index.html`
- **TestNG Report**: `target/surefire-reports/Periplus Test Suite/index.html`
- **Console Logs**: Real-time execution logging via SLF4J

## 🔧 Troubleshooting

### Common Issues & Solutions

**❌ Chrome version mismatch warnings**

```xml
<!-- Add to pom.xml for specific Chrome version support -->
<dependency>
    <groupId>org.seleniumhq.selenium</groupId>
    <artifactId>selenium-devtools-v137</artifactId>
    <version>4.33.0</version>
</dependency>
```

**❌ Element not found errors**

- Verify locators match actual website HTML
- Increase explicit wait timeouts if needed
- Check for dynamic content loading

**❌ Test execution failures**

```bash
# Clean and rebuild
mvn clean compile test-compile

# Refresh VS Code Java projects
# Command Palette > Java: Reload Projects
```

**❌ Authentication failures**

- Ensure you have registered a test account at https://www.periplus.com/
- Verify credentials in `config.properties` are correct
- Check if account is active and not locked
- Try logging in manually on the website first
