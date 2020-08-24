package tv.limehd.androidbillingmodule.controllers;


import androidx.annotation.NonNull;

import java.util.HashMap;

import tv.limehd.androidbillingmodule.interfaces.listeners.ExistenceServiceListener;
import tv.limehd.androidbillingmodule.interfaces.listeners.ExistenceServicesListener;
import tv.limehd.androidbillingmodule.service.EnumPaymentService;
import tv.limehd.androidbillingmodule.service.PayService;

public class ControllerVerifyServices {

    private ExistenceServicesListener existenceServicesListener;
    private HashMap<EnumPaymentService, PayService> services;
    private HashMap<EnumPaymentService, Boolean> checkedServices;

    public ControllerVerifyServices(@NonNull HashMap<EnumPaymentService, PayService> services) {
        this.services = services;
    }

    public void verifyAllServices(@NonNull ExistenceServicesListener existenceServicesListener) {
        checkedServices = new HashMap<>();
        this.existenceServicesListener = existenceServicesListener;
        for (EnumPaymentService nameService : EnumPaymentService.values()) {
            verifyService(nameService, new ExistenceServiceListener() {
                @Override
                public void callBackExistenceService(EnumPaymentService paymentService, boolean existing) {
                    checkedServices.put(paymentService, existing);

                    //Ожидание пока все сервисы кинут калл беск
                    if (checkedServices.size() == EnumPaymentService.values().length) {
                        existenceServicesListener.callBackExistenceServices(checkedServices);
                    }
                }
            });
        }
    }

    public void verifyService(EnumPaymentService enumPaymentService, @NonNull ExistenceServiceListener existenceServiceListener) {
        if (services != null && services.get(enumPaymentService) != null) {
            services.get(enumPaymentService).tryVerifyExistence(existenceServiceListener);
        } else {
            throw new NullPointerException("failed to get service " + enumPaymentService.name() + " in controllerVerifyServices");
        }
    }


}
