import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Класс описывает содержание страницы с регионами.
 */
public class ListOfRegions extends PageObject {
    //Локатор региона Москва
    protected By moscowLocator = By.xpath("//span[text()=\"г. Москва\"]");
    //Локатор региона Санкт-Петербург
    protected By sPBLocator = By.xpath("//span[text()=\"г. Санкт-Петербург\"]");
    //Конструктор страницы
    public ListOfRegions(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }
    //Метод выбирает регион, который передали в параметрах
    public void selectRegion(By locator){
        driver.findElement(locator).click();
    }
}
