package tv.limehd.androidbillingmodule.controllers;

import android.app.Activity;

import androidx.annotation.NonNull;

import java.util.HashMap;

import tv.limehd.androidbillingmodule.service.EnumPaymentService;
import tv.limehd.androidbillingmodule.service.PayService;
import tv.limehd.androidbillingmodule.support.Ref;

public class ControllerInitialServices {

    private Activity activity;
    private Ref<HashMap<EnumPaymentService, PayService>> outPayServices;

    public ControllerInitialServices(@NonNull Activity activity, Ref<HashMap<EnumPaymentService, PayService>> outPayServices) {
        this.activity = activity;
        this.outPayServices = outPayServices;
    }

    public void initAllServices() {
        initHashMap();
        for (EnumPaymentService servicesName : EnumPaymentService.values()) {
            outPayServices.ref.put(servicesName, initServiceByPaymentService(servicesName));
        }
    }

    public void initSingleService(@NonNull EnumPaymentService service) {
        initHashMap();
        outPayServices.ref.put(service, initServiceByPaymentService(service));
    }

    private void initHashMap() {
        if (outPayServices.ref == null) {
            outPayServices.ref = new HashMap<>();
        } else {
            outPayServices.ref.clear();
        }
    }

    private PayService initServiceByPaymentService(EnumPaymentService paymentService) {
        PayService payService = new PayService(activity, paymentService);
        return payService;
    }
}
