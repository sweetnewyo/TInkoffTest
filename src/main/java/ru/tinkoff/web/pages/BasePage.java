package ru.tinkoff.web.pages;

import lombok.Data;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

/**
 * Родительский класс для всех страниц.
 * Содержит наиболее частые методы, которые использовались.
 * Так как шапка сайта является частью всех страниц, то шапку решено описывать именно в родительском классе.
 */
@Data
public class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;
    //Локатор для "Платежи" в шапке, не является объектом HomePage, так как локаторы разные
    private By headerPayments = By.cssSelector("ul#mainMenu li.HeaderMenuItem__item_2v2UK:nth-child(3)");

    //Конструктор родительского класса
    public BasePage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    // Метод, который проверяет наличие элемента на странице и его видимость.
    // Возвращает true, если элемент есть в Dom модели и его видно
    // Иначе false
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
        driver.findElement(locator).sendKeys(str);
    }

    public void clear(By locator){
        driver.findElement(locator).clear();
    }

    public void enter(By locator){
        driver.findElement(locator).sendKeys(Keys.ENTER);
    }
    public String getText(By locator){
        return driver.findElement(locator).getText();
    }
    public void click(By locator){
        driver.findElement(locator).click();
    }


}