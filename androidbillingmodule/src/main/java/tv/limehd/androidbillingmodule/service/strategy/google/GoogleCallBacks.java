package tv.limehd.androidbillingmodule.service.strategy.google;

import java.util.Map;

import tv.limehd.androidbillingmodule.service.PurchaseData;

public interface GoogleCallBacks {
    void onAcknowledgePurchaseStart();

    void onPurchaseAcknowledgeSuccess(PurchaseData purchaseData, Map<String, PurchaseData> purchaseDataMap);

    void onAcknowledgePurchaseError(String error);

    void onBillingSetupFinishedSuccess();

    void onBillingSetupFinishedError(String message);

    void onBillingServiceDisconnected();

    void onPurchaseUpdateError(String message);
}
