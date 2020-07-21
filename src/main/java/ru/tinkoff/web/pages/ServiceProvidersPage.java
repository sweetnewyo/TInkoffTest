package ru.tinkoff.web.pages;

import lombok.Data;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

/**
 * Класс описывает содержание страницы с поставщиками услуг.
 */
@Data
public class ServiceProvidersPage extends BasePage{


    public ServiceProvidersPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    // Локаторы выбора региона
    private By regionLocator = By.cssSelector("span.Link__link_3805p.Link__link_color_blue_10po6.Link__link_type_simple_l_2v_");
    private By regionLocator1 = By.cssSelector("span.PaymentsCatalogHeader__regionSelect_3MRVi");
    // Локаторы ЖКУ-Москва
    private By mosZKH = By.cssSelector("ul.ui-menu.ui-menu_icons.UIPayments__categoryProviders_3Fsrs li:first-child");
    private By mosZKHText = By.xpath("//span[text()=\"ЖКУ-Москва\"]");
    // Локатор списка провайдеров на странице, необходимо для последнего шага
    private By listOfProviders = By.cssSelector("li.ui-menu__item.ui-menu__item_icons");


    // Получение названия текущего выбранного региона
    public String getTextRegion(){
        return driver.findElement(regionLocator).getText();
    }

    // Переход на страницу с регионами, возвращает страницу с регионами
    public RegionsPage openListOfRegions(){
        driver.findElement(regionLocator1).click();
        return new RegionsPage(driver,wait);
    }

    // Переход на страницу для заполнения оплаты.
    public PaymentsZKHPage openMosZKH(){
        driver.findElement(mosZKH).click();
        return new PaymentsZKHPage(driver,wait);
    }
    // Получение текста выбранного поставщика услуг
    public String getTextMosZKH(){
        return driver.findElement(mosZKHText).getText();
    }

    // Метод возращает список доступных поставщиков.
    public List<WebElement> getListOfProviders(By locator){
        return driver.findElements(locator);
    }
}
