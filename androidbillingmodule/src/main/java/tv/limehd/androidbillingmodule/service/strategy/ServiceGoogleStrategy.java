package tv.limehd.androidbillingmodule.service.strategy;

import android.app.Activity;
import android.content.Context;

import androidx.annotation.NonNull;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import tv.limehd.androidbillingmodule.interfaces.IPayServicesStrategy;

public class ServiceGoogleStrategy extends ServiceBaseStrategy implements IPayServicesStrategy {


    public ServiceGoogleStrategy(@NonNull Activity activity) {
        super(activity);
    }

    @Override
    public void buy() {

    }

    @Override
    public boolean isVerifyExistenceService(Context context) {
        if (context == null) return false;
        int status = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(context);
        return status == ConnectionResult.SUCCESS;
    }

    @Override
    public void requestInventory(@NonNull Activity activity) {

    }


}
