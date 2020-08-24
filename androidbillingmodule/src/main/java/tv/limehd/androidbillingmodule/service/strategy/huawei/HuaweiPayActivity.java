package tv.limehd.androidbillingmodule.service.strategy.huawei;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import tv.limehd.androidbillingmodule.service.strategy.huawei.callBacks.HuaweiResultPaymentCallBacks;

public class HuaweiPayActivity extends AppCompatActivity {
    private HuaweiResultPaymentCallBacks huaweiResultPaymentCallBacks;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        huaweiResultPaymentCallBacks.onResultPay(data, requestCode);
        super.onActivityResult(requestCode, resultCode, data);
    }

    public HuaweiPayActivity setHuaweiResultPaymentCallBacks(HuaweiResultPaymentCallBacks huaweiResultPaymentCallBacks) {
        this.huaweiResultPaymentCallBacks = huaweiResultPaymentCallBacks;
        return this;
    }
}
