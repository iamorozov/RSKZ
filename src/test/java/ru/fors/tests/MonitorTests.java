package ru.fors.tests;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriverException;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import ru.fors.pages.MainPage;
import ru.fors.pages.LoginPage;

public class MonitorTests extends TestBase {

    @Test
    @Parameters({"username", "password"})
    public void monitorTests(String username, String password) {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.waitForLoginPageLoaded();
        MainPage mainPage = loginPage.userLogin(username, password);
        mainPage.waitUntilMainPageLoaded();
        mainPage.userClickInfopanelMenu();
        mainPage.userClickSearchScript();
        try {
            while (mainPage.isIncidentExist()) {
                try {
                    mainPage.getAndClickIncidentNumber();
                    mainPage.userChangeStatus();
                    mainPage.switchToParentFrame();
                    mainPage.userSaveIncident();
                    mainPage.userSetIncidentToInWork();
                } catch (TimeoutException te) {
                    mainPage.reloadPage();
                    mainPage.waitUntilMainPageLoaded();
                    mainPage.userClickInfopanelMenu();
                    mainPage.userClickSearchScript();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mainPage.userLogout();
        }
    }
}
