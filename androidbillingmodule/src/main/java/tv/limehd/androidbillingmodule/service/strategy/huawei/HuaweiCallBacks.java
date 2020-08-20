package tv.limehd.androidbillingmodule.service.strategy.huawei;

public interface HuaweiCallBacks {

    void onHuaweiSetupFinishSuccess();

    void onHuaweiSetupFinishError();

    void onHuaweiPurchaseSuccess();

    void onHuaweiPurchaseError();
}
