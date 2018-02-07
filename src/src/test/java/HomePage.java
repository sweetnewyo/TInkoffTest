import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;


//Стартовая страница www.tinkoff.ru
public class HomePage extends PageObject{

    //Локатор "Платежи"
    protected By paymentsLocator = By.cssSelector("ul#mainMenu li.ui-product-block-header-menu-item__item_3WGrk:nth-child(3)");

    //Иницмализация родительского конструктора.
    public HomePage(WebDriver driver, WebDriverWait wait) {
        super(driver,wait);
    }

    //Переход на страницу "Платежи", возвращает страницу "Платежи"
    public PaymentsPage click(){
        driver.findElement(paymentsLocator).click();
        return new PaymentsPage(driver,wait);
    }
}
