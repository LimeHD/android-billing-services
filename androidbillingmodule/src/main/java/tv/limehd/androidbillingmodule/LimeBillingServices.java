package tv.limehd.androidbillingmodule;

import tv.limehd.androidbillingmodule.interfaces.IPayServicesStrategy;
import tv.limehd.androidbillingmodule.servicesPay.PayService;
import tv.limehd.androidbillingmodule.servicesStrategy.ServiceGoogleStrategy;
import tv.limehd.androidbillingmodule.servicesStrategy.ServiceHuaweiStrategy;

public class LimeBillingServices {

    private IPayServicesStrategy servicesStrategy;

    public boolean tryBuySubscriptionFrom(PayService payServices) {
        this.servicesStrategy = null;
        switch (payServices) {
            case google: servicesStrategy = new ServiceGoogleStrategy(); break;
            case huawei: servicesStrategy = new ServiceHuaweiStrategy(); break;
        }
        if(servicesStrategy == null) {
            return false;
        } else {
            servicesStrategy.buy();
            return true;
        }
    }
}
