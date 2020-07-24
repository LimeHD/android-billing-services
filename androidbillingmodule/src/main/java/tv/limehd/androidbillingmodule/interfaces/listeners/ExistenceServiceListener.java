package tv.limehd.androidbillingmodule.interfaces.listeners;

import tv.limehd.androidbillingmodule.servicesEnum.EnumPaymentService;

public interface ExistenceServiceListener {
    void callBackExistenceService(EnumPaymentService paymentService, boolean existing);
}
