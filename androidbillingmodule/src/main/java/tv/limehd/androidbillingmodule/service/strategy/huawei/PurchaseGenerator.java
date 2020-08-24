package tv.limehd.androidbillingmodule.service.strategy.huawei;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tv.limehd.androidbillingmodule.service.PurchaseData;

public class PurchaseGenerator {
    public Map<String, PurchaseData> generateMap(List<String> purchasesList) {
        Map<String, PurchaseData> purchaseMap = new HashMap<>();
        if (purchasesList != null) {
            for (String purchaseString : purchasesList) {
                try {
                    JSONObject jsonObject = new JSONObject(purchaseString);
                    purchaseMap.put(jsonObject.optString("productId"), generatePurchaseData(jsonObject));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return purchaseMap;
    }

    private PurchaseData generatePurchaseData(JSONObject purchase) {
        return new PurchaseData.Builder()
                .setAcknowledged(purchase.optInt("confirmed") == 1)
                .setOrderId(purchase.optString("orderId"))
                .setOriginJson(purchase.toString())
                .setPackageName(purchase.optString("packageName"))
                .setPurchaseState(purchase.optInt("purchaseState"))
                .setPurchaseTime(purchase.optLong("purchaseTime"))
                .setPurchaseToken(purchase.optString("purchaseToken"))
                .setProductId(purchase.optString("productId"))
                .build();
    }

    public PurchaseData generatePurchaseData(String purchaseString) {
        try {
            JSONObject purchase = new JSONObject(purchaseString);
            return new PurchaseData.Builder()
                    .setAcknowledged(purchase.optInt("confirmed") == 1)
                    .setOrderId(purchase.optString("orderId"))
                    .setOriginJson(purchase.toString())
                    .setPackageName(purchase.optString("packageName"))
                    .setPurchaseState(purchase.optInt("purchaseState"))
                    .setPurchaseTime(purchase.optLong("purchaseTime"))
                    .setPurchaseToken(purchase.optString("purchaseToken"))
                    .setProductId(purchase.optString("productId"))
                    .build();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
