package tv.limehd.androidbillingmodule.service.strategy.google.generators;

import com.android.billingclient.api.BillingClient;
import com.huawei.hms.iap.entity.OrderStatusCode;

import tv.limehd.androidbillingmodule.service.EnumPurchaseState;

public class GoogleEnumPurchaseGenerator {
    public EnumPurchaseState generate(int code) {
        EnumPurchaseState enumPurchaseState;
        switch (code) {
            case BillingClient.BillingResponseCode.ERROR:
                enumPurchaseState = EnumPurchaseState.FAILED;
                break;
            case BillingClient.BillingResponseCode.USER_CANCELED:
                enumPurchaseState = EnumPurchaseState.USER_CANCEL;
                break;
            case BillingClient.BillingResponseCode.DEVELOPER_ERROR:
                enumPurchaseState = EnumPurchaseState.DEVELOPER_ERROR;
                break;
            case BillingClient.BillingResponseCode.SERVICE_TIMEOUT:
                enumPurchaseState = EnumPurchaseState.NETWORK_ERROR;
                break;
            case BillingClient.BillingResponseCode.FEATURE_NOT_SUPPORTED:
                enumPurchaseState = EnumPurchaseState.GOOGLE_FEATURE_NOT_SUPPORTED;
                break;
            case BillingClient.BillingResponseCode.SERVICE_DISCONNECTED:
                enumPurchaseState = EnumPurchaseState.GOOGLE_SERVICE_DISCONNECTED;
                break;
            case BillingClient.BillingResponseCode.ITEM_ALREADY_OWNED:
                enumPurchaseState = EnumPurchaseState.ALREADY_OWNED;
                break;
            case BillingClient.BillingResponseCode.ITEM_NOT_OWNED:
                enumPurchaseState = EnumPurchaseState.NOT_OWNED;
                break;
            case BillingClient.BillingResponseCode.ITEM_UNAVAILABLE:
                enumPurchaseState = EnumPurchaseState.ITEM_NOT_SUPPORT;
                break;
            case BillingClient.BillingResponseCode.BILLING_UNAVAILABLE:
                enumPurchaseState = EnumPurchaseState.GOOGLE_BILLING_UNAVAILABLE;
                break;
            default:
                enumPurchaseState = EnumPurchaseState.UNKNOWN;
        }
        return enumPurchaseState;
    }
}
