import com.sun.glass.ui.View;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class BaseClass {
    protected static WebDriver driver;
    protected static WebDriverWait wait;

    //Инициализация драйвера
    //Установка неявных и явных ожиданий
    //Установка размера браузера по размеру экрана.
    @Before
    public void setUp(){
        ChromeOptions co = new ChromeOptions();
        co.addArguments("start-maximized");
        driver = new ChromeDriver(co);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, 10);
    }

    //Выключение драйвера.
    @After
    public void tearDown(){
        driver.close();
    }

}
