package tv.limehd.androidbillingmodule.interfaces.listeners;

import tv.limehd.androidbillingmodule.service.EnumPaymentService;

public interface ExistenceServiceListener {
    void callBackExistenceService(EnumPaymentService paymentService, boolean existing);
}
