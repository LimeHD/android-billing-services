package tv.limehd.androidbillingmodule;

import android.content.Context;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import tv.limehd.androidbillingmodule.service.EnumPaymentService;

import static org.junit.Assert.*;

public class LimeBillingPayServicesTest {

    private LimeBillingServices limeBillingServices;
    private EnumPaymentService[] services;


    @Before
    public void initBillingServices() {
        limeBillingServices = new LimeBillingServices(Mockito.mock(Context.class));
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
       for (EnumPaymentService service : EnumPaymentService.values()) {
           assertTrue(limeBillingServices.tryBuySubscriptionFrom(service));
        }
    }

    @After
    public void disposeBillingServices() {
        limeBillingServices = null;
    }
}