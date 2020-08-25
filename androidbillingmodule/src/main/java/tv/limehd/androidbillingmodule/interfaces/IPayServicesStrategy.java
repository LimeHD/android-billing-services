package tv.limehd.androidbillingmodule.interfaces;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import tv.limehd.androidbillingmodule.interfaces.listeners.RequestInventoryListener;
import tv.limehd.androidbillingmodule.interfaces.listeners.RequestPurchasesListener;
import tv.limehd.androidbillingmodule.service.strategy.PurchaseCallBack;
import tv.limehd.androidbillingmodule.service.strategy.ServiceSetupCallBack;

public interface IPayServicesStrategy {
    void init(@NonNull Activity activity, @NonNull ServiceSetupCallBack serviceSetupCallBack);

    void buySubscription(@NonNull String sku);

    boolean isVerifyExistenceService(@NonNull Context context);

    void requestInventory(@NonNull RequestInventoryListener requestInventoryListener, @NonNull List<String> skuList);

    void requestPurchases(@NonNull RequestPurchasesListener requestPurchasesListener);

    void setPurchaseCallBacks(@NonNull PurchaseCallBack callBack);

    void onActivityResult(int requestCode, int resultCode, @Nullable Intent data);
}
