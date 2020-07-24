package tv.limehd.androidbillingmodule.servicesStrategy;

import android.content.Context;

import com.huawei.hms.api.ConnectionResult;
import com.huawei.hms.api.HuaweiApiAvailability;
import com.huawei.hms.common.HuaweiApi;

import tv.limehd.androidbillingmodule.interfaces.IPayServicesStrategy;

public class ServiceHuaweiStrategy implements IPayServicesStrategy {
    @Override
    public void buy() {

    }

    @Override
    public boolean verifyExistenceService(Context context) {
        int status = HuaweiApiAvailability.getInstance().isHuaweiMobileServicesAvailable(context);
        return status == ConnectionResult.SUCCESS;
    }

}
