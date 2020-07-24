package tv.limehd.androidbillingmodule.interfaces;

import tv.limehd.androidbillingmodule.interfaces.listeners.ExistenceServiceListener;

public interface IPayServicesStrategy {
    void buy();

    boolean verifyExistenceService();
}
