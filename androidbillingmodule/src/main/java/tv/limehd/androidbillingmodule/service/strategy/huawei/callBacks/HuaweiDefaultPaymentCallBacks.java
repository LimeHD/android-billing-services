package tv.limehd.androidbillingmodule.service.strategy.huawei.callBacks;

import java.util.Map;

import tv.limehd.androidbillingmodule.service.PurchaseData;

public class HuaweiDefaultPaymentCallBacks implements HuaweiPurchaseCallBacks {

    @Override
    public void onHuaweiPurchaseSuccess(PurchaseData purchaseData, Map<String, PurchaseData> map) {

    }

    @Override
    public void onHuaweiPurchaseError(String message) {

    }

    public HuaweiPurchaseCallBacks getDefaultPaymentCallBacks() {
        return this;
    }
}
