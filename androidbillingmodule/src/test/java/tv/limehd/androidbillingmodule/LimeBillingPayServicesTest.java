package tv.limehd.androidbillingmodule;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import tv.limehd.androidbillingmodule.servicesEnum.EnumPaymentService;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class LimeBillingPayServicesTest {

    private LimeBillingServices limeBillingServices;
    private EnumPaymentService[] services;


    @Before
    public void initBillingServices() {
        limeBillingServices = new LimeBillingServices();
        services = EnumPaymentService.values();
    }

    @Test
    public void limeBillingServicesTest() {
        assertNotNull(limeBillingServices);
    }

    @Test
    public void enumServicesLowerCase() {
        for (EnumPaymentService service : services) {
            assertEquals(service.name(), service.name().toLowerCase());
        }
    }

    @Test
    public void numberOfCallsBuyMethod() {
        limeBillingServices.init();
       for (EnumPaymentService service : EnumPaymentService.values()) {
           assertTrue(limeBillingServices.tryBuySubscriptionFrom(service));
        }
    }

    @After
    public void disposeBillingServices() {
        limeBillingServices = null;
    }
}