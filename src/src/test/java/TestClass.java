import static org.junit.Assert.*;
import static org.openqa.selenium.support.ui.ExpectedConditions.stalenessOf;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class TestClass extends BaseClass {
    @Test
    public void Test() throws InterruptedException {
        //Переход на стартовую страницу
        driver.get("https://www.tinkoff.ru/");
        //Инициализация стартовой страницы
        HomePage homePage = new HomePage(driver, wait);
        //Проверка, что ссылка "Платежи" отображается
        assertTrue(homePage.isElementPresent(homePage.paymentsLocator));
        //Переход на страницу платежи
        PaymentsPage paymentsPage = homePage.click();
        //Проверка, что кнопка ЖКХ отображается
        assertTrue(paymentsPage.isElementPresent(paymentsPage.communalPaymentsLocator));
        //Переход на странциу поставщиков услуг
        ServiceProviders serviceProviders = paymentsPage.clickPaymentsZKH();
        //Проверка, что список выбора регионов отображается
        assertTrue(serviceProviders.isElementPresent(serviceProviders.regionLocator));
        //Если регион не Москва
        if (!serviceProviders.getTextRegion().contains("Москв")) {
            // Ожидание прогрузки страницы (ждем когда пропадет кнопка из Dom)
            wait.until(stalenessOf(driver.findElement(paymentsPage.communalPaymentsLocator)));
            // Открытие списка регионов
            ListOfRegions listOfRegions = serviceProviders.openListOfRegions();
            listOfRegions.isElementPresent(listOfRegions.moscowLocator);
            //Выбор региона Москва
            listOfRegions.selectRegion(listOfRegions.moscowLocator);
        }
        // Проверка, что поставщик ЖКУ-Москва отображается
        serviceProviders.isElementPresent(serviceProviders.mosZKH);
        // Переход на страницу поставщика
        PaymentsZKH paymentsZKH = serviceProviders.openMosZKH();
        // Получаем название поставщика ЖКУ-Москва
        String zKH = serviceProviders.getTextMosZKH();
        // Выбор формы оплаты ЖКХ в Москве
        wait.until(stalenessOf(driver.findElement(serviceProviders.mosZKH)));
        paymentsZKH.openPayZKHinMoscow();
        wait.until(stalenessOf(driver.findElement(paymentsZKH.payZKHinMoscowLocator)));


        //Проверка обязательности заполнения поля "Код плательщика за ЖКУ в Москве"
        paymentsZKH.sendKey("122015", paymentsZKH.paymentPeriodLocator);
        paymentsZKH.sendKey("100", paymentsZKH.voluntaryInsuranceLocator);
        paymentsZKH.sendKey("10000", paymentsZKH.amountOfPaymentLocator);
        paymentsZKH.enter(paymentsZKH.amountOfPaymentLocator);
        assertTrue(paymentsZKH.isElementPresent(paymentsZKH.errorMessageLocator1));


        //Проверка обязательности заполнения поля период оплаты
        paymentsZKH.sendKey("7862341641", paymentsZKH.payerCodeLocator);
        paymentsZKH.clear(paymentsZKH.paymentPeriodLocator);
        paymentsZKH.enter(paymentsZKH.paymentPeriodLocator);
        assertTrue(paymentsZKH.isElementPresent(paymentsZKH.errorMessageLocator1));


        //Проверка обязательности заполнения поля сумма платежа
        paymentsZKH.sendKey("102010", paymentsZKH.paymentPeriodLocator);
        paymentsZKH.clear(paymentsZKH.amountOfPaymentLocator);
        paymentsZKH.enter(paymentsZKH.amountOfPaymentLocator);
        assertTrue(paymentsZKH.isElementPresent(paymentsZKH.errorMessageLocator1));


        //Проверка на невалидные значения код плательщика
        paymentsZKH.sendKey("5000", paymentsZKH.amountOfPaymentLocator);
        paymentsZKH.clear(paymentsZKH.voluntaryInsuranceLocator);
        checkFields("564784367", paymentsZKH,paymentsZKH.payerCodeLocator,paymentsZKH.errorMessageLocator1, "code");
        checkFields("0", paymentsZKH,paymentsZKH.payerCodeLocator,paymentsZKH.errorMessageLocator1, "code");
        checkFields("4367", paymentsZKH,paymentsZKH.payerCodeLocator,paymentsZKH.errorMessageLocator1, "code");
        paymentsZKH.sendKey("7654932456", paymentsZKH.payerCodeLocator);


        //Проверка на невалидные значения период оплаты коммунальных услуг
        checkFields("000000", paymentsZKH, paymentsZKH.paymentPeriodLocator, paymentsZKH.errorMessageLocator1,"period");
        checkFields("000001", paymentsZKH, paymentsZKH.paymentPeriodLocator, paymentsZKH.errorMessageLocator1, "period");
        checkFields("132015",paymentsZKH,paymentsZKH.paymentPeriodLocator, paymentsZKH.errorMessageLocator1, "period");
        checkFields("12201", paymentsZKH, paymentsZKH.paymentPeriodLocator, paymentsZKH.errorMessageLocator1, "period");
        paymentsZKH.sendKey("022018",paymentsZKH.paymentPeriodLocator);


        //Проверка на невалидные значения сумма платежа
        checkFields("0",paymentsZKH,paymentsZKH.amountOfPaymentLocator,paymentsZKH.errorMessageLocator1,"sum");
        checkFields("9",paymentsZKH,paymentsZKH.amountOfPaymentLocator,paymentsZKH.errorMessageLocator1,"sum");
        checkFields("9.99",paymentsZKH,paymentsZKH.amountOfPaymentLocator,paymentsZKH.errorMessageLocator1,"sum");
        checkFields("15000.99",paymentsZKH,paymentsZKH.amountOfPaymentLocator,paymentsZKH.errorMessageLocator1,"sum");
        checkFields("15001",paymentsZKH,paymentsZKH.amountOfPaymentLocator,paymentsZKH.errorMessageLocator1,"sum");
        checkFields("100000",paymentsZKH,paymentsZKH.amountOfPaymentLocator,paymentsZKH.errorMessageLocator1,"sum");
        checkFields(")(*()",paymentsZKH,paymentsZKH.amountOfPaymentLocator,paymentsZKH.errorMessageLocator2,"sum");

        PageObject pageObject = new PageObject(driver,wait);
        pageObject.click(pageObject.headerPayments);
        paymentsPage.sendKey(zKH,paymentsPage.quickSearch);
        List<WebElement> elementList = paymentsPage.getElements(paymentsPage.listOfQuickSearch);
        assertEquals(elementList.get(0).getText(),zKH);
        elementList.get(0).click();
        assertTrue(paymentsZKH.isElementPresent(paymentsZKH.payZKHinMoscowLocator));
        pageObject.click(pageObject.headerPayments);
        assertTrue(paymentsPage.isElementPresent(paymentsPage.communalPaymentsLocator));
        paymentsPage.clickPaymentsZKH();
        wait.until(stalenessOf(driver.findElement(paymentsPage.communalPaymentsLocator)));
        // Открытие списка регионов
        ListOfRegions listOfRegions = serviceProviders.openListOfRegions();
        listOfRegions.isElementPresent(listOfRegions.moscowLocator);
        //Выбор региона Санкт - Петербург
        listOfRegions.selectRegion(listOfRegions.sPBLocator);
        wait.until(stalenessOf(driver.findElement(listOfRegions.sPBLocator)));
        for (WebElement webElement:serviceProviders.getListOfProviders(serviceProviders.listOfProviders)) {
            if (webElement.getText().equals(zKH)){
                assertTrue(false);
            } else {
                assertTrue(true);
            }
        }
    }

    public void checkFields(String str, PageObject pageObject, By locator, By locatorError, String id) throws InterruptedException {
        pageObject.clear(locator);
        pageObject.sendKey(str, locator);
        pageObject.enter(locator);
        Thread.sleep(300);
        if (id.equals("code")){
            assertEquals(pageObject.getText(locatorError),"Поле неправильно заполнено");
        } else if (id.equals("period")){
            assertEquals(pageObject.getText(locatorError),"Поле заполнено некорректно");
        } else if (id.equals("sum")){
            try{
                double sum = Double.parseDouble(str);
                if (sum <= 9.99){
                    assertEquals(pageObject.getText(locatorError),"Минимальная сумма перевода — 10 \u20BD");
                } else {
                    assertEquals(pageObject.getText(locatorError),"Максимальная сумма перевода — 15 000 \u20BD");
                }
            } catch(Exception e){
                assertEquals(pageObject.getText(locatorError),"Поле заполнено неверно");
            }

        }
    }
}
