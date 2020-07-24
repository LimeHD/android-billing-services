package tv.limehd.androidbillingmodule.servicesStrategy;

import android.content.Context;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import tv.limehd.androidbillingmodule.interfaces.IPayServicesStrategy;

public class ServiceGoogleStrategy implements IPayServicesStrategy {


    @Override
    public void buy() {

    }

    @Override
    public boolean verifyExistenceService(Context context) {
        if(context == null) return false;
        int status = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(context);
        return status == ConnectionResult.SUCCESS;
    }

}
