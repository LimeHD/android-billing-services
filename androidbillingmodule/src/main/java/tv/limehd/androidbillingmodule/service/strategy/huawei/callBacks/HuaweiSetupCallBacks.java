package tv.limehd.androidbillingmodule.service.strategy.huawei.callBacks;

import androidx.annotation.Nullable;

import tv.limehd.androidbillingmodule.service.strategy.ServiceSetupCallBack;

public interface HuaweiSetupCallBacks extends ServiceSetupCallBack {
    void onHuaweiSetupFinishSuccess();

    void onHuaweiSetupFinishError(@Nullable String message);

}
