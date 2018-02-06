import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage extends PageObject{
    protected By paymentsLocator = By.cssSelector("ul#mainMenu li.ui-product-block-header-menu-item__item_3WGrk:nth-child(3)");

    public HomePage(WebDriver driver, WebDriverWait wait) {
        super(driver,wait);
    }

    public void clickPayments(){
        driver.findElement(paymentsLocator).click();
    }
}
