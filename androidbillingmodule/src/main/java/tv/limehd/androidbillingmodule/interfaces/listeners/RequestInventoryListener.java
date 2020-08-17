package tv.limehd.androidbillingmodule.interfaces.listeners;

import androidx.annotation.NonNull;

import java.util.List;
import java.util.Map;

public interface RequestInventoryListener {
    void onResult(@NonNull Map<String, Object> skuDetailsMap, @NonNull Map<String, Object> purchaseDetailsMap);
    void onErrorRequestInventory(String error);
}
