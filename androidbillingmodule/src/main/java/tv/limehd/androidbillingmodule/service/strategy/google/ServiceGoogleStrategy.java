package tv.limehd.androidbillingmodule.service.strategy.google;

import android.app.Activity;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.billingclient.api.AcknowledgePurchaseParams;
import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.SkuDetails;
import com.android.billingclient.api.SkuDetailsParams;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import java.util.HashMap;
import java.util.List;

import tv.limehd.androidbillingmodule.interfaces.IPayServicesStrategy;
import tv.limehd.androidbillingmodule.interfaces.listeners.RequestInventoryListener;
import tv.limehd.androidbillingmodule.service.strategy.ServiceBaseStrategy;

public class ServiceGoogleStrategy extends ServiceBaseStrategy implements IPayServicesStrategy, BillingClientStateListener, PurchasesUpdatedListener {
    private BillingClient billingClient;
    private GoogleCallBacks googleCallBacks;

    public ServiceGoogleStrategy(@NonNull Activity activity) {
        super(activity);
        skuDetailsMap = new HashMap<>();
        purchaseDetailsMap = new HashMap<>();
        googleCallBacks = new DefaultGoogleCallBacks().getGoogleCallBacks();

        billingClient = BillingClient.newBuilder(context).setListener(this)
                .enablePendingPurchases()
                .build();
        billingClient.startConnection(this);
    }

    @Override
    public void buy() {

    }

    @Override
    public boolean isVerifyExistenceService(Context context) {
        if (context == null) return false;
        int status = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(context);
        return status == ConnectionResult.SUCCESS;
    }

    @Override
    public void requestInventory(final RequestInventoryListener requestInventoryListener, List<String> skuList) {
        SkuDetailsParams.Builder params = SkuDetailsParams.newBuilder();
        params.setSkusList(skuList).setType(BillingClient.SkuType.SUBS);
        billingClient.querySkuDetailsAsync(params.build(), (billingResult, list) -> {
            if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK && list != null) {
                initSkuDetailsMap(list);
                initPurchaseMap();
                requestInventoryListener.onResult(skuDetailsMap, purchaseDetailsMap);
            } else {
                requestInventoryListener.onErrorRequestInventory(billingResult.getDebugMessage());
            }
        });
    }

    @Override
    public void setEventCallBacks(Object callBacks) {
        this.googleCallBacks = (GoogleCallBacks) callBacks;
    }

    //billing client listeners
    @Override
    public void onBillingSetupFinished(@NonNull BillingResult billingResult) {
        if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
            googleCallBacks.onBillingSetupFinishedSuccess();
        } else {
            googleCallBacks.onBillingSetupFinishedError(billingResult.getDebugMessage());
        }
    }

    @Override
    public void onBillingServiceDisconnected() {
        googleCallBacks.onBillingServiceDisconnected();
    }

    //purchase update listeners
    @Override
    public void onPurchasesUpdated(@NonNull BillingResult billingResult, @Nullable List<Purchase> list) {
        if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK && list != null) {
            for (Purchase purchase : list) {
                handlePurchase(purchase);
            }
        }
    }

    private List<Purchase> queryPurchases() {
        Purchase.PurchasesResult purchasesResult = billingClient.queryPurchases(BillingClient.SkuType.SUBS);
        return purchasesResult.getPurchasesList();
    }

    private void initPurchaseMap() {
        List<Purchase> purchasesList = queryPurchases();
        if (purchasesList != null) {
            for (Purchase purchase : purchasesList) {
                acknowledge(purchase);
                purchaseDetailsMap.put(purchase.getSku(), purchase);
            }
        }
    }

    private void initSkuDetailsMap(List<SkuDetails> list) {
        for (SkuDetails skuDetails : list) {
            skuDetailsMap.put(skuDetails.getSku(), skuDetails);
        }
    }

    private void acknowledge(Purchase purchase) {
        if (!purchase.isAcknowledged()) {
            AcknowledgePurchaseParams acknowledgePurchaseParams =
                    AcknowledgePurchaseParams.newBuilder()
                            .setPurchaseToken(purchase.getPurchaseToken())
                            .build();
            googleCallBacks.onStartAcknowledgePurchase();
            billingClient.acknowledgePurchase(acknowledgePurchaseParams, billingResult -> {
                if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                    googleCallBacks.onPurchaseAcknowledgeSuccess();
                } else {
                    googleCallBacks.onErrorAcknowledgePurchase(purchase);
                }
            });
        } else {
            googleCallBacks.onPurchaseAcknowledgeSuccess();
        }
    }

    private void handlePurchase(Purchase purchase) {
        if (purchase.getPurchaseState() == Purchase.PurchaseState.PURCHASED) {
            acknowledge(purchase);
        }
    }
}
