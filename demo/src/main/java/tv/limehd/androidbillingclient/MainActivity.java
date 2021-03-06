package tv.limehd.androidbillingclient;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import tv.limehd.androidbillingmodule.LimeBillingServices;
import tv.limehd.androidbillingmodule.interfaces.listeners.ExistenceServiceListener;
import tv.limehd.androidbillingmodule.interfaces.listeners.ExistenceServicesListener;
import tv.limehd.androidbillingmodule.interfaces.listeners.RequestInventoryListener;
import tv.limehd.androidbillingmodule.interfaces.listeners.RequestPurchasesListener;
import tv.limehd.androidbillingmodule.service.EnumPaymentService;
import tv.limehd.androidbillingmodule.service.PurchaseData;
import tv.limehd.androidbillingmodule.service.SkuDetailData;
import tv.limehd.androidbillingmodule.service.strategy.ServiceSetupCallBack;
import tv.limehd.androidbillingmodule.service.strategy.google.callBacks.GooglePurchaseCallBacks;
import tv.limehd.androidbillingmodule.service.strategy.google.callBacks.GoogleSetupCallBacks;
import tv.limehd.androidbillingmodule.service.strategy.huawei.HuaweiPayActivity;
import tv.limehd.androidbillingmodule.service.strategy.huawei.callBacks.HuaweiPurchaseCallBacks;
import tv.limehd.androidbillingmodule.service.strategy.huawei.callBacks.HuaweiSetupCallBacks;

public class MainActivity extends HuaweiPayActivity {

    private Button buttonGoogleSDK;
    private Button buttonHuaweiSDK;
    private LimeBillingServices limeBillingServices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializationView();
        initializationLimeBillingServices();
        verifyAllService();
        setButtonListener();
    }

    private void initializationView() {
        buttonGoogleSDK = findViewById(R.id.button_google_sdk);
        buttonHuaweiSDK = findViewById(R.id.button_huawei_sdk);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        limeBillingServices.onActivityResult(requestCode, resultCode, data);
    }

    private void initializationLimeBillingServices() {
        limeBillingServices = new LimeBillingServices(this);
        limeBillingServices.setPurchaseCallBack(EnumPaymentService.google, new GooglePurchaseCallBacks() {
            @Override
            public void onAcknowledgePurchaseStart() {

            }

            @Override
            public void onPurchaseAcknowledgeSuccess(PurchaseData purchaseData, Map<String, PurchaseData> purchaseDataMap) {

            }

            @Override
            public void onAcknowledgePurchaseError(String error) {

            }

            @Override
            public void onPurchaseUpdateError(String message) {

            }
        });
        limeBillingServices.setPurchaseCallBack(EnumPaymentService.huawei, new HuaweiPurchaseCallBacks() {
            @Override
            public void onHuaweiPurchaseSuccess(@Nullable PurchaseData purchaseData, Map<String, PurchaseData> map) {

            }

            @Override
            public void onHuaweiPurchaseError(@Nullable String message) {

            }
        });
        HashMap<EnumPaymentService, ServiceSetupCallBack> serviceServiceSetupCallBackHashMap = new HashMap<>();
        serviceServiceSetupCallBackHashMap.put(EnumPaymentService.huawei, new HuaweiSetupCallBacks() {
            @Override
            public void onHuaweiSetupFinishSuccess() {

            }

            @Override
            public void onHuaweiSetupFinishError(@Nullable String message) {

            }
        });
        serviceServiceSetupCallBackHashMap.put(EnumPaymentService.google, new GoogleSetupCallBacks() {
            @Override
            public void onBillingSetupFinishedSuccess() {

            }

            @Override
            public void onBillingSetupFinishedError(String message) {

            }

            @Override
            public void onBillingServiceDisconnected() {

            }
        });

        limeBillingServices.getControllerInitial().initServices(serviceServiceSetupCallBackHashMap);
    }

    private void verifyAllService() {
        limeBillingServices.getControllerVerify().verifyAllServices(new ExistenceServicesListener() {
            @Override
            public void callBackExistenceServices(HashMap<EnumPaymentService, Boolean> existing) {
                buttonGoogleSDK.setEnabled(existing.get(EnumPaymentService.google));
                buttonGoogleSDK.setText("service: " + EnumPaymentService.google.name());

                buttonHuaweiSDK.setEnabled(existing.get(EnumPaymentService.huawei));
                buttonHuaweiSDK.setText("service: " + EnumPaymentService.huawei.name());
            }
        });
    }


    private void verifyService(EnumPaymentService paymentService) {
        limeBillingServices.getControllerVerify().verifyService(paymentService, new ExistenceServiceListener() {
            @Override
            public void callBackExistenceService(EnumPaymentService paymentService, boolean existing) {

            }
        });
    }

    private void setButtonListener() {
        buttonGoogleSDK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestDataAboutSubscriptions(EnumPaymentService.google);
                requestSubscriptions();
            }
        });

        buttonHuaweiSDK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void requestDataAboutSubscriptions(EnumPaymentService paymentService) {
        ArrayList<String> productIds = new ArrayList<>();
        productIds.add("pack.id44.v2");//TODO надо передавать список подписок
        productIds.add("pack.id6.v4");

        limeBillingServices.requestInventoryFrom(paymentService, productIds, new RequestInventoryListener() {
            @Override
            public void onSuccessRequestInventory(@NonNull Map<String, SkuDetailData> skuDetailsMap) {

            }

            @Override
            public void onErrorRequestInventory(String error) {

            }
        });
    }

    private void requestSubscriptions() {
        limeBillingServices.requestPurchases(EnumPaymentService.google, new RequestPurchasesListener() {
            @Override
            public void onSuccessRequestPurchases(@NonNull Map<String, PurchaseData> purchaseDetailsMap) {

            }

            @Override
            public void onErrorRequestPurchases(String message) {

            }
        });
    }
}