package tv.limehd.androidbillingmodule.service.strategy.huawei;

import java.util.Map;

import tv.limehd.androidbillingmodule.service.PurchaseData;
import tv.limehd.androidbillingmodule.service.strategy.huawei.callBacks.HuaweiCallBacks;

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
