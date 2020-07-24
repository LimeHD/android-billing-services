package tv.limehd.androidbillingmodule.interfaces.listeners;

import java.util.HashMap;

import tv.limehd.androidbillingmodule.servicesEnum.EnumPaymentService;

public interface ExistenceServicesListener {
    void callBackExistenceServices(HashMap<EnumPaymentService, Boolean> existing);
}
