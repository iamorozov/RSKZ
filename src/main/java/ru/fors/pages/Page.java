package ru.fors.pages;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import ru.fors.utils.PropertyLoader;

public class Page {

	protected WebDriver driver;

	public Page(WebDriver driver) {
		this.driver = driver;
	}
	
	public void type(By element, String string){
		driver.findElement(element).clear();
		driver.findElement(element).sendKeys(string);
	}
	
	public void click(By element){
		driver.findElement(element).click();
	}
	
	public String getElementText(By element){
		return driver.findElement(element).getText();
	}
	
	public String getUrl(){
		return driver.getCurrentUrl();
	}
	
	public static Boolean isElementPresent(WebDriver driver, By element) {
		try {
			driver.findElement(element);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}

	}
	
	public static Object waitForElementPresent(WebDriver driver, By locator, int timeout) {
		try {
			driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);

			new WebDriverWait(driver, timeout) {
			}.until(ExpectedConditions.presenceOfElementLocated(locator));

			driver.manage().timeouts().implicitlyWait(Long.parseLong(PropertyLoader.loadProperty("imp.wait")), TimeUnit.SECONDS);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	

}
