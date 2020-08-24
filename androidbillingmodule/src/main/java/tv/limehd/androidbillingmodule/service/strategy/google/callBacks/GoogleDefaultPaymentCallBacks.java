package tv.limehd.androidbillingmodule.service.strategy.google.callBacks;

import java.util.Map;

import tv.limehd.androidbillingmodule.service.PurchaseData;

public class GoogleDefaultPaymentCallBacks implements GoogleBuySubscriptionCallBacks {
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
    public void onPurchaseUpdateError(String message) {

    }

    public GoogleBuySubscriptionCallBacks getDefaultPaymentCallBacks() {
        return this;
    }
}
