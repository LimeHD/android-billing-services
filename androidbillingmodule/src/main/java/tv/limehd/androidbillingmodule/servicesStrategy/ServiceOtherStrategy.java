package tv.limehd.androidbillingmodule.servicesStrategy;

import tv.limehd.androidbillingmodule.interfaces.IPayServicesStrategy;

public class ServiceOtherStrategy implements IPayServicesStrategy {
    @Override
    public void buy() {

    }

    @Override
    public boolean verifyExistenceService() {
        return false;
    }
}
