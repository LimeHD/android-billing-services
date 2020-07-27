package tv.limehd.androidbillingmodule;

import android.content.Context;

import androidx.annotation.NonNull;

import java.util.HashMap;

import tv.limehd.androidbillingmodule.controllers.ControllerVerifyServices;
import tv.limehd.androidbillingmodule.interfaces.listeners.ExistenceServicesListener;
import tv.limehd.androidbillingmodule.service.PayService;
import tv.limehd.androidbillingmodule.service.EnumPaymentService;

public class LimeBillingServices {

    private HashMap<EnumPaymentService, PayService> payServices;
    private Context context;

    public LimeBillingServices(@NonNull Context context) {
        init(context);
    }

    private void init(Context context) {
        if (context == null) return;
        this.context = context;
        payServices = new HashMap<>();
        for (EnumPaymentService servicesName : EnumPaymentService.values()) {
            payServices.put(servicesName, initServiceByPaymentService(servicesName));
        }
    }

    public void verifyExistenceAllService(ExistenceServicesListener existenceServicesListener) {
        ControllerVerifyServices controllerVerifyServices = new ControllerVerifyServices(payServices, existenceServicesListener);
    }

    public boolean tryBuySubscriptionFrom(EnumPaymentService nameService) {
        if (payServices == null) return false;
        PayService payService = payServices.get(nameService);
        return payService != null;
    }

    public void tryRequestInventoryFrom(@NonNull EnumPaymentService service) {
        if(service == null) return;
        if(payServices == null) return;
        PayService payService = payServices.get(service);

    }

    private PayService initServiceByPaymentService(EnumPaymentService paymentService) {
        PayService payService = new PayService(context, paymentService);
        return payService;
    }

}
