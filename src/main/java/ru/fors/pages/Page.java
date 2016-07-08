package ru.fors.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.fors.utils.PropertyLoader;

public class Page {

    protected WebDriver driver;
    protected WebDriverWait wait;

    public Page(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Long.parseLong(PropertyLoader.loadProperty("imp.wait")));
    }

    public void type(By element, String string) {
        driver.findElement(element).clear();
        driver.findElement(element).sendKeys(string);
    }

    public void click(By element) {
        waitUntilElementPresent(element);
        driver.findElement(element).click();
    }

    public String getElementText(By element) {
        waitUntilElementPresent(element);
        return driver.findElement(element).getText();
    }

    public void waitUntilElementPresent(By element) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(element));
        System.out.println(element + " is visible");
    }

    public boolean findIfElementVisible(By element) {
        return !driver.findElements(element).isEmpty() && driver.findElements(element).get(0).isDisplayed();
    }

    public void waitUntilFrameToBeAvaibleAndSwitchToIt(int element) {
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(element));
        System.out.println(element + " is visible and switch");
    }

    public void reloadPage() {
        driver.navigate().refresh();
    }

    private static ExpectedCondition<Boolean> waitElementValue(final By element, final String value) {
        return new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver webDriver) {
                return webDriver.findElement(element).getAttribute("value").contains(value);
            }
        };
    }

    public void waitUntilElementSetValue(By element, String value) {
        try {
            wait.until(waitElementValue(element, value));
            System.out.println(element + " has value: " + value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}

class LoginException extends Exception
{
    public static final String DEFAULT_MESSAGE = "Вход не выполнен. Ошибка.";

    public LoginException()
    {
        super(DEFAULT_MESSAGE);
    }
}
