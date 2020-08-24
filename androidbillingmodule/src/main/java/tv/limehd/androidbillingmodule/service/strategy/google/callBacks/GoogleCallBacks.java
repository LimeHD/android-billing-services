package tv.limehd.androidbillingmodule.service.strategy.google.callBacks;

import java.util.Map;

import tv.limehd.androidbillingmodule.service.PurchaseData;
import tv.limehd.androidbillingmodule.service.strategy.interfaces.ServiceCallBack;

public interface GoogleCallBacks extends ServiceCallBack {
    void onAcknowledgePurchaseStart();

    void onPurchaseAcknowledgeSuccess(PurchaseData purchaseData, Map<String, PurchaseData> purchaseDataMap);

    void onAcknowledgePurchaseError(String error);

    void onBillingSetupFinishedSuccess();

    void onBillingSetupFinishedError(String message);

    void onBillingServiceDisconnected();

    void onPurchaseUpdateError(String message);
}
