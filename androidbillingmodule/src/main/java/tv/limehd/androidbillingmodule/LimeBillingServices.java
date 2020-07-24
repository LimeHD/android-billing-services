package tv.limehd.androidbillingmodule;

import android.content.Context;

import java.util.HashMap;

import tv.limehd.androidbillingmodule.controllers.ControllerVerifyServices;
import tv.limehd.androidbillingmodule.interfaces.listeners.ExistenceServicesListener;
import tv.limehd.androidbillingmodule.service.PayService;
import tv.limehd.androidbillingmodule.servicesEnum.EnumPaymentService;

public class LimeBillingServices {

    private HashMap<EnumPaymentService, PayService> payServices;
    private Context context;

    public LimeBillingServices(Context context) {
        this.context = context;
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

    public boolean tryBuySubscriptionFrom(EnumPaymentService nameService) {
        PayService payService = payServices.get(nameService);
        if(payService != null) {
            return true;
        } else {
            return false;
        }
    }

    private PayService initServiceByPaymentService(EnumPaymentService paymentService) {
        PayService payService = new PayService(paymentService);
        return payService;
    }

}
