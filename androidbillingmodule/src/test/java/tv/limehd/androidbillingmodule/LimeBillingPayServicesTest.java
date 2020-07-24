package tv.limehd.androidbillingmodule;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.verify;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class LimeBillingPayServicesTest {

    private LimeBillingServices limeBillingServices;
    private LimeBillingServices.PayServices[] services;


    @Before
    public void initBillingServices() {
        limeBillingServices = new LimeBillingServices();
        services = LimeBillingServices.PayServices.values();
    }

    @Test
    public void limeBillingServicesTest() {
        assertNotNull(limeBillingServices);
    }

    @Test
    public void enumServicesLowerCase() {
        for (LimeBillingServices.PayServices service : services) {
            assertEquals(service.name(), service.name().toLowerCase());
        }
    }

    @Test
    public void numberOfCallsBuyMethod() {
       for (LimeBillingServices.PayServices service : LimeBillingServices.PayServices.values()) {
           assertTrue(limeBillingServices.tryBuySubscriptionFrom(service));
        }
    }

    @After
    public void disposeBillingServices() {
        limeBillingServices = null;
    }
}