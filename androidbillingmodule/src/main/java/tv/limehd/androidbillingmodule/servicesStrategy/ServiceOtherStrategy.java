package tv.limehd.androidbillingmodule.servicesStrategy;

import android.content.Context;

import tv.limehd.androidbillingmodule.interfaces.IPayServicesStrategy;

public class ServiceOtherStrategy implements IPayServicesStrategy {
    @Override
    public void buy() {

    }

    @Override
    public boolean isVerifyExistenceService(Context context) {
        return false;
    }

}
