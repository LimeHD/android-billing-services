package tv.limehd.androidbillingmodule.interfaces;

import android.app.Activity;
import android.content.Context;

import androidx.annotation.NonNull;

import java.util.List;

import tv.limehd.androidbillingmodule.interfaces.listeners.RequestInventoryListener;

public interface IPayServicesStrategy {
    void buy();

    boolean isVerifyExistenceService(Context context);

    void requestInventory(RequestInventoryListener requestInventoryListener, List<String> skuList);
}
