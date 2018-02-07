import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;


public class ServiceProviders extends PageObject{

    public ServiceProviders(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    protected By regionLocator = By.cssSelector("span.Link__link_3805p.Link__link_color_blue_10po6.Link__link_type_simple_l_2v_");
    protected By regionLocator1 = By.cssSelector("span.PaymentsCatalogHeader__regionSelect_3MRVi");
    protected By mosZKH = By.cssSelector("ul.ui-menu.ui-menu_icons.UIPayments__categoryProviders_3Fsrs li:first-child");
    protected By mosZKHText = By.xpath("//span[text()=\"ЖКУ-Москва\"]");
    protected By listOfProviders = By.cssSelector("li.ui-menu__item.ui-menu__item_icons");


    public String getTextRegion(){
        return driver.findElement(regionLocator).getText();
    }

    public ListOfRegions openListOfRegions(){
        driver.findElement(regionLocator1).click();
        return new ListOfRegions(driver,wait);
    }

    public PaymentsZKH openMosZKH(){
        driver.findElement(mosZKH).click();
        return new PaymentsZKH(driver,wait);
    }

    public String getTextMosZKH(){
        return driver.findElement(mosZKHText).getText();
    }

    public List<WebElement> getListOfProviders(By locator){
        return driver.findElements(locator);
    }
}
