package tv.limehd.androidbillingmodule.service.strategy.google;

import android.app.Activity;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.billingclient.api.AcknowledgePurchaseParams;
import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.SkuDetails;
import com.android.billingclient.api.SkuDetailsParams;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tv.limehd.androidbillingmodule.interfaces.IPayServicesStrategy;
import tv.limehd.androidbillingmodule.interfaces.listeners.RequestInventoryListener;
import tv.limehd.androidbillingmodule.interfaces.listeners.RequestPurchasesListener;
import tv.limehd.androidbillingmodule.service.PurchaseData;
import tv.limehd.androidbillingmodule.service.strategy.ServiceBaseStrategy;
import tv.limehd.androidbillingmodule.service.strategy.google.callBacks.DefaultGoogleCallBacks;
import tv.limehd.androidbillingmodule.service.strategy.google.callBacks.GoogleCallBacks;
import tv.limehd.androidbillingmodule.service.strategy.google.generators.PurchaseGenerator;
import tv.limehd.androidbillingmodule.service.strategy.google.generators.SkuDetailMapGenerator;

public class ServiceGoogleStrategy extends ServiceBaseStrategy implements IPayServicesStrategy, BillingClientStateListener, PurchasesUpdatedListener {
    private BillingClient billingClient;
    private GoogleCallBacks googleCallBacks;
    private Map<String, SkuDetails> skuDetailsMap;
    private Map<String, PurchaseData> purchaseDetailsMap;

    public ServiceGoogleStrategy(@NonNull Activity activity) {
        super(activity);
        googleCallBacks = new DefaultGoogleCallBacks().getGoogleCallBacks();
        purchaseDetailsMap = new HashMap<>();
        billingClient = BillingClient.newBuilder(context).setListener(this)
                .enablePendingPurchases()
                .build();
        billingClient.startConnection(this);
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

    //purchase update listener
    @Override
    public void onPurchasesUpdated(@NonNull BillingResult billingResult, @Nullable List<Purchase> list) {
        if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK && list != null) {
            for (Purchase purchase : list) {
                handlePurchase(purchase);
            }
        } else {
            googleCallBacks.onPurchaseUpdateError(billingResult.getDebugMessage());
        }
    }

    @Override
    public void buySubscription(@NonNull String sku) {
        SkuDetails skuDetails = skuDetailsMap.get(sku);
        if (skuDetails == null) {
            throw new NullPointerException("not found pack with name: "+ sku);
        }

        BillingFlowParams flowParams = BillingFlowParams.newBuilder()
                .setSkuDetails(skuDetails)
                .build();
        billingClient.launchBillingFlow(activity, flowParams);
    }

    @Override
    public boolean isVerifyExistenceService(@NonNull Context context) {
        int status = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(context);
        return status == ConnectionResult.SUCCESS;
    }

    @Override
    public void requestInventory(@NonNull final RequestInventoryListener requestInventoryListener, @NonNull List<String> skuList) {
        SkuDetailsParams.Builder params = SkuDetailsParams.newBuilder();
        params.setSkusList(skuList).setType(BillingClient.SkuType.SUBS);
        billingClient.querySkuDetailsAsync(params.build(), (billingResult, list) -> {
            if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK && list != null) {
                SkuDetailMapGenerator skuDetailMapGenerator = new SkuDetailMapGenerator();
                skuDetailsMap = skuDetailMapGenerator.generateLocalMap(list);
                requestInventoryListener.onSuccessRequestInventory(skuDetailMapGenerator.generate(list));
            } else {
                requestInventoryListener.onErrorRequestInventory(billingResult.getDebugMessage());
            }
        });
    }

    @Override
    public void requestPurchases(@NonNull RequestPurchasesListener requestPurchasesListener) {
        List<Purchase> purchasesList = queryPurchases();
        if (purchasesList != null) {
            for (Purchase purchase : purchasesList) {
                acknowledge(purchase);
            }
        }
        purchaseDetailsMap = new PurchaseGenerator().generateMap(purchasesList);
        requestPurchasesListener.onSuccessRequestPurchases(purchaseDetailsMap);
    }

    @Override
    public void setEventCallBacks(Object callBacks) {
        this.googleCallBacks = (GoogleCallBacks) callBacks;
    }

    private List<Purchase> queryPurchases() {
        Purchase.PurchasesResult purchasesResult = billingClient.queryPurchases(BillingClient.SkuType.SUBS);
        return purchasesResult.getPurchasesList();
    }

    private void acknowledge(Purchase purchase) {
        if (!purchase.isAcknowledged()) {
            AcknowledgePurchaseParams acknowledgePurchaseParams =
                    AcknowledgePurchaseParams.newBuilder()
                            .setPurchaseToken(purchase.getPurchaseToken())
                            .build();
            googleCallBacks.onAcknowledgePurchaseStart();
            billingClient.acknowledgePurchase(acknowledgePurchaseParams, billingResult -> {
                if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                    PurchaseGenerator purchaseGenerator = new PurchaseGenerator();
                    purchaseDetailsMap.put(purchase.getSku(), purchaseGenerator.generatePurchaseData(purchase));
                    googleCallBacks.onPurchaseAcknowledgeSuccess(purchaseGenerator.generatePurchaseData(purchase), purchaseDetailsMap);
                } else {
                    googleCallBacks.onAcknowledgePurchaseError(billingResult.getDebugMessage());
                }
            });
        }
    }

    private void handlePurchase(Purchase purchase) {
        if (purchase.getPurchaseState() == Purchase.PurchaseState.PURCHASED) {
            acknowledge(purchase);
        }
    }
}
