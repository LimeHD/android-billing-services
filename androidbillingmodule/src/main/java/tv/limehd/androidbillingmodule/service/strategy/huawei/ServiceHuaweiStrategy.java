package tv.limehd.androidbillingmodule.service.strategy.huawei;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;

import androidx.annotation.NonNull;

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
import tv.limehd.androidbillingmodule.service.strategy.ServiceBaseStrategy;
import tv.limehd.androidbillingmodule.service.strategy.huawei.callBacks.HuaweiCallBacks;
import tv.limehd.androidbillingmodule.service.strategy.huawei.callBacks.HuaweiPaymentCallBacks;
import tv.limehd.androidbillingmodule.service.strategy.huawei.generators.PurchaseGenerator;
import tv.limehd.androidbillingmodule.service.strategy.huawei.generators.SkuDetailMapGenerator;

public class ServiceHuaweiStrategy extends ServiceBaseStrategy implements IPayServicesStrategy, HuaweiPaymentCallBacks {
    private HuaweiCallBacks huaweiCallBacks;
    private int REQ_CODE_BUY = 543;
    private Map<String, PurchaseData> purchaseDataMap;

    public ServiceHuaweiStrategy(@NonNull Activity activity) {
        super(activity);
        ((HuaweiPayActivity) activity).setHuaweiPaymentCallBacks(this);
        huaweiCallBacks = new DefaultHuaweiCallBacks().getHuaweiCallBacks();
        purchaseDataMap = new HashMap<>();
        Iap.getIapClient(activity).isEnvReady()
                .addOnFailureListener(e -> huaweiCallBacks.onHuaweiSetupFinishError(e.getLocalizedMessage()))
                .addOnSuccessListener(isEnvReadyResult -> huaweiCallBacks.onHuaweiSetupFinishSuccess());
    }

    @Override
    public void onResultPay(Intent data, int requestCode) {
        if (requestCode == REQ_CODE_BUY) {
            PurchaseResultInfo purchaseResultInfo = Iap.getIapClient(activity).parsePurchaseResultInfoFromIntent(data);
            switch (purchaseResultInfo.getReturnCode()) {
                case OrderStatusCode.ORDER_STATE_SUCCESS:
                    PurchaseGenerator purchaseGenerator = new PurchaseGenerator();
                    PurchaseData purchaseData = purchaseGenerator.generatePurchaseData(purchaseResultInfo.getInAppPurchaseData());
                    if (purchaseData != null) {
                        purchaseDataMap.put(purchaseData.getProductId(), purchaseData);
                    }
                    huaweiCallBacks.onHuaweiPurchaseSuccess(purchaseData, purchaseDataMap);
                    break;
                default:
                    huaweiCallBacks.onHuaweiPurchaseError("code:" + purchaseResultInfo.getReturnCode() + "message:" + purchaseResultInfo.getErrMsg());
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
                        purchaseIntentResult.getStatus().startResolutionForResult(activity, REQ_CODE_BUY);
                    } catch (IntentSender.SendIntentException e) {
                        huaweiCallBacks.onHuaweiPurchaseError(e.getLocalizedMessage());
                        e.printStackTrace();
                    }
                })
                .addOnFailureListener(e -> huaweiCallBacks.onHuaweiPurchaseError(e.getLocalizedMessage()));
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
                .addOnSuccessListener(productInfoResult ->
                        requestInventoryListener.onSuccessRequestInventory(new SkuDetailMapGenerator().generate(productInfoResult.getProductInfoList())))
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
    public void setEventCallBacks(Object callBacks) {
        this.huaweiCallBacks = (HuaweiCallBacks) callBacks;
    }
}
