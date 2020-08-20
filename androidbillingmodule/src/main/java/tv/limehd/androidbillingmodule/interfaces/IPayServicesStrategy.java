package tv.limehd.androidbillingmodule.interfaces;

import android.content.Context;

import androidx.annotation.NonNull;

import java.util.List;

import tv.limehd.androidbillingmodule.interfaces.listeners.RequestInventoryListener;
import tv.limehd.androidbillingmodule.interfaces.listeners.RequestPurchasesListener;

public interface IPayServicesStrategy {
    void buySubscription(@NonNull String sku);

    boolean isVerifyExistenceService(@NonNull Context context);

    void requestInventory(@NonNull RequestInventoryListener requestInventoryListener, @NonNull List<String> skuList);

    void requestPurchases(@NonNull RequestPurchasesListener requestPurchasesListener);

    void setEventCallBacks(Object callBacks);
}
