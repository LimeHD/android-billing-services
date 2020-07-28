package tv.limehd.androidbillingmodule.service.strategy;

import android.app.Activity;
import android.content.Context;

import androidx.annotation.NonNull;

import tv.limehd.androidbillingmodule.interfaces.IPayServicesStrategy;

public class ServiceOtherStrategy extends ServiceBaseStrategy implements IPayServicesStrategy {
    public ServiceOtherStrategy(@NonNull Activity activity) {
        super(activity);
    }

    @Override
    public void buy() {

    }

    @Override
    public boolean isVerifyExistenceService(Context context) {
        return false;
    }

    @Override
    public void requestInventory(@NonNull Activity activity) {

    }

}
