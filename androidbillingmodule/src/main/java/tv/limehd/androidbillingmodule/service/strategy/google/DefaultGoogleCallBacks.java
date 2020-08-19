package tv.limehd.androidbillingmodule.service.strategy.google;

import com.android.billingclient.api.Purchase;

public class DefaultGoogleCallBacks implements GoogleCallBacks{
    @Override
    public void onStartAcknowledgePurchase() {

    }

    @Override
    public void onPurchaseAcknowledgeSuccess() {

    }

    @Override
    public void onErrorAcknowledgePurchase(Purchase purchase) {

    }

    @Override
    public void onBillingSetupFinishedSuccess() {

    }

    @Override
    public void onBillingSetupFinishedError(String message) {

    }

    @Override
    public void onBillingServiceDisconnected() {

    }

    public GoogleCallBacks getGoogleCallBacks(){
        return this;
    }
}
