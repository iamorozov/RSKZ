package ru.fors.tests;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import ru.fors.pages.MainPage;
import ru.fors.pages.LoginPage;


public class MonitorTests extends TestBase {
	
	
	
	@Test
	@Parameters({"username", "password"})
	public void monitorTests(String username, String password){
		LoginPage loginPage = new LoginPage(driver);
        loginPage.waitForLoginPageLoaded();
        MainPage mainPage = loginPage.userLogin(username, password);
        mainPage.waitUntilMainPageLoaded();
        mainPage.userClickInfopanelMenu();
        mainPage.userClickSearchScript();
        int i=0;
        while (i<10){
            System.out.println("Current iteration: "+i);
            mainPage.userChangeStatus();
            mainPage.switchToParentFrame();
            mainPage.userSaveIncident();
            mainPage.userSetIncidentToInWork();
            mainPage.getAndClickIncidentNumber();
            i++;
        }

        mainPage.userLogout();
	}
	

}
