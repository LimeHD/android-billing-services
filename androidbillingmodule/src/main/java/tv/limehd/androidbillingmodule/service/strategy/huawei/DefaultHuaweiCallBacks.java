package tv.limehd.androidbillingmodule.service.strategy.huawei;

import java.util.Map;

import tv.limehd.androidbillingmodule.service.PurchaseData;

public class DefaultHuaweiCallBacks implements HuaweiCallBacks {
    @Override
    public void onHuaweiSetupFinishSuccess() {

    }

    @Override
    public void onHuaweiSetupFinishError(String message) {

    }

    @Override
    public void onHuaweiPurchaseSuccess(PurchaseData purchaseData, Map<String, PurchaseData> map) {

    }

    @Override
    public void onHuaweiPurchaseError(String message) {

    }

    public HuaweiCallBacks getHuaweiCallBacks(){
        return this;
    }
}
