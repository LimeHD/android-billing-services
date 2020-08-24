package tv.limehd.androidbillingmodule.interfaces.listeners;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Map;

import tv.limehd.androidbillingmodule.service.PurchaseData;

public interface RequestPurchasesListener {
    void onSuccessRequestPurchases(@NonNull Map<String, PurchaseData> purchaseDetailsMap);

    void onErrorRequestPurchases(@Nullable String message);
}
