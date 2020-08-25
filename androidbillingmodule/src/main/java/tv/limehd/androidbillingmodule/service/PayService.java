package tv.limehd.androidbillingmodule.service;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;

import java.util.List;

import tv.limehd.androidbillingmodule.interfaces.IPayServicesStrategy;
import tv.limehd.androidbillingmodule.interfaces.listeners.ExistenceServiceListener;
import tv.limehd.androidbillingmodule.interfaces.listeners.RequestInventoryListener;
import tv.limehd.androidbillingmodule.interfaces.listeners.RequestPurchasesListener;
import tv.limehd.androidbillingmodule.service.strategy.PurchaseCallBack;
import tv.limehd.androidbillingmodule.service.strategy.ServiceSetupCallBack;
import tv.limehd.androidbillingmodule.service.strategy.google.ServiceGoogleStrategy;
import tv.limehd.androidbillingmodule.service.strategy.huawei.ServiceHuaweiResultStrategy;

public class PayService {

    private EnumPaymentService enumPaymentService;
    private IPayServicesStrategy servicesStrategy;
    private Context context;
    private Activity activity;
    private ServiceSetupCallBack serviceSetupCallBack;

    public PayService(@NonNull Activity activity, @NonNull EnumPaymentService enumPaymentService) {
        initialization(activity, enumPaymentService);
    }

    public PayService(@NonNull Activity activity, @NonNull EnumPaymentService enumPaymentService, @NonNull ServiceSetupCallBack serviceSetupCallBack) {
        initialization(activity, enumPaymentService, serviceSetupCallBack);
    }

    public void initServiceStrategy() {
        if (servicesStrategy == null) {
            throw new NullPointerException("something wrong happened with initialization service strategy");
        }
        this.servicesStrategy.init(activity, serviceSetupCallBack);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (servicesStrategy == null) {
            throw new NullPointerException("something wrong happened with initialization service strategy");
        }
        servicesStrategy.onActivityResult(requestCode, resultCode, data);
    }

    public void tryVerifyExistence(@NonNull ExistenceServiceListener existenceServiceListener) {
        if (servicesStrategy == null) {
            throw new NullPointerException("something wrong happened with initialization service strategy");
        }
        boolean isExistenceService = servicesStrategy.isVerifyExistenceService(context);
        existenceServiceListener.callBackExistenceService(enumPaymentService, isExistenceService);
    }

    public void buySubscription(@NonNull String sku) {
        if (servicesStrategy == null) {
            throw new NullPointerException("something wrong happened with initialization service strategy");
        }
        servicesStrategy.buySubscription(sku);
    }

    public void requestInventory(@NonNull RequestInventoryListener requestInventoryListener, @NonNull List<String> skuList) {
        if (servicesStrategy == null) {
            requestInventoryListener.onErrorRequestInventory("something wrong happened with initialization service strategy");
            return;
        }
        servicesStrategy.requestInventory(requestInventoryListener, skuList);
    }

    public void requestPurchases(@NonNull RequestPurchasesListener requestPurchasesListener) {
        if (servicesStrategy == null) {
            requestPurchasesListener.onErrorRequestPurchases("something wrong happened with initialization service strategy");
            return;
        }
        servicesStrategy.requestPurchases(requestPurchasesListener);
    }

    public void setPurchaseCallBack(@NonNull PurchaseCallBack purchaseCallBack) {
        if (servicesStrategy == null) {
            throw new NullPointerException("something wrong happened with initialization service strategy");
        }
        servicesStrategy.setPurchaseCallBacks(purchaseCallBack);
    }

    private IPayServicesStrategy createServicesStrategyByPayService(@NonNull EnumPaymentService paymentServices) {
        IPayServicesStrategy iPayServicesStrategy;
        switch (paymentServices) {
            case google:
                iPayServicesStrategy = new ServiceGoogleStrategy();
                break;
            case huawei:
                iPayServicesStrategy = new ServiceHuaweiResultStrategy();
                break;
            default:
                iPayServicesStrategy = null;
        }
        return iPayServicesStrategy;
    }

    private void initialization(@NonNull Activity activity, @NonNull EnumPaymentService enumPaymentService) {
        this.activity = activity;
        this.context = activity;
        this.enumPaymentService = enumPaymentService;
        this.servicesStrategy = createServicesStrategyByPayService(this.enumPaymentService);
    }

    private void initialization(@NonNull Activity activity, @NonNull EnumPaymentService enumPaymentService, @NonNull ServiceSetupCallBack serviceSetupCallBack) {
        initialization(activity, enumPaymentService);
        this.serviceSetupCallBack = serviceSetupCallBack;
    }
}
