package ru.fors.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LoginPage extends Page {

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    private By usernameField = By.id("LoginUsername");
    private By passwordField = By.id("LoginPassword");
    private By enterButton = By.id("loginBtn");

    public void waitForLoginPageLoaded() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(enterButton));
    }

    private void userTypeUsername(String username) {
        click(usernameField);
        type(usernameField, username);
    }

    private void userTypePassword(String password) {
        click(passwordField);
        type(passwordField, password);
    }

    private void userClickEnterButton() {
        click(enterButton);
    }

    public MainPage userLogin(String username, String password) throws LoginException {
        userTypeUsername(username);
        userTypePassword(password);
        userClickEnterButton();
        if (driver.findElements(usernameField).get(0).isDisplayed())
        {
            throw new LoginException();
        }
        else
            return new MainPage(driver);
    }


}
