package tv.limehd.androidbillingmodule.interfaces.listeners;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Map;

import tv.limehd.androidbillingmodule.service.SkuDetailData;

public interface RequestInventoryListener {
    void onSuccessRequestInventory(@NonNull Map<String, SkuDetailData> skuDetailsMap);

    void onErrorRequestInventory(@Nullable String error);
}
