package tv.limehd.androidbillingmodule.service.strategy.huawei;

import android.app.Activity;
import android.content.Context;

import androidx.annotation.NonNull;

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

public class ServiceHuaweiStrategy extends ServiceBaseStrategy implements IPayServicesStrategy {
    private final int AUTO_RENEWABLE_SUBSCRIPTION = 2;

    public ServiceHuaweiStrategy(@NonNull Activity activity) {
        super(activity);

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

    }

    @Override
    public void setEventCallBacks(Object callBacks) {

    }

//
//    @Override
//    public void setEventCallBacks(Object callBacks) {
//
//    }
//
//    private ProductInfoReq createProductInfoReq() {
//        ProductInfoReq productInfoReq = new ProductInfoReq();
//        productInfoReq.setPriceType(IapClient.PriceType.IN_APP_SUBSCRIPTION);
//        ArrayList<String> productIds = new ArrayList<>();
//        productIds.add("pack.id44.v2");
//        productIds.add("pack.id6.v4");
//        productInfoReq.setProductIds(productIds);
//        return productInfoReq;
//    }
//
//    private OwnedPurchasesReq createOwnedPurchasesReq() {
//        OwnedPurchasesReq ownedPurchasesReq = new OwnedPurchasesReq();
//        ownedPurchasesReq.setPriceType(IapClient.PriceType.IN_APP_SUBSCRIPTION);
//        return ownedPurchasesReq;
//    }
}
