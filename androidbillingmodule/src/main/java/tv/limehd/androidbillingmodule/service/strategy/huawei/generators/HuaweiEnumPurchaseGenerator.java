package tv.limehd.androidbillingmodule.service.strategy.huawei.generators;

import com.huawei.hms.iap.entity.OrderStatusCode;

import tv.limehd.androidbillingmodule.service.EnumPurchaseState;

public class HuaweiEnumPurchaseGenerator {
    public EnumPurchaseState generate(int code) {
        EnumPurchaseState enumPurchaseState;
        switch (code) {
            case OrderStatusCode.ORDER_STATE_FAILED:
                enumPurchaseState = EnumPurchaseState.FAILED;
                break;
            case OrderStatusCode.ORDER_STATE_CANCEL:
                enumPurchaseState = EnumPurchaseState.USER_CANCEL;
                break;
            case OrderStatusCode.ORDER_STATE_PARAM_ERROR:
                enumPurchaseState = EnumPurchaseState.DEVELOPER_ERROR;
                break;
            case OrderStatusCode.ORDER_STATE_NET_ERROR:
                enumPurchaseState = EnumPurchaseState.NETWORK_ERROR;
                break;
            case OrderStatusCode.ORDER_VR_UNINSTALL_ERROR:
                enumPurchaseState = EnumPurchaseState.HUAWEI_ORDER_VR_UNINSTALL_ERROR;
                break;
            case OrderStatusCode.ORDER_HWID_NOT_LOGIN:
                enumPurchaseState = EnumPurchaseState.HUAWEI_ORDER_PRODUCT_NOT_LOGIN;
                break;
            case OrderStatusCode.ORDER_PRODUCT_OWNED:
                enumPurchaseState = EnumPurchaseState.ALREADY_OWNED;
                break;
            case OrderStatusCode.ORDER_PRODUCT_NOT_OWNED:
                enumPurchaseState = EnumPurchaseState.NOT_OWNED;
                break;
            case OrderStatusCode.ORDER_ACCOUNT_AREA_NOT_SUPPORTED:
                enumPurchaseState = EnumPurchaseState.ITEM_NOT_SUPPORT;
                break;
            case OrderStatusCode.ORDER_HIGH_RISK_OPERATIONS:
                enumPurchaseState = EnumPurchaseState.HUAWEI_ORDER_HIGH_RISK_OPERATIONS;
                break;
            default:
                enumPurchaseState = EnumPurchaseState.UNKNOWN;
        }
        return enumPurchaseState;
    }
}
