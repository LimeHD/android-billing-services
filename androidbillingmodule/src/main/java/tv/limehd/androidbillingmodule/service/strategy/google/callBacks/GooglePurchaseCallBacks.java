package tv.limehd.androidbillingmodule.service.strategy.google.callBacks;

import java.util.Map;

import tv.limehd.androidbillingmodule.service.EnumPurchaseState;
import tv.limehd.androidbillingmodule.service.PurchaseData;
import tv.limehd.androidbillingmodule.service.strategy.PurchaseCallBack;

public interface GooglePurchaseCallBacks extends PurchaseCallBack {
    void onAcknowledgePurchaseStart();

    void onPurchaseAcknowledgeSuccess(PurchaseData purchaseData, Map<String, PurchaseData> purchaseDataMap);

    void onAcknowledgePurchaseError(String error, EnumPurchaseState enumPurchaseState);

    void onPurchaseUpdateError(String message, EnumPurchaseState enumPurchaseState);
}
