package tv.limehd.androidbillingmodule.service.strategy;

import android.app.Activity;
import android.content.Context;

import androidx.annotation.NonNull;

public class ServiceBaseStrategy {

    protected Activity activity;
    protected Context context;

    public ServiceBaseStrategy() {

    }

    @Deprecated
    public ServiceBaseStrategy(@NonNull Activity activity) {
        init(activity);
    }

    protected void init(@NonNull Activity activity) {
        this.activity = activity;
        this.context = activity;
    }
}
