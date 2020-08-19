package tv.limehd.androidbillingmodule.service.strategy.huawei;

import android.app.Activity;
import android.content.Context;

import androidx.annotation.NonNull;

import com.huawei.hmf.tasks.OnFailureListener;
import com.huawei.hmf.tasks.OnSuccessListener;
import com.huawei.hmf.tasks.Task;
import com.huawei.hms.api.ConnectionResult;
import com.huawei.hms.api.HuaweiApiAvailability;
import com.huawei.hms.iap.Iap;
import com.huawei.hms.iap.IapApiException;
import com.huawei.hms.iap.IapClient;
import com.huawei.hms.iap.entity.InAppPurchaseData;
import com.huawei.hms.iap.entity.OwnedPurchasesReq;
import com.huawei.hms.iap.entity.OwnedPurchasesResult;
import com.huawei.hms.iap.entity.ProductInfoReq;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import tv.limehd.androidbillingmodule.interfaces.IPayServicesStrategy;
import tv.limehd.androidbillingmodule.interfaces.listeners.RequestInventoryListener;
import tv.limehd.androidbillingmodule.service.strategy.ServiceBaseStrategy;

public class ServiceHuaweiStrategy extends ServiceBaseStrategy implements IPayServicesStrategy {


    public ServiceHuaweiStrategy(@NonNull Activity activity) {
        super(activity);
    }

    @Override
    public void buy() {

    }

    @Override
    public boolean isVerifyExistenceService(@NonNull Context context) {
        int status = HuaweiApiAvailability.getInstance().isHuaweiMobileServicesAvailable(context);
        return status == ConnectionResult.SUCCESS;
    }

    @Override
    public void requestInventory(@NonNull final RequestInventoryListener requestInventoryListener, List<String> skuList) {
        if(activity == null) {
//            requestInventoryListener.onResult(new ArrayList<String>());
            return;
        }
        IapClient iapClient = Iap.getIapClient(activity);
        final List<String> productsId = new ArrayList<>();
        Task<OwnedPurchasesResult> task = iapClient.obtainOwnedPurchases(createOwnedPurchasesReq());
        task.addOnSuccessListener(new OnSuccessListener<OwnedPurchasesResult>() {
            @Override
            public void onSuccess(OwnedPurchasesResult result) {
                for (int i = 0; i < result.getInAppPurchaseDataList().size(); i++) {
                    String inAppPurchaseData = result.getInAppPurchaseDataList().get(i);
                    try {
                        InAppPurchaseData inAppPurchaseDataBean = new InAppPurchaseData(inAppPurchaseData);
                        int purchaseState = inAppPurchaseDataBean.getPurchaseState();
                        if (purchaseState == 1) {
                            productsId.add(inAppPurchaseDataBean.getProductId());
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
//                requestInventoryListener.onResult(productsId);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                if (e instanceof IapApiException) {
                    IapApiException apiException = (IapApiException) e;
                    int returnCode = apiException.getStatusCode();
                }
                e.printStackTrace();
            }
        });
    }

    @Override
    public void setEventCallBacks(Object callBacks) {

    }

    private ProductInfoReq createProductInfoReq() {
        ProductInfoReq productInfoReq = new ProductInfoReq();
        productInfoReq.setPriceType(IapClient.PriceType.IN_APP_SUBSCRIPTION);
        ArrayList<String> productIds = new ArrayList<>();
        productIds.add("pack.id44.v2");
        productIds.add("pack.id6.v4");
        productInfoReq.setProductIds(productIds);
        return productInfoReq;
    }

    private OwnedPurchasesReq createOwnedPurchasesReq() {
        OwnedPurchasesReq ownedPurchasesReq = new OwnedPurchasesReq();
        ownedPurchasesReq.setPriceType(IapClient.PriceType.IN_APP_SUBSCRIPTION);
        return ownedPurchasesReq;
    }
}
