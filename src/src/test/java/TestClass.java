import static org.junit.Assert.*;

import org.junit.Test;

public class TestClass extends BaseClass {
    @Test
    public void Test() throws InterruptedException {
        driver.get("https://www.tinkoff.ru/");
        HomePage homePage = new HomePage(driver, wait);
        PaymentsPage paymentsPage = new PaymentsPage(driver, wait);
        ServiceProviders serviceProviders = new ServiceProviders(driver, wait);
        assertTrue(homePage.isElementPresent(homePage.paymentsLocator));
        homePage.clickPayments();
        assertTrue(paymentsPage.isElementPresent(paymentsPage.communalPaymentsLocator));
        paymentsPage.clickPaymentsZKH();
        assertTrue(serviceProviders.isElementPresent(serviceProviders.regionLocator));
        if (!serviceProviders.getText().contains("Москв")){
            serviceProviders.openListOfRegions();
        }
        Thread.sleep(10000);
    }
}
