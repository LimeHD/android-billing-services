package tv.limehd.androidbillingmodule.service.strategy;

import android.content.Context;

import com.huawei.hms.api.ConnectionResult;
import com.huawei.hms.api.HuaweiApiAvailability;

import tv.limehd.androidbillingmodule.interfaces.IPayServicesStrategy;

public class ServiceHuaweiStrategy implements IPayServicesStrategy {
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
    public void requestInventory() {

    }

}
