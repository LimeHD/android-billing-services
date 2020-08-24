package tv.limehd.androidbillingmodule.service.strategy.huawei.callBacks;

import android.content.Intent;

public interface HuaweiPaymentCallBacks {
    void onResultPay(Intent data, int requestCode);
}
