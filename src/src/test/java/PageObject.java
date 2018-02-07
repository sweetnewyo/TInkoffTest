import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

public class PageObject {
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected By headerPayments = By.cssSelector("ul#mainMenu li.HeaderMenuItem__item_2v2UK:nth-child(3)");

    public PageObject(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    public boolean isElementPresent(By locator) {
        try {
            wait.until((driver) -> driver.findElement(locator));
            wait.until(visibilityOfElementLocated(locator));
            return true;
        } catch (NoSuchElementException e) {
            System.out.println(driver.findElement(locator).getText() + "not found");
            return false;
        }
    }

    public void sendKey(String str, By locator){

    }

    public void clear(By locator){

    }

    public void enter(By locator){

    }
    public String getText(By locator){
        return null;
    }
    public void click(By locator){
        driver.findElement(locator).click();
    }


}