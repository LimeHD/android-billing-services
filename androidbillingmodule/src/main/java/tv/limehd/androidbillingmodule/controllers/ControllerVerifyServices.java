package tv.limehd.androidbillingmodule.controllers;


import java.util.HashMap;

import tv.limehd.androidbillingmodule.interfaces.listeners.ExistenceServiceListener;
import tv.limehd.androidbillingmodule.interfaces.listeners.ExistenceServicesListener;
import tv.limehd.androidbillingmodule.service.PayService;
import tv.limehd.androidbillingmodule.servicesPay.EnumPaymentService;

public class ControllerVerifyServices {

    private ExistenceServicesListener existenceServicesListener;
    private HashMap<EnumPaymentService, PayService> services;
    private HashMap<EnumPaymentService, Boolean> checkedServices;

    public ControllerVerifyServices(HashMap<EnumPaymentService, PayService> services, ExistenceServicesListener existenceServicesListener) {
        this.services = services;
        this.existenceServicesListener = existenceServicesListener;
        controlAllServices();
    }

    public void controlAllServices() {
        checkedServices = new HashMap<>();

        for (EnumPaymentService nameService : EnumPaymentService.values()) {
            controlService(nameService);
        }
    }

    private void controlService(EnumPaymentService enumPaymentService) {
        services.get(enumPaymentService).verifyExistence(new ExistenceServiceListener() {
            @Override
            public void callBackExistenceService(EnumPaymentService paymentService, boolean existing) {
                checkedServices.put(paymentService, existing);

                //Ожидание пока все сервисы кинут калл беск
                if(checkedServices.size() == EnumPaymentService.values().length) {
                    if(existenceServicesListener != null) {
                        existenceServicesListener.callBackExistenceServices(checkedServices);
                    }
                }
            }
        });
    }
}
