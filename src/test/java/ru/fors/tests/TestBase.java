package ru.fors.tests;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;

import ru.fors.utils.Browser;
import ru.fors.utils.PropertyLoader;
import ru.fors.utils.WebDriverFactory;

public class TestBase {

    protected WebDriver driver;
    public String baseUrl;

    @BeforeTest
    public void init() {
        baseUrl = PropertyLoader.loadProperty("site.url");
        Browser browser = new Browser();
        browser.setName(PropertyLoader.loadProperty("browser.name"));
        driver = new WebDriverFactory("C:\\Users\\Morozov Ivan\\AppData\\Local\\Google\\Chrome\\Application\\chromedriver.exe")
                .getInstance(browser);
        driver.manage().window().maximize();
        driver.get(baseUrl);

    }

    public void takeScreenShot(String testName) throws IOException {
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(scrFile, new File("target/" + "screenshot_" + testName + ".png"));
    }


    @AfterMethod
    public void afterMethod(ITestResult result) throws IOException {
        String testName = result.getName();
        if (result.getStatus() == ITestResult.FAILURE) {
            takeScreenShot(testName);
        }
        driver.quit();
    }

}
