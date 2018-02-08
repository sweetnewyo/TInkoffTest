import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Класс описывает содержание страницы после выбора поставщика услуг.
 */
public class PaymentsZKH extends PageObject {

    //Локатор для выбора "Оплатить ЖКУ в Москве"
    protected By payZKHinMoscowLocator = By.cssSelector("ul.Tabs__tabs_2Vaw9 li:last-child");
    //Локатор поля "Код плательщика"
    protected By payerCodeLocator = By.id("payerCode");
    // Локатор поля "период оплаты"
    protected By paymentPeriodLocator = By.name("provider-period");
    // Локатор поля "Добровольное страхование
    protected By voluntaryInsuranceLocator = By.cssSelector("div.Input__value_2Kx90.Input__value_changed_2SIME input.Input__valueContent_1Os4v.Input__valueContent_primary_3sxF0");
    // Локатор поля "Итоговая сумма"
    protected By amountOfPaymentLocator = By.cssSelector("div.ui-form__row.ui-form__row_combination input.Input__valueContent_1Os4v.Input__valueContent_primary_3sxF0");
    // Локаторы ошибок
    protected By errorMessageLocator1 = By.cssSelector("div.ui-form-field-error-message.ui-form-field-error-message_ui-form");
    protected By errorMessageLocator2 = By.cssSelector("div.ui-form__row.ui-form__row_combination div.ui-form-field-error-message.ui-form-field-error-message_ui-form");


    public PaymentsZKH(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    // Метод для выбора оплаты жку в Москве
    public void openPayZKHinMoscow() {
        driver.findElement(payZKHinMoscowLocator).isDisplayed();
        driver.findElement(payZKHinMoscowLocator).click();
    }

    // Переопределенный метод (добавлена доп проверка отображения элемента)
    @Override
    public void sendKey(String str, By locator){
        driver.findElement(locator).isDisplayed();
        driver.findElement(locator).sendKeys(str);
    }

    // Переопределенный метод (добавлена доп проверка отображения элемента)
    // Пришлось выдумать велосипед, так как clear() не очищала поля.
    @Override
    public void clear(By locator){
        driver.findElement(locator).sendKeys(Keys.CONTROL + "a");
        driver.findElement(locator).sendKeys(Keys.DELETE);
    }

    // Метод заменяющий клик по кнопке оплатить ЖКУ в Москве
    @Override
    public void enter(By locator){
        driver.findElement(locator).sendKeys(Keys.ENTER);
    }


    @Override
    public String getText(By locator){
        return driver.findElement(locator).getText();
    }

}
