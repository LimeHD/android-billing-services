package tv.limehd.androidbillingmodule.service.strategy;

import android.app.Activity;
import android.content.Context;

import androidx.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;

public class ServiceBaseStrategy {

    protected Activity activity;
    protected Context context;

    public Map<String, Object> skuDetailsMap;
    public Map<String, Object> purchaseDetailsMap;

    public ServiceBaseStrategy(@NonNull Activity activity) {
        this.activity = activity;
        this.context = activity;
    }
}
