package tv.limehd.androidbillingmodule.service.strategy.huawei.callBacks;

import tv.limehd.androidbillingmodule.service.strategy.ServiceSetupCallBack;

public interface HuaweiSetupCallBacks extends ServiceSetupCallBack {
    void onHuaweiSetupFinishSuccess();

    void onHuaweiSetupFinishError(String message);

}
