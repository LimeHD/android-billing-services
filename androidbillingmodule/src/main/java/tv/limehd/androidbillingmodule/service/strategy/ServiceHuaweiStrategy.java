package tv.limehd.androidbillingmodule.service.strategy;

import android.app.Activity;
import android.content.Context;

import androidx.annotation.NonNull;

import com.huawei.hms.api.ConnectionResult;
import com.huawei.hms.api.HuaweiApiAvailability;

import tv.limehd.androidbillingmodule.interfaces.IPayServicesStrategy;

public class ServiceHuaweiStrategy extends ServiceBaseStrategy implements IPayServicesStrategy {


    public ServiceHuaweiStrategy(@NonNull Activity activity) {
        super(activity);
    }

    @Override
    public void buy() {

    }

    @Override
    public boolean isVerifyExistenceService(Context context) {
        if (context == null) return false;
        int status = HuaweiApiAvailability.getInstance().isHuaweiMobileServicesAvailable(context);
        return status == ConnectionResult.SUCCESS;
    }

    @Override
    public void requestInventory(@NonNull Activity activity) {

    }

}
