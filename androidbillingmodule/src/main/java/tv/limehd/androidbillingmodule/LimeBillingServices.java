package tv.limehd.androidbillingmodule;

import java.util.HashMap;

import tv.limehd.androidbillingmodule.controllers.ControllerVerifyServices;
import tv.limehd.androidbillingmodule.interfaces.listeners.ExistenceServicesListener;
import tv.limehd.androidbillingmodule.service.PayService;
import tv.limehd.androidbillingmodule.servicesPay.EnumPaymentService;

public class LimeBillingServices {

    private HashMap<EnumPaymentService, PayService> payServices;

    public LimeBillingServices() {

    }

    public LimeBillingServices init() {
        payServices = new HashMap<>();
        for (EnumPaymentService servicesName: EnumPaymentService.values()) {
            payServices.put(servicesName, initServiceByPaymentService(servicesName));
        }
        return this;
    }

    public void verifyExistenceAllService(ExistenceServicesListener existenceServicesListener) {
        ControllerVerifyServices controllerVerifyServices = new ControllerVerifyServices(payServices, existenceServicesListener);
    }

    private PayService initServiceByPaymentService(EnumPaymentService paymentService) {
        PayService payService = new PayService(paymentService);
        return payService;
    }


}
