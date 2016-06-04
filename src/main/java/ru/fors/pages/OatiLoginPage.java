package ru.fors.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class OatiLoginPage extends Page {
	


	public OatiLoginPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}


	By usernameField = By.name("username");
	By passwordField = By.name("password");
	By loginButton = By.name("submit");


	public OatiMainPage userLogin(String username, String password){
		type(usernameField, username);
		type(passwordField, password);
		click(loginButton);
		return new OatiMainPage(driver);
		
	}
}
