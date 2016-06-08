package ru.fors.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Created by Alexander Zhaleiko on 04.06.2016.
 */
public class MainPage extends Page{
    public MainPage(WebDriver driver) {
        super(driver);
    }

    private By infopanelMenu = By.xpath("//span[text()='Избранные объекты и инфопанели']");
    private By searchScript = By.xpath("//span[text()='______тестирование скрипта']");
    private By saveButton = By.xpath("//button[text()='Сохранить']");
    private By comboUpButton = By.id("X3Button");
    private By inWorkStatus = By.xpath("//div[text()='В работе']");
    private By inWaitButton = By.xpath("//button[text()='В ожидание']");
    private By returnBackButton = By.xpath("//button[text()='Возврат оператору']");
    private By statusField = By.id("X3");
    private By logoutButton = By.linkText("Выход");
    private By loginAgainLink = By.id("loginAgain");
    private By moreIconButton = By.cssSelector("button[class*='more-icon']");
    private By inWaitButton2 = By.xpath("//span[text()='В ожидание']");
    private By inWaitReasonSelectButton = By.id("X6FillButton");
    private By incidentDiagnosticLink = By.linkText("диагностика инцидента");
    private By commentField = By.id("X11");
    private By saveIncidentButton = By.xpath("//button[text()='Готово']");
    private By firsRowInTable = By.xpath("//tbody[@role='presentation']//td[2]/div");

    public void waitUntilMainPageLoaded(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(infopanelMenu));
    }
    public void userClickInfopanelMenu(){
        click(infopanelMenu);
        waitUntilElementPresent(searchScript);
    }

    public void userClickSearchScript(){
        click(searchScript);
        waitUntilFrameToBeAvaibleAndSwitchToIt(1);
        waitUntilElementPresent(saveButton);
    }

    public void switchToParentFrame(){
        driver.switchTo().defaultContent();
    }

    private void userClickSaveButton(){
        click(saveButton);
    }

    private void userClickLogoutButton(){
        switchToParentFrame();
        click(logoutButton);
    }

    public void userLogout(){
        userClickLogoutButton();
        wait.until(ExpectedConditions.alertIsPresent());
        driver.switchTo().alert().accept();
        waitUntilElementPresent(loginAgainLink);
    }
    private void userClickComboUpButton(){
        click(comboUpButton);
    }

    private void userClickInWorkStatus(){
        click(inWorkStatus);
    }

    public void userChangeStatus(){
        waitUntilFrameToBeAvaibleAndSwitchToIt(1);
        userClickComboUpButton();
        userClickInWorkStatus();
        //waitUntilElementSetValue(statusField, "В работе");
    }

    public void userSaveIncident(){
        waitUntilFrameToBeAvaibleAndSwitchToIt(1);
        userClickSaveButton();
        waitUntilElementPresent(returnBackButton);
    }

    private void userClickMoreIconButton(){
        click(moreIconButton);
    }

    private void userClickInWaitButton2(){
        click(inWaitButton2);
    }

    public void userSetIncidentToInWork(){
        userClickMoreIconButton();
        userClickInWaitButton2();
        userClickInWaitReasonSelectButton();
        userClickIncidentDiagnosticLink();
        userTypeIncidentComment();
        userClickSaveIncidentButton();
    }

    private void userClickInWaitReasonSelectButton(){
        click(inWaitReasonSelectButton);
    }

    private void userClickIncidentDiagnosticLink(){
        click(incidentDiagnosticLink);
        waitUntilElementPresent(commentField);
    }

    private void userTypeIncidentComment(){
        type(commentField, "диагностика инцидента");
    }

    private void userClickSaveIncidentButton(){
        click(saveIncidentButton);
        waitUntilElementPresent(saveButton);
    }

    public void getAndClickIncidentNumber(){
        waitUntilFrameToBeAvaibleAndSwitchToIt(0);
        System.out.println("Next incident: "+getElementText(firsRowInTable));
        click(firsRowInTable);
        switchToParentFrame();
        waitUntilFrameToBeAvaibleAndSwitchToIt(1);
    }
}
