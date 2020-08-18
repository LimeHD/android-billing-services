package tv.limehd.androidbillingclient;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import tv.limehd.androidbillingmodule.LimeBillingServices;
import tv.limehd.androidbillingmodule.interfaces.listeners.ExistenceServicesListener;
import tv.limehd.androidbillingmodule.interfaces.listeners.RequestInventoryListener;
import tv.limehd.androidbillingmodule.service.EnumPaymentService;
import tv.limehd.androidbillingmodule.service.strategy.google.GoogleCallBacks;

public class MainActivity extends AppCompatActivity {

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

    private void initializationLimeBillingServices() {
        limeBillingServices = new LimeBillingServices(this);
        setGoogleCallBacks(EnumPaymentService.google);
        limeBillingServices.init();
    }

    private void verifyAllService() {
        limeBillingServices.verifyExistenceAllService(new ExistenceServicesListener() {
            @Override
            public void callBackExistenceServices(HashMap<EnumPaymentService, Boolean> existing) {

                buttonGoogleSDK.setEnabled(existing.get(EnumPaymentService.google));
                buttonGoogleSDK.setText("service: " + EnumPaymentService.google.name());

                buttonHuaweiSDK.setEnabled(existing.get(EnumPaymentService.huawei));
                buttonHuaweiSDK.setText("service: " + EnumPaymentService.huawei.name());
            }
        });
    }


    private void setButtonListener() {
        buttonGoogleSDK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestDataAboutSubscriptions(EnumPaymentService.google);
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

        limeBillingServices.tryRequestInventoryFrom(paymentService, productIds, new RequestInventoryListener() {
            @Override
            public void onResult(@NonNull Map<String, Object> skuDetailsMap, @NonNull Map<String, Object> purchaseDetailsMap) {

            }

            @Override
            public void onErrorRequestInventory(String error) {

            }
        });
    }

    private void setGoogleCallBacks(EnumPaymentService paymentService) {
        limeBillingServices.setEventCallBacks(paymentService, new GoogleCallBacks() {
            @Override
            public void onStartAcknowledgePurchase() {
            }

            @Override
            public void onPurchaseAcknowledgeSuccess() {
            }

            @Override
            public void onErrorAcknowledgePurchase(com.android.billingclient.api.Purchase purchase) {
            }

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
    }

}