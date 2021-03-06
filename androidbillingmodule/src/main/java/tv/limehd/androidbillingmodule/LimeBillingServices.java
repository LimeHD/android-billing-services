package tv.limehd.androidbillingmodule;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.HashMap;
import java.util.List;

import tv.limehd.androidbillingmodule.controllers.ControllerInitialServices;
import tv.limehd.androidbillingmodule.controllers.ControllerVerifyServices;
import tv.limehd.androidbillingmodule.interfaces.listeners.RequestInventoryListener;
import tv.limehd.androidbillingmodule.interfaces.listeners.RequestPurchasesListener;
import tv.limehd.androidbillingmodule.service.EnumPaymentService;
import tv.limehd.androidbillingmodule.service.PayService;
import tv.limehd.androidbillingmodule.service.PurchaseData;
import tv.limehd.androidbillingmodule.service.SkuDetailData;
import tv.limehd.androidbillingmodule.service.strategy.PurchaseCallBack;
import tv.limehd.androidbillingmodule.support.Ref;

public class LimeBillingServices {

    private HashMap<EnumPaymentService, PayService> payServices;
    private Activity activity;

    public LimeBillingServices(@NonNull Activity activity) {
        this.activity = activity;
    }

    public ControllerInitialServices getControllerInitial() {
        if (activity != null) {

            if(payServices == null) {
                payServices = new HashMap<>();
            }
            ControllerInitialServices controllerInitialServices = new ControllerInitialServices(activity, new Ref<>(payServices));
            Log.e("1111", payServices.toString());
            return controllerInitialServices;
        } else {
            throw new NullPointerException("activity is null");
        }
    }

    public ControllerVerifyServices getControllerVerify() {
        return new ControllerVerifyServices(activity);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        for (PayService payService : payServices.values()) {
            if(payService != null) {
                payService.onActivityResult(requestCode, resultCode, data);
            }
        }
    }

    public void launchBuySubscription(@NonNull EnumPaymentService service, @NonNull String sku) {
        if (payServices == null) {
            throw new NullPointerException("pay services is not init");
        }
        PayService payService = payServices.get(service);
        if (payService != null) {
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

    public void setPurchaseCallBack(@NonNull EnumPaymentService service, @NonNull PurchaseCallBack purchaseCallBack) {
        if (payServices == null) {
            throw new NullPointerException("pay services is not init");
        }
        PayService payService = payServices.get(service);
        if (payService != null) {
            payService.setPurchaseCallBack(purchaseCallBack);
        } else {
            throw new NullPointerException(service + " is not init");
        }
    }

    @Nullable
    public PurchaseData getPurchaseDataBySku(@NonNull EnumPaymentService service, @NonNull String sku) {
        if (payServices == null) {
            throw new NullPointerException("pay services is not init");
        }
        PayService payService = payServices.get(service);
        if (payService != null) {
            return payService.getPurchaseDataBySku(sku);
        } else {
            throw new NullPointerException(service + " is not init");
        }
    }

    @Nullable
    public SkuDetailData getSkuDetailDataBySku(@NonNull EnumPaymentService service, @NonNull String sku) {
        if (payServices == null) {
            throw new NullPointerException("pay services is not init");
        }
        PayService payService = payServices.get(service);
        if (payService != null) {
            return payService.getSkuDetailDataBySku(sku);
        } else {
            throw new NullPointerException(service + " is not init");
        }
    }
}
