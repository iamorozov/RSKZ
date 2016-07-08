package ru.fors.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

public class WebDriverFactory {

    public static final String CHROME = "chrome";
    public static final String FIREFOX = "firefox";
    public static final String INTERNET_EXPLORER = "ie";

    private String driverPath;

    public WebDriverFactory(String driverPath) {
        this.driverPath = driverPath;
    }

    public WebDriver getInstance(Browser browser) {

        WebDriver webDriver = null;
        String browserName = browser.getName();

        if (CHROME.equals(browserName)) {
            System.setProperty("webdriver.chrome.driver", driverPath);
            webDriver = new ChromeDriver();

        } else if (FIREFOX.equals(browserName)) {
            webDriver = new FirefoxDriver();

        } else if (INTERNET_EXPLORER.equals(browserName)) {
            webDriver = new InternetExplorerDriver();

        }
        return webDriver;
    }

}
