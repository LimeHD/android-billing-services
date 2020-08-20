package tv.limehd.androidbillingmodule.service.strategy.huawei;

import android.app.Activity;
import android.content.Context;

import androidx.annotation.NonNull;

import com.huawei.hms.api.ConnectionResult;
import com.huawei.hms.api.HuaweiApiAvailability;
import com.huawei.hms.iap.Iap;
import com.huawei.hms.iap.IapClient;
import com.huawei.hms.iap.entity.OwnedPurchasesReq;

import java.util.List;

import tv.limehd.androidbillingmodule.interfaces.IPayServicesStrategy;
import tv.limehd.androidbillingmodule.interfaces.listeners.RequestInventoryListener;
import tv.limehd.androidbillingmodule.interfaces.listeners.RequestPurchasesListener;
import tv.limehd.androidbillingmodule.service.strategy.ServiceBaseStrategy;

public class ServiceHuaweiStrategy extends ServiceBaseStrategy implements IPayServicesStrategy {

    public ServiceHuaweiStrategy(@NonNull Activity activity) {
        super(activity);
    }

    @Override
    public void buySubscription(@NonNull String sku) {

    }

    //
//    public ServiceHuaweiStrategy(@NonNull Activity activity) {
//        super(activity);
//    }
//
//    @Override
//    public void buy() {
//
//    }
//
    @Override
    public boolean isVerifyExistenceService(@NonNull Context context) {
        int status = HuaweiApiAvailability.getInstance().isHuaweiMobileServicesAvailable(context);
        return status == ConnectionResult.SUCCESS;
    }

    @Override
    public void requestInventory(@NonNull RequestInventoryListener requestInventoryListener, @NonNull List<String> skuList) {

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

    }
}
