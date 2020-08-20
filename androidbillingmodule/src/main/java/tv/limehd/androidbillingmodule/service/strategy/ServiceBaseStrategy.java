package tv.limehd.androidbillingmodule.service.strategy;

import android.app.Activity;
import android.content.Context;

import androidx.annotation.NonNull;

public class ServiceBaseStrategy {

    protected Activity activity;
    protected Context context;

    public ServiceBaseStrategy(@NonNull Activity activity) {
        this.activity = activity;
        this.context = activity;
    }
}
