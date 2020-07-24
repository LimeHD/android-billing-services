package tv.limehd.androidbillingmodule.interfaces;

import android.content.Context;

import tv.limehd.androidbillingmodule.interfaces.listeners.ExistenceServiceListener;

public interface IPayServicesStrategy {
    void buy();

    boolean isVerifyExistenceService(Context context);
}
