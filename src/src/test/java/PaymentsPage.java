import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;


//Страница платежи
public class PaymentsPage extends PageObject {

    //Инициализация родительского конструктора
    public PaymentsPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }


    //Локатор кнопки ЖКХ (круглая с домом)
    protected By communalPaymentsLocator = By.cssSelector("span.ui-link.ui-menu__logo-link > a[title=ЖКХ]");
    protected By quickSearch = By.cssSelector("input.Input__field_2XFoD.Input__field_align_left_1phV_");
    protected By listOfQuickSearch = By.cssSelector("div.Text__text_3WKsv.Text__text_size_17_HhZgX.Text__text_sizeDesktop_17_IHzjA.Text__text_overflowEllipsis_1A9i2");

    //Нажатие на кнопку ЖКХ, переход на страницу поставщики услуг, возвращает страницу поставщики услуг.
    public ServiceProviders clickPaymentsZKH() {
        driver.findElement(communalPaymentsLocator).click();
        return new ServiceProviders(driver, wait);
    }

    @Override
    public void sendKey(String str, By locator) {
        driver.findElement(quickSearch).sendKeys(str);
    }

    public List<WebElement> getElements(By locator){
        return driver.findElements(locator);
    }
}

