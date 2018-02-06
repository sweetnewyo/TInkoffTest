import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PaymentsPage extends PageObject {
    public PaymentsPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    protected By communalPaymentsLocator = By.cssSelector("span.ui-link.ui-menu__logo-link > a[title=ЖКХ]");

    public void clickPaymentsZKH() {
        driver.findElement(communalPaymentsLocator).click();
    }

}

