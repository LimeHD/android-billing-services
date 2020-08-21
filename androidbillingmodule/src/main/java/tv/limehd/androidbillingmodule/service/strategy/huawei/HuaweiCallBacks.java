package tv.limehd.androidbillingmodule.service.strategy.huawei;

import java.util.Map;

import tv.limehd.androidbillingmodule.service.PurchaseData;

public interface HuaweiCallBacks {

    void onHuaweiSetupFinishSuccess();

    void onHuaweiSetupFinishError(String message);

    void onHuaweiPurchaseSuccess(PurchaseData purchaseData, Map<String, PurchaseData> map);

    void onHuaweiPurchaseError(String message);
}
