package tv.limehd.androidbillingmodule.controllers;

import android.app.Activity;

import androidx.annotation.NonNull;

import java.util.HashMap;

import tv.limehd.androidbillingmodule.service.EnumPaymentService;
import tv.limehd.androidbillingmodule.service.PayService;
import tv.limehd.androidbillingmodule.service.strategy.ServiceSetupCallBack;
import tv.limehd.androidbillingmodule.service.strategy.SetupBillingInterfaces;
import tv.limehd.androidbillingmodule.support.Ref;

public class ControllerInitialServices {

    private Activity activity;
    private Ref<HashMap<EnumPaymentService, PayService>> payServices;

    public ControllerInitialServices(@NonNull Activity activity, Ref<HashMap<EnumPaymentService, PayService>> payServices) {
        this.activity = activity;
        this.payServices = payServices;
    }

    public void initService(@NonNull SetupBillingInterfaces setupBillingInterfaces) {
        clearServices();
        for (EnumPaymentService servicesName : setupBillingInterfaces.keySet()) {
            ServiceSetupCallBack serviceSetupCallBack = setupBillingInterfaces.getServiceSetupCallback(servicesName);
            if(serviceSetupCallBack != null) {
                payServices.ref.put(servicesName, initServiceByPaymentService(servicesName, serviceSetupCallBack));
            }
        }
    }

    protected PayService initSingleEmptyServiceByPaymentService(@NonNull EnumPaymentService paymentService) {
        PayService payService = new PayService(activity, paymentService);
        return payService;
    }

    private void clearServices() {
        if (payServices.ref == null) {
            throw new NullPointerException("pay services map is null");
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
