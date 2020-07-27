package tv.limehd.androidbillingclient;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;

import tv.limehd.androidbillingmodule.LimeBillingServices;
import tv.limehd.androidbillingmodule.interfaces.listeners.ExistenceServicesListener;
import tv.limehd.androidbillingmodule.servicesEnum.EnumPaymentService;

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
        limeBillingServices = new LimeBillingServices(getApplicationContext());
    }

    private void verifyAllService() {
        limeBillingServices.verifyExistenceAllService(new ExistenceServicesListener() {
            @Override
            public void callBackExistenceServices(HashMap<EnumPaymentService, Boolean> existing) {

                for (EnumPaymentService service : EnumPaymentService.values()) {

                }

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

            }
        });

        buttonHuaweiSDK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void requestDataAboutSubscriptions(EnumPaymentService paymentService) {

    }

}