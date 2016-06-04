package ru.fors.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class MainPage extends Page {

	public MainPage(WebDriver driver) {
		super(driver);
	}

	By oatiInspLink = By.xpath("//span[text()='Учет нарушений']");
	By ordersOatiLink = By.xpath("//span[text()='Учет заявок на выдачу ордеров на производство земляных работ, аварийных разрытий']");
	By analyticsLink = By.xpath("//span[text()='Аналитическая система']");
	By sgtnLink = By.xpath("//span[text()='СГТН']");
	
	public OatiLoginPage userGoToOatiInsp(){
		click(oatiInspLink);
		return new OatiLoginPage(driver);
	}

}
