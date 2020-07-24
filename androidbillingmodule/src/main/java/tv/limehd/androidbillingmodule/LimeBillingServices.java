package tv.limehd.androidbillingmodule;

import tv.limehd.androidbillingmodule.Interfaces.IPayServicesStrategy;
import tv.limehd.androidbillingmodule.ServicesStrategy.ServiceGoogleStrategy;
import tv.limehd.androidbillingmodule.ServicesStrategy.ServiceHuaweiStrategy;

public class LimeBillingServices {

    public enum PayServices {
        google,
        huawei,
    }

    private IPayServicesStrategy servicesStrategy;

    public boolean tryBuySubscriptionFrom(PayServices payServices) {
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
