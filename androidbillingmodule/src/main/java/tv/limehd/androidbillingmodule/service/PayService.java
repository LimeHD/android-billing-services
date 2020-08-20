package tv.limehd.androidbillingmodule.service;

import android.app.Activity;
import android.content.Context;

import androidx.annotation.NonNull;

import java.util.List;

import tv.limehd.androidbillingmodule.interfaces.IPayServicesStrategy;
import tv.limehd.androidbillingmodule.interfaces.listeners.ExistenceServiceListener;
import tv.limehd.androidbillingmodule.interfaces.listeners.RequestInventoryListener;
import tv.limehd.androidbillingmodule.interfaces.listeners.RequestPurchasesListener;
import tv.limehd.androidbillingmodule.service.strategy.google.ServiceGoogleStrategy;
import tv.limehd.androidbillingmodule.service.strategy.huawei.ServiceHuaweiStrategy;

public class PayService {

    private EnumPaymentService enumPaymentService;
    private IPayServicesStrategy servicesStrategy;
    private Context context;
    private Activity activity;

    public PayService(@NonNull Activity activity, @NonNull EnumPaymentService enumPaymentService) {
        this.activity = activity;
        this.context = activity;
        this.enumPaymentService = enumPaymentService;
        this.servicesStrategy = initServicesStrategyByPayService(this.enumPaymentService);
    }

    private IPayServicesStrategy initServicesStrategyByPayService(@NonNull EnumPaymentService paymentServices) {
        IPayServicesStrategy iPayServicesStrategy;
        switch (paymentServices) {
            case google:
                iPayServicesStrategy = new ServiceGoogleStrategy(activity);
                break;
            case huawei:
//                iPayServicesStrategy = null;
                iPayServicesStrategy = new ServiceHuaweiStrategy(activity);
                break;
            default:
                iPayServicesStrategy = null;
        }
        return iPayServicesStrategy;
    }

    public void tryVerifyExistence(@NonNull ExistenceServiceListener existenceServiceListener) {
        if (servicesStrategy == null) return;
        boolean isExistenceService = servicesStrategy.isVerifyExistenceService(context);
        existenceServiceListener.callBackExistenceService(enumPaymentService, isExistenceService);
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

    public void setEventCallBacks(@NonNull Object callbacks) {
        if (servicesStrategy == null) return;
        servicesStrategy.setEventCallBacks(callbacks);
    }
}
