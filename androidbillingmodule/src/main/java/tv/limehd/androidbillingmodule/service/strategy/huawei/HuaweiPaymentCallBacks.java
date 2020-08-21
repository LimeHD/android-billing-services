package tv.limehd.androidbillingmodule.service.strategy.huawei;

import android.content.Intent;

public interface HuaweiPaymentCallBacks {
    void onResultPay(Intent data, int requestCode);
}
