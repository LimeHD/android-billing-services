package tv.limehd.androidbillingmodule.interfaces;

import android.app.Activity;
import android.content.Context;

import androidx.annotation.NonNull;

public interface IPayServicesStrategy {
    void buy();

    boolean isVerifyExistenceService(Context context);

    void requestInventory(@NonNull Activity activity);
}
