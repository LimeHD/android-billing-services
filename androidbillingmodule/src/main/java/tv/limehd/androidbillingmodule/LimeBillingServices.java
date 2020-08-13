package tv.limehd.androidbillingmodule;

import android.app.Activity;
import android.content.Context;

import androidx.annotation.NonNull;

import java.util.HashMap;

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
        init(activity);
    }

    private void init(@NonNull Activity activity) {
        this.context = activity;
        this.activity = activity;
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

    public void tryRequestInventoryFrom(@NonNull EnumPaymentService service, @NonNull RequestInventoryListener requestInventoryListener) {
        if (service == null) return;
        if (requestInventoryListener == null) return;
        if (payServices == null) return;
        PayService payService = payServices.get(service);
        payService.tryRequestInventory(requestInventoryListener);
    }

    private PayService initServiceByPaymentService(EnumPaymentService paymentService) {
        PayService payService = new PayService(activity, paymentService);
        return payService;
    }


}
