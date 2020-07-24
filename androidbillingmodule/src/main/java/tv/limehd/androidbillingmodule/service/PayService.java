package tv.limehd.androidbillingmodule.service;

import tv.limehd.androidbillingmodule.interfaces.IPayServicesStrategy;
import tv.limehd.androidbillingmodule.interfaces.listeners.ExistenceServiceListener;
import tv.limehd.androidbillingmodule.servicesEnum.EnumPaymentService;
import tv.limehd.androidbillingmodule.servicesStrategy.ServiceGoogleStrategy;
import tv.limehd.androidbillingmodule.servicesStrategy.ServiceHuaweiStrategy;

public class PayService {

    private EnumPaymentService nameService;
    private IPayServicesStrategy servicesStrategy;

    public PayService(EnumPaymentService paymentService) {
        nameService = paymentService;
        servicesStrategy = initServicesStrategyByPayService(nameService);
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

    public void verifyExistence (ExistenceServiceListener existenceServiceListener) {
        existenceServiceListener.callBackExistenceService(nameService, servicesStrategy.verifyExistenceService());
    }
}
