package tv.limehd.androidbillingmodule.service.strategy.google;

import com.android.billingclient.api.Purchase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tv.limehd.androidbillingmodule.service.PurchaseData;

public class PurchaseGenerator {
    public Map<String, PurchaseData> generateMap(List<Purchase> purchasesList) {
        Map<String, PurchaseData> purchaseMap = new HashMap<>();
        if (purchasesList != null) {
            for (Purchase purchase : purchasesList) {
                purchaseMap.put(purchase.getSku(), generatePurchaseData(purchase));
            }
        }
        return purchaseMap;
    }

    public PurchaseData generatePurchaseData(Purchase purchase){
        return new PurchaseData.Builder()
                .setAcknowledged(purchase.isAcknowledged())
                .setOrderId(purchase.getOrderId())
                .setOriginJson(purchase.getOriginalJson())
                .setPackageName(purchase.getPackageName())
                .setPurchaseState(purchase.getPurchaseState())
                .setPurchaseTime(purchase.getPurchaseTime())
                .setPurchaseToken(purchase.getPurchaseToken())
                .setProductId(purchase.getSku())
                .build();
    }
}
