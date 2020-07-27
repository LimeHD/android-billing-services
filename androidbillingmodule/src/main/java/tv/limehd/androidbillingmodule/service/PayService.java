package tv.limehd.androidbillingmodule.service;

import android.content.Context;

import tv.limehd.androidbillingmodule.interfaces.IPayServicesStrategy;
import tv.limehd.androidbillingmodule.interfaces.listeners.ExistenceServiceListener;
import tv.limehd.androidbillingmodule.service.strategy.ServiceGoogleStrategy;
import tv.limehd.androidbillingmodule.service.strategy.ServiceHuaweiStrategy;

public class PayService {

    private EnumPaymentService enumPaymentService;
    private IPayServicesStrategy servicesStrategy;
    private Context context;

    public PayService(Context context, EnumPaymentService enumPaymentService) {
        this.context = context;
        this.enumPaymentService = enumPaymentService;
        this.servicesStrategy = initServicesStrategyByPayService(this.enumPaymentService);
    }

    private IPayServicesStrategy initServicesStrategyByPayService(EnumPaymentService paymentServices) {
        IPayServicesStrategy iPayServicesStrategy;
        switch (paymentServices) {
            case google: iPayServicesStrategy = new ServiceGoogleStrategy(); break;
            case huawei: iPayServicesStrategy = new ServiceHuaweiStrategy(); break;
            default: iPayServicesStrategy = null;
        }
        return iPayServicesStrategy;
    }

    public void tryVerifyExistence(ExistenceServiceListener existenceServiceListener) {
        if(servicesStrategy == null) return;
        boolean isExistenceService = servicesStrategy.isVerifyExistenceService(context);
        existenceServiceListener.callBackExistenceService(enumPaymentService, isExistenceService);
    }

    public void tryRequestInventory() {
        if(servicesStrategy == null) return;
            servicesStrategy.requestInventory();

    }
}
