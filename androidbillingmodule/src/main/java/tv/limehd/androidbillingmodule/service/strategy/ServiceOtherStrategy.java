package tv.limehd.androidbillingmodule.service.strategy;

import android.app.Activity;
import android.content.Context;

import androidx.annotation.NonNull;

import java.util.List;

import tv.limehd.androidbillingmodule.interfaces.IPayServicesStrategy;
import tv.limehd.androidbillingmodule.interfaces.listeners.RequestInventoryListener;

public class ServiceOtherStrategy extends ServiceBaseStrategy implements IPayServicesStrategy {
    public ServiceOtherStrategy(@NonNull Activity activity) {
        super(activity);
    }

    @Override
    public void buy() {

    }

    @Override
    public boolean isVerifyExistenceService(@NonNull Context context) {
        return false;
    }

    @Override
    public void requestInventory(RequestInventoryListener requestInventoryListener, List<String> skuList) {

    }

    @Override
    public void setEventCallBacks(Object callBacks) {

    }
}
