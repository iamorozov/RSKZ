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
    private String activity;
    private String solution;
    private boolean doChangeStatus;
    private boolean doChangeActivity;
    private boolean doChangeStatusToSolve;

    public User(String username, String password, String driverPath, String representation) {
        this.username = username;
        this.password = password;
        this.driverPath = driverPath;
        this.representation = representation;
        doChangeStatus = true;
    }

    public User(String username, String password, String driverPath, String representation, String solution) {
        this.username = username;
        this.password = password;
        this.driverPath = driverPath;
        this.representation = representation;
        this.solution = solution;
        doChangeStatusToSolve = true;
    }

    public User(String username, String password, String driverPath, String representation, String activity, boolean doChangeStatus) {
        this(username, password, driverPath, representation);
        this.doChangeStatus = doChangeStatus;
        this.solution = activity; // for test only // TODO return activity
        this.doChangeStatusToSolve = true; // for test only //Todo return doChangeActivity
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

    private void manageAllIssues(MainPage mainPage) {
        while (mainPage.isIncidentExist()) {
            try {
                if (doChangeStatus && doChangeActivity) {
                    changeStatusAndActivity(mainPage);
                } else if (doChangeStatus) {
                    changeStatus(mainPage);
                } else if (doChangeActivity) {
                    changeActivity(mainPage);
                } else if (doChangeStatusToSolve) {
                    changeStatusToSolve(mainPage);
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

    private void changeStatus(MainPage mainPage) {
        mainPage.getAndClickIncidentNumber();
        mainPage.openFrame();
        mainPage.userChangeStatus();
        mainPage.switchToParentFrame();
        mainPage.userSaveIncident();
        mainPage.userSetIncidentToInWork();
    }

    private void changeStatusToSolve(MainPage mainPage) {
        mainPage.getAndClickIncidentNumber();
        mainPage.openFrame();
        mainPage.userChangeStatus();
        mainPage.switchToParentFrame();
        mainPage.userSaveIncident();
        mainPage.openFrame();
        mainPage.userChangeStatusSolved();
        mainPage.switchToParentFrame();
        mainPage.openFrame();
        mainPage.userSetIncidentSolved();
        mainPage.userTypeSolveText(solution);
        mainPage.setSolvedOnSecondLine();
        mainPage.switchToParentFrame();
        mainPage.userSaveIncident();
    }

    private void changeStatusAndActivity(MainPage mainPage) {
        mainPage.getAndClickIncidentNumber();
        mainPage.openFrame();
        mainPage.userChangeStatus();
        mainPage.changeActivity(activity);
        mainPage.switchToParentFrame();
        mainPage.userSaveIncident();
        mainPage.userSetIncidentToInWork();
    }

    private void changeActivity(MainPage mainPage) {
        mainPage.getAndClickIncidentNumber();
        mainPage.openFrame();
        mainPage.changeActivity(activity);
        mainPage.switchToParentFrame();
        mainPage.userSaveActivityChange();
    }

    private MainPage loginAndGetMainPage(WebDriver driver) throws LoginException {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.waitForLoginPageLoaded();
        return loginPage.userLogin(this);
    }

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
