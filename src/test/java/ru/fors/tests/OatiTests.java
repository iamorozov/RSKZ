package ru.fors.tests;

import static org.testng.AssertJUnit.*;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import ru.fors.pages.OatiLoginPage;
import ru.fors.pages.MainPage;
import ru.fors.pages.OatiMainPage;


public class OatiTests extends TestBase {
	
	
	
	@Test
	@Parameters({"username", "password"})
	public void oatiPageTests(String username, String password){
		MainPage mainPage = new MainPage(driver);
		OatiLoginPage oatiLoginPage = mainPage.userGoToOatiInsp();
        OatiMainPage oatiMainPage = oatiLoginPage.userLogin(username, password);
		assertTrue("АРМ Инспектор не открылся", oatiMainPage.getArmTitle().contains("АРМ «Инспектор»"));
		oatiMainPage.userGoToRaports();
        assertTrue("Страница список рапортов не открылась", oatiMainPage.getPageTitle().contains("Список рапортов"));
        assertTrue("Не отображается список рапортов", oatiMainPage.resultsInTable());
        oatiMainPage.userGoToPrescription();
        assertTrue("Страница список предписаний не открылась", oatiMainPage.getPageTitle().contains("Список предписаний"));
        assertTrue("Не отображается список предписаний", oatiMainPage.resultsInTable());
        oatiMainPage.userGoToRecords();
        assertTrue("Страница список дел об АП не открылась", oatiMainPage.getPageTitle().contains("Список дел об АП"));
        //assertTrue("Не отображается список дел об АП", oatiMainPage.resultsInTable());
        oatiMainPage.userGoToDecisions();
        assertTrue("Страница список постановлений не открылась", oatiMainPage.getPageTitle().contains("Список постановлений"));
        assertTrue("Не отображается список постановлений", oatiMainPage.resultsInTable());
        oatiMainPage.userGoToDocumentCntSummaryReport();
        assertTrue("Отчет Справка о наработанных документах не открылся", oatiMainPage.getPageTitle().contains("Справка о наработанных документах"));
        oatiMainPage.userGoToYardCleanMonitoring();
        assertTrue("Страница Мониторинг уборки дворов не открылась", oatiMainPage.getPageTitle().contains("Мониторинг уборки дворов"));
        oatiMainPage.userGoToDuties();
        assertTrue("Страница Журнал учета поручений", oatiMainPage.getPageTitle().contains("Журнал учета поручений"));
        assertTrue("Не отображается список поручений", oatiMainPage.resultsInTable());
        oatiMainPage.userLogout();
	}
	

}
