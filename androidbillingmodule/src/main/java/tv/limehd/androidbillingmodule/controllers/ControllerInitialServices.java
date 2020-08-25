package tv.limehd.androidbillingmodule.controllers;

import android.app.Activity;

import androidx.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;

import tv.limehd.androidbillingmodule.service.EnumPaymentService;
import tv.limehd.androidbillingmodule.service.PayService;
import tv.limehd.androidbillingmodule.service.strategy.ServiceSetupCallBack;
import tv.limehd.androidbillingmodule.support.Ref;

public class ControllerInitialServices {

    private Activity activity;
    private Ref<HashMap<EnumPaymentService, PayService>> payServices;

    public ControllerInitialServices(@NonNull Activity activity, Ref<HashMap<EnumPaymentService, PayService>> payServices) {
        this.activity = activity;
        this.payServices = payServices;
    }

    public void initServices(@NonNull Map<EnumPaymentService, ServiceSetupCallBack> setupCallBackMap) {
        initHashMap();
        for (EnumPaymentService servicesName : setupCallBackMap.keySet()) {
            ServiceSetupCallBack serviceSetupCallBack = setupCallBackMap.get(servicesName);
            if(serviceSetupCallBack != null) {
                payServices.ref.put(servicesName, initServiceByPaymentService(servicesName, serviceSetupCallBack));
            }
        }
    }

    public void initSingleService(@NonNull EnumPaymentService service, @NonNull ServiceSetupCallBack serviceSetupCallBack) {
        initHashMap();
        payServices.ref.put(service, initServiceByPaymentService(service, serviceSetupCallBack));
    }

    public PayService initSingleEmptyServiceByPaymentService(@NonNull EnumPaymentService paymentService) {
        PayService payService = new PayService(activity, paymentService);
        return payService;
    }

    private void initHashMap() {
        if (payServices.ref == null) {
            payServices.ref = new HashMap<>();
        } else {
            payServices.ref.clear();
        }
    }

    private PayService initServiceByPaymentService(@NonNull EnumPaymentService paymentService, @NonNull ServiceSetupCallBack serviceSetupCallBack) {
        PayService payService = new PayService(activity, paymentService, serviceSetupCallBack);
        payService.initServiceStrategy();
        return payService;
    }


}
