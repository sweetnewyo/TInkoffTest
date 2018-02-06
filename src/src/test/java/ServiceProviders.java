import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ServiceProviders extends PageObject{

    public ServiceProviders(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    protected By regionLocator = By.cssSelector("span.Link__link_3805p.Link__link_color_blue_10po6.Link__link_type_simple_l_2v_");
    protected By regionLocatorList = By.xpath("//span[text()=\"г. Москва\"]");

    public String getText(){
        return driver.findElement(regionLocator).getText();
    }

    public void openListOfRegions(){
        driver.findElement(regionLocator).click();
    }
}
