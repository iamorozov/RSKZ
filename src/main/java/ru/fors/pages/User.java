package ru.fors.pages;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import ru.fors.utils.Browser;
import ru.fors.utils.PropertyLoader;
import ru.fors.utils.WebDriverFactory;

/**
 * Created by Morozov Ivan on 08.07.2016.
 * <p>
 * Class presents a User in "СУЭ"
 */
public class User {

    private String username;
    private String password;
    private String driverPath;
    private String representation;
    private String activity;
    private boolean doChangeStatus;
    private boolean doChangeActivity;

    public User(String username, String password, String driverPath, String representation) {
        this.username = username;
        this.password = password;
        this.driverPath = driverPath;
        this.representation = representation;
        doChangeStatus = true;
    }

    public User(String username, String password, String driverPath, String representation, String activity, boolean doChangeStatus) {
        this(username, password, driverPath, representation);
        this.doChangeStatus = doChangeStatus;
        this.activity = activity;
        this.doChangeActivity = true;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getDriverPath() {
        return driverPath;
    }

    public String getRepresentation() {
        return representation;
    }

    public String getActivity() {
        return activity;
    }

    /**
     * Start working for defined user
     * @throws LoginException is some logging problem occurred
     */
    public void startWork() throws LoginException {

        WebDriver driver = startBrowser();

        MainPage mainPage = loginAndGetMainPage(driver);
        mainPage.waitUntilMainPageLoaded();
        mainPage.userClickInfopanelMenu();
        mainPage.userClickSearchScript();
        try {
            manageAllIssues(mainPage);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mainPage.userLogout();
        }
    }

    /**
     * Make defined operations on all of the incidents in some representation
     * @param mainPage an instance of {@code MainPage} class
     */
    private void manageAllIssues(MainPage mainPage) {
        while (mainPage.isIncidentExist()) {
            try {
                if (doChangeStatus && doChangeActivity) {
                    changeStatusAndActivity(mainPage);
                } else if (doChangeStatus) {
                    changeStatus(mainPage);
                } else if (doChangeActivity) {
                    changeActivity(mainPage);
                }
            } catch (TimeoutException te) {
                te.printStackTrace();
                mainPage.reloadPage();
                mainPage.waitUntilMainPageLoaded();
                mainPage.userClickInfopanelMenu();
                mainPage.userClickSearchScript();
            }
        }
    }

    /**
     * Changes status of incident
     * @param mainPage an instance of {@code MainPage} class
     */
    private void changeStatus(MainPage mainPage) {
        mainPage.getAndClickIncidentNumber();
        mainPage.openFrame();
        mainPage.userChangeStatus();
        mainPage.switchToParentFrame();
        mainPage.userSaveIncident();
        mainPage.userSetIncidentToInWork();
    }

    /**
     * Changes both a status of incident and activity field
     * @param mainPage an instance of {@code MainPage} class
     */
    private void changeStatusAndActivity(MainPage mainPage) {
        mainPage.getAndClickIncidentNumber();
        mainPage.openFrame();
        mainPage.userChangeStatus();
        mainPage.changeActivity(activity);
        mainPage.switchToParentFrame();
        mainPage.userSaveIncident();
        mainPage.userSetIncidentToInWork();
    }

    /**
     * Rewrites a value in "Активность" field
     * @param mainPage an instance of {@code MainPage} class
     */
    private void changeActivity(MainPage mainPage) {
        mainPage.getAndClickIncidentNumber();
        mainPage.openFrame();
        mainPage.changeActivity(activity);
        mainPage.switchToParentFrame();
        mainPage.userSaveActivityChange();
    }

    /**
     * Makes authorization of user
     * @param driver - an instance of {@code WebDriver} class
     * @return {@code MainPage} instance
     * @throws LoginException if some error occured while logging
     */
    private MainPage loginAndGetMainPage(WebDriver driver) throws LoginException {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.waitForLoginPageLoaded();
        return loginPage.userLogin(this);
    }

    /**
     * Starts browser and open "СУЭ" page
     * @return an instance of {@code WebDriver} class
     */
    private WebDriver startBrowser() {
        String baseUrl = PropertyLoader.loadProperty("site.url");
        Browser browser = new Browser();
        browser.setName(PropertyLoader.loadProperty("browser.name"));
        WebDriver driver = new WebDriverFactory(driverPath)
                .getInstance(browser);
        driver.manage().window().maximize();
        driver.get(baseUrl);
        return driver;
    }
}
