package tv.limehd.androidbillingmodule.service.strategy.huawei.generators;

import com.huawei.hms.iap.entity.ProductInfo;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tv.limehd.androidbillingmodule.service.SkuDetailData;

public class SkuDetailMapGenerator {
    public Map<String, SkuDetailData> generate(List<ProductInfo> list) {
        Map<String, SkuDetailData> skuDetailDataMap = new HashMap<>();
        for (ProductInfo productInfo : list) {
            skuDetailDataMap.put(productInfo.getProductId(), initSkuDetailData(productInfo));
        }
        return skuDetailDataMap;
    }

    private SkuDetailData initSkuDetailData(ProductInfo productInfo) {
        return new SkuDetailData.Builder()
                .setDescription(productInfo.getProductDesc())
                .setTitle(productInfo.getProductName())
                .setSubscriptionPeriod(productInfo.getSubPeriod())
                .setPriceCurrencyCode(productInfo.getCurrency())
                .setPriceAmountMicros(productInfo.getMicrosPrice())
                .setPrice(productInfo.getPrice())
                .setOriginJSON(generateOriginGSON(productInfo).toString())
                .setProductId(productInfo.getProductId())
                .build();
    }

    private JSONObject generateOriginGSON(ProductInfo productInfo) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("productId", productInfo.getProductId());
            jsonObject.put("priceType", productInfo.getPriceType());
            jsonObject.put("price", productInfo.getPrice());
            jsonObject.put("microsPrice", productInfo.getMicrosPrice());
            jsonObject.put("originalLocalPrice", productInfo.getOriginalLocalPrice());
            jsonObject.put("originalMicroPrice", productInfo.getOriginalMicroPrice());
            jsonObject.put("productName", productInfo.getProductName());
            jsonObject.put("productDesc", productInfo.getProductDesc());
            jsonObject.put("subPeriod", productInfo.getSubPeriod());
            jsonObject.put("subSpecialPrice", productInfo.getSubSpecialPrice());
            jsonObject.put("subSpecialPriceMicros", productInfo.getSubSpecialPriceMicros());
            jsonObject.put("subSpecialPeriod", productInfo.getSubSpecialPeriod());
            jsonObject.put("subSpecialPeriodCycles", productInfo.getSubSpecialPeriodCycles());
            jsonObject.put("subFreeTrialPeriod", productInfo.getSubFreeTrialPeriod());
            jsonObject.put("subGroupId", productInfo.getSubGroupId());
            jsonObject.put("subGroupTitle", productInfo.getSubGroupTitle());
            jsonObject.put("subProductLevel", productInfo.getSubProductLevel());
            jsonObject.put("status", productInfo.getStatus());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
