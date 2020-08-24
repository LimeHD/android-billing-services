package tv.limehd.androidbillingmodule.controllers;

import android.app.Activity;

import androidx.annotation.NonNull;

import java.util.HashMap;

import tv.limehd.androidbillingmodule.service.EnumPaymentService;
import tv.limehd.androidbillingmodule.service.PayService;
import tv.limehd.androidbillingmodule.support.Ref;

public class ControllerInitialServices {

    private Activity activity;
    private Ref<HashMap<EnumPaymentService, PayService>> payServices;

    public ControllerInitialServices(@NonNull Activity activity, Ref<HashMap<EnumPaymentService, PayService>> payServices) {
        this.activity = activity;
        this.payServices = payServices;
    }

    public void initAllServices() {
        initHashMap();
        for (EnumPaymentService servicesName : EnumPaymentService.values()) {
            payServices.ref.put(servicesName, initServiceByPaymentService(servicesName));
        }
    }

    public void initSingleService(@NonNull EnumPaymentService service) {
        initHashMap();
        payServices.ref.put(service, initServiceByPaymentService(service));
    }

    private void initHashMap() {
        if (payServices.ref == null) {
            payServices.ref = new HashMap<>();
        } else {
            payServices.ref.clear();
        }
    }

    private PayService initServiceByPaymentService(EnumPaymentService paymentService) {
        PayService payService = new PayService(activity, paymentService);
        return payService;
    }
}
