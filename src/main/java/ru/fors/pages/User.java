package ru.fors.pages;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import ru.fors.utils.Browser;
import ru.fors.utils.PropertyLoader;
import ru.fors.utils.WebDriverFactory;

/**
 * Created by Morozov Ivan on 08.07.2016.
 * <p>
 * Class presents a User
 */
public class User {

    private String username;
    private String password;
    private String driverPath;
    private String representation;

    public User(String username, String password, String driverPath, String representation) {
        this.username = username;
        this.password = password;
        this.driverPath = driverPath;
        this.representation = representation;
    }

    public void startWork() {

        String baseUrl = PropertyLoader.loadProperty("site.url");
        Browser browser = new Browser();
        browser.setName(PropertyLoader.loadProperty("browser.name"));
        WebDriver driver = new WebDriverFactory(driverPath)
                .getInstance(browser);
        driver.manage().window().maximize();
        driver.get(baseUrl);

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
