package tv.limehd.androidbillingmodule.service.strategy.huawei.callBacks;

import androidx.annotation.Nullable;

import java.util.Map;

import tv.limehd.androidbillingmodule.service.PurchaseData;
import tv.limehd.androidbillingmodule.service.strategy.ServiceCallBack;

public interface HuaweiCallBacks extends ServiceCallBack {

    void onHuaweiSetupFinishSuccess();

    void onHuaweiSetupFinishError(String message);

    void onHuaweiPurchaseSuccess(@Nullable PurchaseData purchaseData, Map<String, PurchaseData> map);

    void onHuaweiPurchaseError(String message);
}
