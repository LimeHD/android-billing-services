package tv.limehd.androidbillingmodule.service.strategy.google;

import java.util.Map;

import tv.limehd.androidbillingmodule.service.PurchaseData;

public interface GoogleCallBacks {
    void onStartAcknowledgePurchase();

    void onPurchaseAcknowledgeSuccess(PurchaseData purchaseData, Map<String, PurchaseData> purchaseDataMap);

    void onErrorAcknowledgePurchase(String error);

    void onBillingSetupFinishedSuccess();

    void onBillingSetupFinishedError(String message);

    void onBillingServiceDisconnected();

    void onErrorPurchaseUpdate(String message);
}
