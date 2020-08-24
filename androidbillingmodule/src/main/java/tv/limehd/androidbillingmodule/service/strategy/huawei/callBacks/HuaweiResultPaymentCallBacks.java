package tv.limehd.androidbillingmodule.service.strategy.huawei.callBacks;

import android.content.Intent;

public interface HuaweiResultPaymentCallBacks {
    void onResultPay(Intent data, int requestCode);
}
