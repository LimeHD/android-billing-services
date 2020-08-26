package tv.limehd.androidbillingmodule.service.strategy.huawei;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.huawei.hms.api.ConnectionResult;
import com.huawei.hms.api.HuaweiApiAvailability;
import com.huawei.hms.iap.Iap;
import com.huawei.hms.iap.IapClient;
import com.huawei.hms.iap.entity.OrderStatusCode;
import com.huawei.hms.iap.entity.OwnedPurchasesReq;
import com.huawei.hms.iap.entity.ProductInfoReq;
import com.huawei.hms.iap.entity.PurchaseIntentReq;
import com.huawei.hms.iap.entity.PurchaseResultInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tv.limehd.androidbillingmodule.interfaces.IPayServicesStrategy;
import tv.limehd.androidbillingmodule.interfaces.listeners.RequestInventoryListener;
import tv.limehd.androidbillingmodule.interfaces.listeners.RequestPurchasesListener;
import tv.limehd.androidbillingmodule.service.PurchaseData;
import tv.limehd.androidbillingmodule.service.SkuDetailData;
import tv.limehd.androidbillingmodule.service.strategy.PurchaseCallBack;
import tv.limehd.androidbillingmodule.service.strategy.ServiceBaseStrategy;
import tv.limehd.androidbillingmodule.service.strategy.ServiceSetupCallBack;
import tv.limehd.androidbillingmodule.service.strategy.huawei.callBacks.HuaweiDefaultPaymentCallBacks;
import tv.limehd.androidbillingmodule.service.strategy.huawei.callBacks.HuaweiPurchaseCallBacks;
import tv.limehd.androidbillingmodule.service.strategy.huawei.callBacks.HuaweiResultPaymentCallBacks;
import tv.limehd.androidbillingmodule.service.strategy.huawei.callBacks.HuaweiSetupCallBacks;
import tv.limehd.androidbillingmodule.service.strategy.huawei.generators.PurchaseGenerator;
import tv.limehd.androidbillingmodule.service.strategy.huawei.generators.SkuDetailMapGenerator;

public class ServiceHuaweiResultStrategy extends ServiceBaseStrategy implements IPayServicesStrategy, HuaweiResultPaymentCallBacks {
    public static final int REQUEST_CODE_BUY_HUAWEI = 543;
    private Map<String, PurchaseData> purchaseDataMap;
    private Map<String, SkuDetailData> skuDetailDataMap;
    private HuaweiSetupCallBacks huaweiSetupCallBacks;
    private HuaweiPurchaseCallBacks huaweiPurchaseCallBacks;

    public ServiceHuaweiResultStrategy() {
        super();
    }

    @Deprecated
    public ServiceHuaweiResultStrategy(@NonNull Activity activity, @NonNull ServiceSetupCallBack serviceSetupCallBack) {
        super(activity);
        init(activity, serviceSetupCallBack);
    }

    @Override
    public void init(@NonNull Activity activity, @NonNull ServiceSetupCallBack serviceSetupCallBack) {
        super.init(activity);
        huaweiSetupCallBacks = (HuaweiSetupCallBacks) serviceSetupCallBack;
        ((HuaweiPayActivity) activity).setHuaweiResultPaymentCallBacks(this);
        huaweiPurchaseCallBacks = new HuaweiDefaultPaymentCallBacks().getDefaultPaymentCallBacks();
        purchaseDataMap = new HashMap<>();
        skuDetailDataMap = new HashMap<>();
        Iap.getIapClient(activity).isEnvReady()
                .addOnFailureListener(e -> huaweiSetupCallBacks.onHuaweiSetupFinishError(e.getLocalizedMessage()))
                .addOnSuccessListener(isEnvReadyResult -> huaweiSetupCallBacks.onHuaweiSetupFinishSuccess());
    }


    @Override
    public void onResultPay(Intent data, int requestCode) {
        if (requestCode == REQUEST_CODE_BUY_HUAWEI) {
            PurchaseResultInfo purchaseResultInfo = Iap.getIapClient(activity).parsePurchaseResultInfoFromIntent(data);
            switch (purchaseResultInfo.getReturnCode()) {
                case OrderStatusCode.ORDER_STATE_SUCCESS:
                    PurchaseGenerator purchaseGenerator = new PurchaseGenerator();
                    PurchaseData purchaseData = purchaseGenerator.generatePurchaseData(purchaseResultInfo.getInAppPurchaseData());
                    if (purchaseData != null) {
                        purchaseDataMap.put(purchaseData.getProductId(), purchaseData);
                    }
                    huaweiPurchaseCallBacks.onHuaweiPurchaseSuccess(purchaseData, purchaseDataMap);
                    break;
                default:
                    huaweiPurchaseCallBacks.onHuaweiPurchaseError("code:" + purchaseResultInfo.getReturnCode() + "\tmessage: " + purchaseResultInfo.getErrMsg());
            }
        }
    }

    @Override
    public void buySubscription(@NonNull String sku) {
        PurchaseIntentReq purchaseIntentReq = new PurchaseIntentReq();
        purchaseIntentReq.setProductId(sku);
        purchaseIntentReq.setPriceType(IapClient.PriceType.IN_APP_SUBSCRIPTION);

        Iap.getIapClient(activity).createPurchaseIntent(purchaseIntentReq)
                .addOnSuccessListener(purchaseIntentResult -> {
                    try {
                        purchaseIntentResult.getStatus().startResolutionForResult(activity, REQUEST_CODE_BUY_HUAWEI);
                    } catch (IntentSender.SendIntentException e) {
                        huaweiPurchaseCallBacks.onHuaweiPurchaseError(e.getLocalizedMessage());
                        e.printStackTrace();
                    }
                })
                .addOnFailureListener(e -> huaweiPurchaseCallBacks.onHuaweiPurchaseError(e.getLocalizedMessage()));
    }

    @Override
    public boolean isVerifyExistenceService(@NonNull Context context) {
        int status = HuaweiApiAvailability.getInstance().isHuaweiMobileServicesAvailable(context);
        return status == ConnectionResult.SUCCESS;
    }

    @Override
    public void requestInventory(@NonNull RequestInventoryListener requestInventoryListener, @NonNull List<String> skuList) {
        ProductInfoReq infoReq = new ProductInfoReq();
        infoReq.setPriceType(IapClient.PriceType.IN_APP_SUBSCRIPTION);
        infoReq.setProductIds(skuList);

        Iap.getIapClient(activity).obtainProductInfo(infoReq)
                .addOnSuccessListener(productInfoResult -> {
                    skuDetailDataMap = new SkuDetailMapGenerator().generate(productInfoResult.getProductInfoList());
                    requestInventoryListener.onSuccessRequestInventory(skuDetailDataMap);
                })
                .addOnFailureListener(e -> requestInventoryListener.onErrorRequestInventory(e.getMessage()));
    }

    @Override
    public void requestPurchases(@NonNull RequestPurchasesListener requestPurchasesListener) {
        OwnedPurchasesReq ownedPurchasesReq = new OwnedPurchasesReq();
        ownedPurchasesReq.setPriceType(IapClient.PriceType.IN_APP_SUBSCRIPTION);

        Iap.getIapClient(activity).obtainOwnedPurchases(ownedPurchasesReq)
                .addOnSuccessListener(result -> {
                    purchaseDataMap = new PurchaseGenerator().generateMap(result.getInAppPurchaseDataList());
                    requestPurchasesListener.onSuccessRequestPurchases(purchaseDataMap);
                }).addOnFailureListener(e -> requestPurchasesListener.onErrorRequestPurchases(e.getLocalizedMessage()));
    }

    @Override
    public void setPurchaseCallBacks(@NonNull PurchaseCallBack callBack) {
        huaweiPurchaseCallBacks = (HuaweiPurchaseCallBacks) callBack;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        onResultPay(data, requestCode);
    }

    @Override
    public PurchaseData getPurchaseDataBySku(@NonNull String sku) {
        return purchaseDataMap.get(sku);
    }

    @Override
    public SkuDetailData getSkuDetailDataBySku(@NonNull String sku) {
        return skuDetailDataMap.get(sku);
    }
}
