package ru.fors.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;

/**
 * Created by azhaleyko on 24.11.2015.
 */
public class OatiMainPage extends Page{

    public OatiMainPage(WebDriver driver) {
        super(driver);
    }
    By armTitle = By.xpath("//div[@class='name-system_title']/div[3]"); //наименование АРМ
    By listsLink = By.linkText("Списки"); //Меню Списки
    By raportsLink = By.linkText("Рапорты"); //Рапорты
    By prescriptionLink = By.linkText("Предписания"); //Предписания
    By recordsLink = By.linkText("Дела об АП"); //Дела об АП
    By decisionLink = By.linkText("Постановления"); //Постановления
    By reportsLink = By.linkText("Отчеты"); //Меню Отчеты
    By documentCntSummaryReport = By.linkText("Справки о количестве наработанных документов");//отчет Справки о количестве наработанных документов
    By monitoringLink = By.linkText("Мониторинг"); //Меню Мониторинг
    By yardCleanMonitoring = By.linkText("Мониторинг уборки дворов");
    By dutiesLink = By.linkText("Журнал учета поручений");
    By firstLineInResultsTable = By.xpath("//div[@class='result-find']//table/tbody/tr[1]"); //первая строка в таблице результатов поиска
    By pageTitle = By.cssSelector("h2"); //название списка/отчета
    By logoutButton=By.linkText("Выход");


    public String getArmTitle(){
        return getElementText(armTitle);
    }

    public void clickOnLink(By element, By element1){
        int count = 0;
        while (count < 5){
            try{
                click(element);
                waitForElementPresent(driver, element1, 10);
                click(element1);
            } catch (StaleElementReferenceException e){
                System.out.println("Trying to recover from a stale element");
                count = count+1;
            }catch (NoSuchElementException ex){
                System.out.println("Trying to recover from a no such element");
                count = count+1;
            }
            count=count+5;
        }
    }

    public void userGoToRaports(){
        clickOnLink(listsLink, raportsLink);
    }

    public void userGoToPrescription(){
        clickOnLink(listsLink, prescriptionLink);
    }

    public void userGoToRecords(){
        clickOnLink(listsLink, recordsLink);
    }

    public void userGoToDecisions(){
        clickOnLink(listsLink, decisionLink);
    }

    public void userGoToDocumentCntSummaryReport(){
        clickOnLink(reportsLink, documentCntSummaryReport);
    }

    public void userGoToYardCleanMonitoring(){
        clickOnLink(monitoringLink, yardCleanMonitoring);
    }

    public void userGoToDuties(){
        click(dutiesLink);
    }

    public String getPageTitle(){
        int count = 0;
        while (count < 5){
            try{
                getElementText(pageTitle);
            } catch (StaleElementReferenceException e){
                System.out.println("Trying to recover from a stale element");
                count = count+1;
            }
            count=count+5;
        }
        return getElementText(pageTitle);
    }

    public boolean resultsInTable(){
        return isElementPresent(driver, firstLineInResultsTable);
    }

    public OatiLoginPage userLogout(){
        click(logoutButton);
        return new OatiLoginPage(driver);
    }






}
