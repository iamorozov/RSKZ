package ru.fors.tests;

public class MonitorTests extends TestBase {

//    @Test
//    @Parameters({"username", "password"})
//    public void monitorTests(String username, String password) throws LoginException {
//        LoginPage loginPage = new LoginPage(driver);
//        loginPage.waitForLoginPageLoaded();
//        User user = new User(username, password, "", "", "");
//        MainPage mainPage = loginPage.userLogin(user);
//        mainPage.waitUntilMainPageLoaded();
//        mainPage.userClickInfopanelMenu();
//        mainPage.userClickSearchScript();
//        try {
//            while (mainPage.isIncidentExist()) {
//                try {
//                    mainPage.getAndClickIncidentNumber();
//                    mainPage.userChangeStatus();
//                    mainPage.switchToParentFrame();
//                    mainPage.userSaveIncident();
//                    mainPage.userSetIncidentToInWork();
//                } catch (TimeoutException te) {
//                    mainPage.reloadPage();
//                    mainPage.waitUntilMainPageLoaded();
//                    mainPage.userClickInfopanelMenu();
//                    mainPage.userClickSearchScript();
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            mainPage.userLogout();
//        }
//    }
}
