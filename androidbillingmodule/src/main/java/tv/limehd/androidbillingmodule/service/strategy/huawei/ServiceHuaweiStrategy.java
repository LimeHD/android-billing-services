package tv.limehd.androidbillingmodule.service.strategy.huawei;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;

import com.huawei.hmf.tasks.OnSuccessListener;
import com.huawei.hms.api.ConnectionResult;
import com.huawei.hms.api.HuaweiApiAvailability;
import com.huawei.hms.iap.Iap;
import com.huawei.hms.iap.IapClient;
import com.huawei.hms.iap.entity.IsEnvReadyResult;
import com.huawei.hms.iap.entity.OwnedPurchasesReq;
import com.huawei.hmf.tasks.Task;
import com.huawei.hms.api.ConnectionResult;
import com.huawei.hms.api.HuaweiApiAvailability;
import com.huawei.hms.iap.Iap;
import com.huawei.hms.iap.entity.ProductInfoReq;
import com.huawei.hms.iap.entity.ProductInfoResult;

import java.util.List;

import tv.limehd.androidbillingmodule.interfaces.IPayServicesStrategy;
import tv.limehd.androidbillingmodule.interfaces.listeners.RequestInventoryListener;
import tv.limehd.androidbillingmodule.interfaces.listeners.RequestPurchasesListener;
import tv.limehd.androidbillingmodule.service.strategy.ServiceBaseStrategy;

public class ServiceHuaweiStrategy extends ServiceBaseStrategy implements IPayServicesStrategy, HuaweiPaymentCallBacks {
    private final int AUTO_RENEWABLE_SUBSCRIPTION = 2;
    private HuaweiCallBacks huaweiCallBacks;

    public ServiceHuaweiStrategy(@NonNull Activity activity) {
        super(activity);
        ((HuaweiPayActivity) activity).setHuaweiPaymentCallBacks(this);
        huaweiCallBacks = new DefaultHuaweiCallBacks().getHuaweiCallBacks();
        Iap.getIapClient(activity).isEnvReady()
                .addOnFailureListener(e -> huaweiCallBacks.onHuaweiSetupFinishError(e.getLocalizedMessage()))
                .addOnSuccessListener(isEnvReadyResult -> huaweiCallBacks.onHuaweiSetupFinishSuccess());
    }

    @Override
    public void onResultPay(Intent data, int requestCode) {

    }

    @Override
    public void buySubscription(@NonNull String sku) {

    }

    @Override
    public boolean isVerifyExistenceService(@NonNull Context context) {
        int status = HuaweiApiAvailability.getInstance().isHuaweiMobileServicesAvailable(context);
        return status == ConnectionResult.SUCCESS;
    }

    @Override
    public void requestInventory(@NonNull RequestInventoryListener requestInventoryListener, @NonNull List<String> skuList) {
        ProductInfoReq infoReq = new ProductInfoReq();
        infoReq.setPriceType(AUTO_RENEWABLE_SUBSCRIPTION);
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
                .addOnSuccessListener(result ->
                        requestPurchasesListener.onSuccessRequestPurchases(new PurchaseGenerator().generateMap(result.getInAppPurchaseDataList())))
                .addOnFailureListener(e -> requestPurchasesListener.onErrorRequestPurchases(e.getLocalizedMessage()));
    }

    @Override
    public void setEventCallBacks(Object callBacks) {
        this.huaweiCallBacks = (HuaweiCallBacks) callBacks;
    }
}
