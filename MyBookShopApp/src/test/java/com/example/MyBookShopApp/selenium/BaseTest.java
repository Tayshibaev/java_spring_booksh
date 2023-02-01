package com.example.MyBookShopApp.selenium;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.TestPropertySource;

import java.util.concurrent.TimeUnit;

@TestPropertySource("/application-test.properties")
public class BaseTest {

    protected static WebDriver driver;

    @Value("${webdriver.path}")
    private String pathDriver;

    @BeforeEach
    protected void beforeMethod() {
        System.setProperty("webdriver.chrome.driver", pathDriver);
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10L, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }

    @AfterEach
    protected void afterMethod() {
        driver.quit();
    }

    public static WebDriver getDriver() {
        return driver;
    }


}
