package tv.limehd.androidbillingmodule;

import android.app.Activity;
import android.content.Context;

import androidx.annotation.NonNull;

import java.util.HashMap;
import java.util.List;

import tv.limehd.androidbillingmodule.controllers.ControllerVerifyServices;
import tv.limehd.androidbillingmodule.interfaces.listeners.ExistenceServicesListener;
import tv.limehd.androidbillingmodule.interfaces.listeners.RequestInventoryListener;
import tv.limehd.androidbillingmodule.interfaces.listeners.RequestPurchasesListener;
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

    public void init(@NonNull EnumPaymentService service) {
        payServices = new HashMap<>();
        payServices.put(service, initServiceByPaymentService(service));
    }

    public void verifyExistenceAllService(ExistenceServicesListener existenceServicesListener) {
        ControllerVerifyServices controllerVerifyServices = new ControllerVerifyServices(payServices, existenceServicesListener);
        controllerVerifyServices.verifyAllServices();
    }

    public void launchBuySubscription(@NonNull EnumPaymentService service, @NonNull String sku) {
        if (payServices == null) {
            throw new NullPointerException("pay services is not init");
        }
        PayService payService = payServices.get(service);
        if(payService!=null){
            payService.buySubscription(sku);
        } else {
            throw new NullPointerException(service + " is not init");
        }
    }

    public void requestInventoryFrom(@NonNull EnumPaymentService service, @NonNull List<String> skuList, @NonNull RequestInventoryListener requestInventoryListener) {
        if (payServices == null) {
            requestInventoryListener.onErrorRequestInventory("pay services is not init");
        }
        PayService payService = payServices.get(service);
        if (payService != null) {
            payService.requestInventory(requestInventoryListener, skuList);
        } else {
            requestInventoryListener.onErrorRequestInventory(service + " is not init");
        }
    }

    public void requestPurchases(@NonNull EnumPaymentService service, @NonNull RequestPurchasesListener requestPurchasesListener) {
        if (payServices == null) {
            requestPurchasesListener.onErrorRequestPurchases("pay services is not init");
        }
        PayService payService = payServices.get(service);
        if (payService != null) {
            payService.requestPurchases(requestPurchasesListener);
        } else {
            requestPurchasesListener.onErrorRequestPurchases(service + " is not init");
        }
    }

    public void setEventCallBacks(@NonNull EnumPaymentService service, @NonNull Object callbacks) {
        if (payServices == null) {
               throw new NullPointerException("pay services is not init");
        }
        PayService payService = payServices.get(service);
        if (payService != null) {
            payService.setEventCallBacks(callbacks);
        } else {
            throw new NullPointerException("Create event callbacks for nonexistence service!!!" + "Service named: " + service);
        }
    }

    private PayService initServiceByPaymentService(EnumPaymentService paymentService) {
        PayService payService = new PayService(activity, paymentService);
        return payService;
    }


}
