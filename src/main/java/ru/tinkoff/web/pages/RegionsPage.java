package ru.tinkoff.web.pages;

import lombok.Data;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Класс описывает содержание страницы с регионами.
 */
@Data
public class RegionsPage extends BasePage {
    //Локатор региона Москва
    private By moscowLocator = By.xpath("//span[text()=\"г. Москва\"]");
    //Локатор региона Санкт-Петербург
    private By sPBLocator = By.xpath("//span[text()=\"г. Санкт-Петербург\"]");

    //Конструктор страницы
    public RegionsPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    //Метод выбирает регион, который передали в параметрах
    public void selectRegion(By locator) {
        driver.findElement(locator).click();
    }
}
