package tv.limehd.androidbillingmodule.servicesStrategy;

import android.content.Context;

import tv.limehd.androidbillingmodule.interfaces.IPayServicesStrategy;

public class ServiceGoogleStrategy implements IPayServicesStrategy {


    @Override
    public void buy() {

    }

    @Override
    public boolean verifyExistenceService(Context context) {
        return false;
    }

}
