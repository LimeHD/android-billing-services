package tv.limehd.androidbillingmodule.service.strategy.google;

import com.android.billingclient.api.Purchase;

public interface GoogleCallBacks {
    void onStartAcknowledgePurchase();

    void onPurchaseAcknowledgeSuccess();

    void onErrorAcknowledgePurchase(Purchase purchase);

    void onBillingSetupFinishedSuccess();

    void onBillingSetupFinishedError(String message);

    void onBillingServiceDisconnected();
}
