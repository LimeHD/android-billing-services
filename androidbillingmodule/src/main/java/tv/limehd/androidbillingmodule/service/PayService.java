package tv.limehd.androidbillingmodule.service;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import tv.limehd.androidbillingmodule.interfaces.IPayServicesStrategy;
import tv.limehd.androidbillingmodule.interfaces.listeners.ExistenceServiceListener;
import tv.limehd.androidbillingmodule.interfaces.listeners.RequestInventoryListener;
import tv.limehd.androidbillingmodule.service.strategy.ServiceGoogleStrategy;
import tv.limehd.androidbillingmodule.service.strategy.ServiceHuaweiStrategy;

public class PayService {

    private EnumPaymentService enumPaymentService;
    private IPayServicesStrategy servicesStrategy;
    private Context context;
    private Activity activity;

    public PayService(Activity activity, EnumPaymentService enumPaymentService) {
        this.activity = activity;
        this.context = activity;
        this.enumPaymentService = enumPaymentService;
        this.servicesStrategy = initServicesStrategyByPayService(this.enumPaymentService);
    }

    private IPayServicesStrategy initServicesStrategyByPayService(EnumPaymentService paymentServices) {
        IPayServicesStrategy iPayServicesStrategy;
        switch (paymentServices) {
            case google:
                iPayServicesStrategy = new ServiceGoogleStrategy(activity);
                break;
            case huawei:
                iPayServicesStrategy = new ServiceHuaweiStrategy(activity);
                break;
            default:
                iPayServicesStrategy = null;
        }
        return iPayServicesStrategy;
    }

    public void tryVerifyExistence(ExistenceServiceListener existenceServiceListener) {
        if (servicesStrategy == null) return;
        boolean isExistenceService = servicesStrategy.isVerifyExistenceService(context);
        existenceServiceListener.callBackExistenceService(enumPaymentService, isExistenceService);
    }

    public void tryRequestInventory(@NonNull RequestInventoryListener requestInventoryListener) {
        if (servicesStrategy == null) return;
        if (requestInventoryListener == null) return;
        servicesStrategy.requestInventory(requestInventoryListener);

    }
}
