package tv.limehd.androidbillingmodule.service.strategy.huawei;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import tv.limehd.androidbillingmodule.service.strategy.huawei.callBacks.HuaweiPaymentCallBacks;

public class HuaweiPayActivity extends AppCompatActivity {
    private HuaweiPaymentCallBacks huaweiPaymentCallBacks;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        huaweiPaymentCallBacks.onResultPay(data, requestCode);
        super.onActivityResult(requestCode, resultCode, data);
    }

    public HuaweiPayActivity setHuaweiPaymentCallBacks(HuaweiPaymentCallBacks huaweiPaymentCallBacks) {
        this.huaweiPaymentCallBacks = huaweiPaymentCallBacks;
        return this;
    }
}
