package tv.limehd.androidbillingmodule.service.strategy.huawei.callBacks;

import androidx.annotation.Nullable;

import java.util.Map;

import tv.limehd.androidbillingmodule.service.PurchaseData;
import tv.limehd.androidbillingmodule.service.strategy.PurchaseCallBack;
import tv.limehd.androidbillingmodule.service.strategy.ServiceSetupCallBack;

public interface HuaweiPurchaseCallBacks extends PurchaseCallBack {

    void onHuaweiPurchaseSuccess(@Nullable PurchaseData purchaseData, Map<String, PurchaseData> map);

    void onHuaweiPurchaseError(@Nullable String message);
}
