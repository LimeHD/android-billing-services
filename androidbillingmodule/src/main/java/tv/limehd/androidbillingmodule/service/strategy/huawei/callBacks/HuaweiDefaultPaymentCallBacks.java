package tv.limehd.androidbillingmodule.service.strategy.huawei.callBacks;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Map;

import tv.limehd.androidbillingmodule.service.EnumPurchaseState;
import tv.limehd.androidbillingmodule.service.PurchaseData;

public class HuaweiDefaultPaymentCallBacks implements HuaweiPurchaseCallBacks {

    @Override
    public void onHuaweiPurchaseSuccess(PurchaseData purchaseData, Map<String, PurchaseData> map) {

    }

    @Override
    public void onHuaweiPurchaseError(@Nullable String message, @NonNull EnumPurchaseState enumPurchaseState) {

    }

    public HuaweiPurchaseCallBacks getDefaultPaymentCallBacks() {
        return this;
    }
}
