package tv.limehd.androidbillingmodule.service.strategy.google.generators;

import com.android.billingclient.api.SkuDetails;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tv.limehd.androidbillingmodule.service.SkuDetailData;

public class SkuDetailMapGenerator {
    public Map<String, SkuDetailData> generate(List<SkuDetails> list) {
        Map<String, SkuDetailData> skuDetailDataMap = new HashMap<>();
        for (SkuDetails skuDetails : list) {
            skuDetailDataMap.put(skuDetails.getSku(), initSkuDetailData(skuDetails));
        }
        return skuDetailDataMap;
    }

    private SkuDetailData initSkuDetailData(SkuDetails skuDetails) {
        return new SkuDetailData.Builder()
                .setDescription(skuDetails.getDescription())
                .setTitle(skuDetails.getTitle())
                .setSubscriptionPeriod(skuDetails.getSubscriptionPeriod())
                .setPriceCurrencyCode(skuDetails.getPriceCurrencyCode())
                .setPriceAmountMicros(skuDetails.getPriceAmountMicros())
                .setPrice(skuDetails.getPrice())
                .setOriginJSON(skuDetails.getOriginalJson())
                .setProductId(skuDetails.getSku())
                .build();
    }

    public Map<String, SkuDetails> generateLocalMap(List<SkuDetails> list) {
        Map<String, SkuDetails> skuDetailsMap = new HashMap<>();
        for (SkuDetails skuDetails : list) {
            skuDetailsMap.put(skuDetails.getSku(), skuDetails);
        }
        return skuDetailsMap;
    }
}
