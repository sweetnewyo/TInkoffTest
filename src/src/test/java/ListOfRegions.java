import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ListOfRegions extends PageObject {
    protected By moscowLocator = By.xpath("//span[text()=\"г. Москва\"]");
    protected By sPBLocator = By.xpath("//span[text()=\"г. Санкт-Петербург\"]");
    public ListOfRegions(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public void selectRegion(By locator){
        driver.findElement(locator).click();
    }
}
