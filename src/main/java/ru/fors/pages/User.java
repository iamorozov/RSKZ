package ru.fors.pages;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import ru.fors.utils.Browser;
import ru.fors.utils.PropertyLoader;
import ru.fors.utils.WebDriverFactory;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;

/**
 * Created by Morozov Ivan on 08.07.2016.
 * <p>
 * Class presents a User in "СУЭ"
 */
public class User implements Serializable {
    private String username;
    private String password;
    private String driverPath;
    private String representation;
    private String activity;
    private String solution;
    private boolean doChangeStatus;
    private boolean doChangeActivity;
    private boolean doChangeStatusToSolve;

    public User() {

    }

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

    public static void save(User object, String path) {
        try {
            XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(path)));
            encoder.writeObject(object);
            encoder.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static User decode(String path) throws FileNotFoundException {
        XMLDecoder decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream(path)));
        return (User) decoder.readObject();
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

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setDriverPath(String driverPath) {
        this.driverPath = driverPath;
    }

    public void setRepresentation(String representation) {
        this.representation = representation;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public boolean isDoChangeStatus() {
        return doChangeStatus;
    }

    public void setDoChangeStatus(boolean doChangeStatus) {
        this.doChangeStatus = doChangeStatus;
    }

    public boolean isDoChangeActivity() {
        return doChangeActivity;
    }

    public void setDoChangeActivity(boolean doChangeActivity) {
        this.doChangeActivity = doChangeActivity;
    }

    public boolean isDoChangeStatusToSolve() {
        return doChangeStatusToSolve;
    }

    public void setDoChangeStatusToSolve(boolean doChangeStatusToSolve) {
        this.doChangeStatusToSolve = doChangeStatusToSolve;
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
    private void changeStatusToSolve(MainPage mainPage) {
        mainPage.getAndClickIncidentNumber();
        mainPage.openFrame();
        mainPage.userChangeStatus();
        mainPage.switchToParentFrame();
        mainPage.userSaveIncident();
        mainPage.openFrame();
        mainPage.userSetIncidentSolutionType();
        mainPage.userTypeSolveText(solution);
        mainPage.setSolvedOnSecondLine();
        mainPage.userChangeStatusSolved();
        mainPage.switchToParentFrame();
        mainPage.userSaveActivityChange();
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
