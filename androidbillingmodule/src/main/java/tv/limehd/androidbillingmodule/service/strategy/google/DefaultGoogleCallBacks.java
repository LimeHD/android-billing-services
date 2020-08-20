package tv.limehd.androidbillingmodule.service.strategy.google;

import java.util.Map;

import tv.limehd.androidbillingmodule.service.PurchaseData;

public class DefaultGoogleCallBacks implements GoogleCallBacks{
    @Override
    public void onAcknowledgePurchaseStart() {

    }

    @Override
    public void onPurchaseAcknowledgeSuccess(PurchaseData purchaseData, Map<String, PurchaseData> purchaseDataMap) {

    }

    @Override
    public void onAcknowledgePurchaseError(String error) {

    }

    @Override
    public void onBillingSetupFinishedSuccess() {

    }

    @Override
    public void onBillingSetupFinishedError(String message) {

    }

    @Override
    public void onBillingServiceDisconnected() {

    }

    @Override
    public void onPurchaseUpdateError(String message) {

    }

    public GoogleCallBacks getGoogleCallBacks() {
        return this;
    }
}
