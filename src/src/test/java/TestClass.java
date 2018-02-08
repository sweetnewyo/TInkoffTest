import static org.junit.Assert.*;
import static org.openqa.selenium.support.ui.ExpectedConditions.stalenessOf;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import java.util.List;

/**
 * Тестовый класс с набором шагов.
 */
public class TestClass extends BaseClass {
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
        assertTrue(homePage.isElementPresent(homePage.paymentsLocator));
        /*
        Шаг 2
         */
        //Переход на страницу платежи
        PaymentsPage paymentsPage = homePage.click();
        /*
        Шаг 3
         */
        //Проверка, что кнопка ЖКХ отображается
        assertTrue(paymentsPage.isElementPresent(paymentsPage.communalPaymentsLocator));
        //Переход на странциу поставщиков услуг
        ServiceProviders serviceProviders = paymentsPage.click();
        /*
        Шаг 4
         */
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
        /*
        Шаг 5
         */
        // Проверка, что поставщик ЖКУ-Москва отображается
        serviceProviders.isElementPresent(serviceProviders.mosZKH);
        // Переход на страницу поставщика
        PaymentsZKH paymentsZKH = serviceProviders.openMosZKH();
        // Получаем название поставщика ЖКУ-Москва
        String zKH = serviceProviders.getTextMosZKH();
        /*
        Шаг 6
         */
        // Выбор формы оплаты ЖКХ в Москве
        wait.until(stalenessOf(driver.findElement(serviceProviders.mosZKH)));
        paymentsZKH.openPayZKHinMoscow();
        wait.until(stalenessOf(driver.findElement(paymentsZKH.payZKHinMoscowLocator)));


        /*
        Шаг 7
         */
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
        //Проверка на не полностью заполненный код если не хватает 1 цифры
        checkFields("564784367", paymentsZKH,paymentsZKH.payerCodeLocator,paymentsZKH.errorMessageLocator1, "code");
        // Проверка на 0
        checkFields("0", paymentsZKH,paymentsZKH.payerCodeLocator,paymentsZKH.errorMessageLocator1, "code");
        // Проврка на не полностью заполненный код если не хватает более 1 цифры
        checkFields("4367", paymentsZKH,paymentsZKH.payerCodeLocator,paymentsZKH.errorMessageLocator1, "code");
        // Заполнение валидным кодом
        paymentsZKH.sendKey("7654932456", paymentsZKH.payerCodeLocator);


        //Проверка на невалидные значения период оплаты коммунальных услуг
        // Проверка если все значения равны 0
        checkFields("000000", paymentsZKH, paymentsZKH.paymentPeriodLocator, paymentsZKH.errorMessageLocator1,"period");
        // Прворека если месяц равен 00, если год равен 0000 , система предупреждения не выдает, при корректном месяце.
        checkFields("000001", paymentsZKH, paymentsZKH.paymentPeriodLocator, paymentsZKH.errorMessageLocator1, "period");
        // Проверка граничного значения если месяц > 12
        checkFields("132015",paymentsZKH,paymentsZKH.paymentPeriodLocator, paymentsZKH.errorMessageLocator1, "period");
        // Проверка на неполностью заполненное поле
        checkFields("12201", paymentsZKH, paymentsZKH.paymentPeriodLocator, paymentsZKH.errorMessageLocator1, "period");
        // Ввод влидного значения
        paymentsZKH.sendKey("022018",paymentsZKH.paymentPeriodLocator);


        //Проверка на невалидные значения сумма платежа
        // Проверка на 0
        checkFields("0",paymentsZKH,paymentsZKH.amountOfPaymentLocator,paymentsZKH.errorMessageLocator1,"sum");
        // Проверка граничного значения для Integer, сумма не может быть < 10
        checkFields("9",paymentsZKH,paymentsZKH.amountOfPaymentLocator,paymentsZKH.errorMessageLocator1,"sum");
        // Проверка граничного значения для Double, сумма не может быть < 10
        checkFields("9.99",paymentsZKH,paymentsZKH.amountOfPaymentLocator,paymentsZKH.errorMessageLocator1,"sum");
        // Проверка граничного значения для Double, сумма не может быть > 15000
        checkFields("15000.01",paymentsZKH,paymentsZKH.amountOfPaymentLocator,paymentsZKH.errorMessageLocator1,"sum");
        // Проверка граничного значения для Integer, сумма не может быть > 15000
        checkFields("15001",paymentsZKH,paymentsZKH.amountOfPaymentLocator,paymentsZKH.errorMessageLocator1,"sum");
        // Проверка, что сумма не может быть > 15000
        checkFields("100000",paymentsZKH,paymentsZKH.amountOfPaymentLocator,paymentsZKH.errorMessageLocator1,"sum");
        // Проверка на невалидные значения (не число)
        checkFields(")(*()",paymentsZKH,paymentsZKH.amountOfPaymentLocator,paymentsZKH.errorMessageLocator2,"sum");

        /*
        Шаг 8
         */
        // Инициализируем родительский объект
        PageObject pageObject = new PageObject(driver,wait);
        // Переходим в "платежи" (шапка сайта)
        pageObject.click(pageObject.headerPayments);
        /*
        Шаг 9
         */
        // В строку быстрого поиска вводим искомого поставщика услуг
        paymentsPage.sendKey(zKH,paymentsPage.quickSearch);
        // Получаем список из быстрого поиска
        List<WebElement> elementList = paymentsPage.getElements(paymentsPage.listOfQuickSearch);
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
        assertTrue(paymentsZKH.isElementPresent(paymentsZKH.payZKHinMoscowLocator));
        /*
        Шаг 12
         */
        pageObject.click(pageObject.headerPayments);
        assertTrue(paymentsPage.isElementPresent(paymentsPage.communalPaymentsLocator));
        paymentsPage.click();
        wait.until(stalenessOf(driver.findElement(paymentsPage.communalPaymentsLocator)));
        /*
        Шаг 13
         */
        // Открытие списка регионов
        ListOfRegions listOfRegions = serviceProviders.openListOfRegions();
        listOfRegions.isElementPresent(listOfRegions.moscowLocator);
        //Выбор региона Санкт - Петербург
        listOfRegions.selectRegion(listOfRegions.sPBLocator);
        wait.until(stalenessOf(driver.findElement(listOfRegions.sPBLocator)));
       /*
       Шаг 14
        */
       // Получили список поставщиков для региона Санкт-Петербург, проверяем , что среди них нет ЖКУ-Москва
        for (WebElement webElement:serviceProviders.getListOfProviders(serviceProviders.listOfProviders)) {
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
