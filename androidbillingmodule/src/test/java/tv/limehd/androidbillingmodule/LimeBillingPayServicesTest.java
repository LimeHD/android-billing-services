package tv.limehd.androidbillingmodule;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import tv.limehd.androidbillingmodule.servicesPay.PayService;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class LimeBillingPayServicesTest {

    private LimeBillingServices limeBillingServices;
    private PayService[] services;


    @Before
    public void initBillingServices() {
        limeBillingServices = new LimeBillingServices();
        services = PayService.values();
    }

    @Test
    public void limeBillingServicesTest() {
        assertNotNull(limeBillingServices);
    }

    @Test
    public void enumServicesLowerCase() {
        for (PayService service : services) {
            assertEquals(service.name(), service.name().toLowerCase());
        }
    }

    @Test
    public void numberOfCallsBuyMethod() {
       for (PayService service : PayService.values()) {
           assertTrue(limeBillingServices.tryBuySubscriptionFrom(service));
        }
    }

    @After
    public void disposeBillingServices() {
        limeBillingServices = null;
    }
}