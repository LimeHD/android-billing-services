package tv.limehd.androidbillingmodule;

import android.app.Activity;
import android.content.Context;

import androidx.annotation.NonNull;

import java.util.HashMap;
import java.util.List;

import tv.limehd.androidbillingmodule.controllers.ControllerVerifyServices;
import tv.limehd.androidbillingmodule.interfaces.listeners.ExistenceServicesListener;
import tv.limehd.androidbillingmodule.interfaces.listeners.RequestInventoryListener;
import tv.limehd.androidbillingmodule.service.EnumPaymentService;
import tv.limehd.androidbillingmodule.service.PayService;

public class LimeBillingServices {

    private HashMap<EnumPaymentService, PayService> payServices;
    private Context context;
    private Activity activity;

    public LimeBillingServices(@NonNull Activity activity) {
        this.context = activity;
        this.activity = activity;
//        init(activity);
    }

    public void init() {
        payServices = new HashMap<>();
        for (EnumPaymentService servicesName : EnumPaymentService.values()) {
            payServices.put(servicesName, initServiceByPaymentService(servicesName));
        }
    }

    public void verifyExistenceAllService(ExistenceServicesListener existenceServicesListener) {
        ControllerVerifyServices controllerVerifyServices = new ControllerVerifyServices(payServices, existenceServicesListener);
        controllerVerifyServices.verifyAllServices();
    }

    public boolean tryBuySubscriptionFrom(EnumPaymentService nameService) {
        if (payServices == null) return false;
        PayService payService = payServices.get(nameService);
        return payService != null;
    }

    public void tryRequestInventoryFrom(@NonNull EnumPaymentService service, @NonNull List<String> skuList, @NonNull RequestInventoryListener requestInventoryListener) {
        if (payServices == null) return;
        PayService payService = payServices.get(service);
        payService.tryRequestInventory(requestInventoryListener, skuList);
    }

    public void setEventCallBacks(@NonNull EnumPaymentService service, @NonNull Object callbacks) {
        if (payServices == null) return;
        PayService payService = payServices.get(service);
        payService.setEventCallBacks(callbacks);
    }

    private PayService initServiceByPaymentService(EnumPaymentService paymentService) {
        PayService payService = new PayService(activity, paymentService);
        return payService;
    }


}
