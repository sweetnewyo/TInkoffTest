package tests;

import static org.junit.Assert.*;
import static org.openqa.selenium.support.ui.ExpectedConditions.stalenessOf;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import ru.tinkoff.web.driver.WebDriverManager;
import ru.tinkoff.web.pages.*;

import java.util.List;

/**
 * Тестовый класс с набором шагов.
 */
public class TestClass extends WebDriverManager {
    @Test
    public void Test() throws InterruptedException {

        /*
        Шаг 1
         */
        //Переход на стартовую страницу
        driver.get("https://www.tinkoff.ru/");
        //Инициализация стартовой страницы
        HomePage homePage = new HomePage(driver, wait);
        //Проверка, что ссылка "Платежи" отображается
        assertTrue(homePage.isElementPresent(homePage.getPaymentsLocator()));
        /*
        Шаг 2
         */
        //Переход на страницу платежи
        PaymentsPage paymentsPage = homePage.click();
        /*
        Шаг 3
         */
        //Проверка, что кнопка ЖКХ отображается
        assertTrue(paymentsPage.isElementPresent(paymentsPage.getCommunalPaymentsLocator()));
        //Переход на странциу поставщиков услуг
        ServiceProvidersPage serviceProviders = paymentsPage.click();
        /*
        Шаг 4
         */
        //Проверка, что список выбора регионов отображается
        assertTrue(serviceProviders.isElementPresent(serviceProviders.getRegionLocator()));
        //Если регион не Москва
        if (!serviceProviders.getTextRegion().contains("Москв")) {
            // Ожидание прогрузки страницы (ждем когда пропадет кнопка из Dom)
            wait.until(stalenessOf(driver.findElement(paymentsPage.getCommunalPaymentsLocator())));
            // Открытие списка регионов
            RegionsPage listOfRegions = serviceProviders.openListOfRegions();
            listOfRegions.isElementPresent(listOfRegions.getMoscowLocator());
            //Выбор региона Москва
            listOfRegions.selectRegion(listOfRegions.getMoscowLocator());
        }
        /*
        Шаг 5
         */
        // Проверка, что поставщик ЖКУ-Москва отображается
        serviceProviders.isElementPresent(serviceProviders.getMosZKH());
        // Переход на страницу поставщика
        PaymentsZKHPage paymentsZKH = serviceProviders.openMosZKH();
        // Получаем название поставщика ЖКУ-Москва
        String zKH = serviceProviders.getTextMosZKH();
        /*
        Шаг 6
         */
        // Выбор формы оплаты ЖКХ в Москве
        wait.until(stalenessOf(driver.findElement(serviceProviders.getMosZKH())));
        paymentsZKH.openPayZKHinMoscow();
        wait.until(stalenessOf(driver.findElement(paymentsZKH.getPayZKHinMoscowLocator())));


        /*
        Шаг 7
         */
        //Проверка обязательности заполнения поля "Код плательщика за ЖКУ в Москве"
        paymentsZKH.sendKey("122015", paymentsZKH.getPaymentPeriodLocator());
        paymentsZKH.sendKey("100", paymentsZKH.getVoluntaryInsuranceLocator());
        paymentsZKH.sendKey("10000", paymentsZKH.getAmountOfPaymentLocator());
        paymentsZKH.enter(paymentsZKH.getAmountOfPaymentLocator());
        assertTrue(paymentsZKH.isElementPresent(paymentsZKH.getErrorMessageLocator1()));


        //Проверка обязательности заполнения поля период оплаты
        paymentsZKH.sendKey("7862341641", paymentsZKH.getPayerCodeLocator());
        paymentsZKH.clear(paymentsZKH.getPaymentPeriodLocator());
        paymentsZKH.enter(paymentsZKH.getPaymentPeriodLocator());
        assertTrue(paymentsZKH.isElementPresent(paymentsZKH.getErrorMessageLocator1()));


        //Проверка обязательности заполнения поля сумма платежа
        paymentsZKH.sendKey("102010", paymentsZKH.getPaymentPeriodLocator());
        paymentsZKH.clear(paymentsZKH.getAmountOfPaymentLocator());
        paymentsZKH.enter(paymentsZKH.getAmountOfPaymentLocator());
        assertTrue(paymentsZKH.isElementPresent(paymentsZKH.getErrorMessageLocator1()));



        //Проверка на невалидные значения код плательщика
        paymentsZKH.sendKey("5000", paymentsZKH.getAmountOfPaymentLocator());
        paymentsZKH.clear(paymentsZKH.getVoluntaryInsuranceLocator());
        //Проверка на не полностью заполненный код если не хватает 1 цифры
        checkFields("564784367", paymentsZKH,paymentsZKH.getPayerCodeLocator(),paymentsZKH.getErrorMessageLocator1(), "code");
        // Проверка на 0
        checkFields("0", paymentsZKH,paymentsZKH.getPayerCodeLocator(),paymentsZKH.getErrorMessageLocator1(), "code");
        // Проврка на не полностью заполненный код если не хватает более 1 цифры
        checkFields("4367", paymentsZKH,paymentsZKH.getPayerCodeLocator(),paymentsZKH.getErrorMessageLocator1(), "code");
        // Заполнение валидным кодом
        paymentsZKH.sendKey("7654932456", paymentsZKH.getPayerCodeLocator());


        //Проверка на невалидные значения период оплаты коммунальных услуг
        // Проверка если все значения равны 0
        checkFields("000000", paymentsZKH, paymentsZKH.getPaymentPeriodLocator(), paymentsZKH.getErrorMessageLocator1(),"period");
        // Прворека если месяц равен 00, если год равен 0000 , система предупреждения не выдает, при корректном месяце.
        checkFields("000001", paymentsZKH, paymentsZKH.getPaymentPeriodLocator(), paymentsZKH.getErrorMessageLocator1(), "period");
        // Проверка граничного значения если месяц > 12
        checkFields("132015",paymentsZKH,paymentsZKH.getPaymentPeriodLocator(), paymentsZKH.getErrorMessageLocator1(), "period");
        // Проверка на неполностью заполненное поле
        checkFields("12201", paymentsZKH, paymentsZKH.getPaymentPeriodLocator(), paymentsZKH.getErrorMessageLocator1(), "period");
        // Ввод влидного значения
        paymentsZKH.sendKey("022018",paymentsZKH.getPaymentPeriodLocator());


        //Проверка на невалидные значения сумма платежа
        // Проверка на 0
        checkFields("0",paymentsZKH,paymentsZKH.getAmountOfPaymentLocator(),paymentsZKH.getErrorMessageLocator1(),"sum");
        // Проверка граничного значения для Integer, сумма не может быть < 10
        checkFields("9",paymentsZKH,paymentsZKH.getAmountOfPaymentLocator(),paymentsZKH.getErrorMessageLocator1(),"sum");
        // Проверка граничного значения для Double, сумма не может быть < 10
        checkFields("9.99",paymentsZKH,paymentsZKH.getAmountOfPaymentLocator(),paymentsZKH.getErrorMessageLocator1(),"sum");
        // Проверка граничного значения для Double, сумма не может быть > 15000
        checkFields("15000.01",paymentsZKH,paymentsZKH.getAmountOfPaymentLocator(),paymentsZKH.getErrorMessageLocator1(),"sum");
        // Проверка граничного значения для Integer, сумма не может быть > 15000
        checkFields("15001",paymentsZKH,paymentsZKH.getAmountOfPaymentLocator(),paymentsZKH.getErrorMessageLocator1(),"sum");
        // Проверка, что сумма не может быть > 15000
        checkFields("100000",paymentsZKH,paymentsZKH.getAmountOfPaymentLocator(),paymentsZKH.getErrorMessageLocator1(),"sum");
        // Проверка на невалидные значения (не число)
        checkFields(")(*()",paymentsZKH,paymentsZKH.getAmountOfPaymentLocator(),paymentsZKH.getErrorMessageLocator2(),"sum");

        /*
        Шаг 8
         */
        // Инициализируем родительский объект
        BasePage pageObject = new BasePage(driver,wait);
        // Переходим в "платежи" (шапка сайта)
        pageObject.click(pageObject.getHeaderPayments());
        /*
        Шаг 9
         */
        // В строку быстрого поиска вводим искомого поставщика услуг
        paymentsPage.sendKey(zKH,paymentsPage.getQuickSearch());
        // Получаем список из быстрого поиска
        List<WebElement> elementList = paymentsPage.getElements(paymentsPage.getListOfQuickSearch());
        /*
        Шаг 10
         */
        // Проверяем, что поставщик в списке первый
        assertEquals(elementList.get(0).getText(),zKH);
        elementList.get(0).click();
        /*
        Шаг 11
         */
        //Убеждаемся, что страница загруженная , соответствует странице шага 5
        assertTrue(paymentsZKH.isElementPresent(paymentsZKH.getPayZKHinMoscowLocator()));
        /*
        Шаг 12
         */
        pageObject.click(pageObject.getHeaderPayments());
        assertTrue(paymentsPage.isElementPresent(paymentsPage.getCommunalPaymentsLocator()));
        paymentsPage.click();
        wait.until(stalenessOf(driver.findElement(paymentsPage.getCommunalPaymentsLocator())));
        /*
        Шаг 13
         */
        // Открытие списка регионов
        RegionsPage listOfRegions = serviceProviders.openListOfRegions();
        listOfRegions.isElementPresent(listOfRegions.getMoscowLocator());
        //Выбор региона Санкт - Петербург
        listOfRegions.selectRegion(listOfRegions.getSPBLocator());
        wait.until(stalenessOf(driver.findElement(listOfRegions.getSPBLocator())));
       /*
       Шаг 14
        */
       // Получили список поставщиков для региона Санкт-Петербург, проверяем , что среди них нет ЖКУ-Москва
        for (WebElement webElement:serviceProviders.getListOfProviders(serviceProviders.getListOfProviders())) {
            if (webElement.getText().equals(zKH)){
                assertTrue(false);
            } else {
                assertTrue(true);
            }
        }
    }


    /*
    Универсальный метод, для проверки невалидных значений, для полей на странице формы оплаты ЖКУ в Москве
    В параметрах передается:

    str - строка ввода значения
    pageObject - текущая страница
    locator - локатор поля
    lokatorError - локатор ошибки
    id - оид поля (может быть code - поле код плательщика, period - период оплаты, sum - итоговая сумма)
     */
    public void checkFields(String str, BasePage pageObject, By locator, By locatorError, String id) throws InterruptedException {
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
