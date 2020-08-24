package tv.limehd.androidbillingmodule.service.strategy.google.callBacks;

import tv.limehd.androidbillingmodule.service.strategy.ServiceSetupCallBack;

public interface GoogleSetupCallBacks extends ServiceSetupCallBack {
    void onBillingSetupFinishedSuccess();

    void onBillingSetupFinishedError(String message);

    void onBillingServiceDisconnected();
}
