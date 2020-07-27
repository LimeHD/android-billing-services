package tv.limehd.androidbillingmodule.controllers;


import androidx.annotation.NonNull;

import java.util.HashMap;

import tv.limehd.androidbillingmodule.interfaces.listeners.ExistenceServiceListener;
import tv.limehd.androidbillingmodule.interfaces.listeners.ExistenceServicesListener;
import tv.limehd.androidbillingmodule.service.PayService;
import tv.limehd.androidbillingmodule.service.EnumPaymentService;

public class ControllerVerifyServices {

    private ExistenceServicesListener existenceServicesListener;
    private HashMap<EnumPaymentService, PayService> services;
    private HashMap<EnumPaymentService, Boolean> checkedServices;

    public ControllerVerifyServices(@NonNull HashMap<EnumPaymentService, PayService> services, @NonNull ExistenceServicesListener existenceServicesListener) throws NullPointerException {
        try {
            this.services = services;
            this.existenceServicesListener = existenceServicesListener;
            controlAllServices();
        } catch (NullPointerException e) {
            e.getMessage();
        }
    }

    public void controlAllServices() throws NullPointerException {
        checkedServices = new HashMap<>();

        for (EnumPaymentService nameService : EnumPaymentService.values()) {
            tryControlService(nameService);
        }
    }

    private void tryControlService(EnumPaymentService enumPaymentService) {
        if (services == null) return;

        services.get(enumPaymentService).tryVerifyExistence(new ExistenceServiceListener() {
            @Override
            public void callBackExistenceService(EnumPaymentService paymentService, boolean existing) {
                checkedServices.put(paymentService, existing);

                //Ожидание пока все сервисы кинут калл беск
                if (checkedServices.size() == EnumPaymentService.values().length) {
                    if (existenceServicesListener != null) {
                        existenceServicesListener.callBackExistenceServices(checkedServices);
                    }
                }
            }
        });
    }
}
