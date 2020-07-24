package tv.limehd.androidbillingclient;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;

import tv.limehd.androidbillingmodule.LimeBillingServices;
import tv.limehd.androidbillingmodule.interfaces.listeners.ExistenceServicesListener;
import tv.limehd.androidbillingmodule.servicesEnum.EnumPaymentService;

public class MainActivity extends AppCompatActivity {

    private Button buttonGoogleSDK;
    private Button buttonHuaweiSDK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonGoogleSDK = findViewById(R.id.button_google_sdk);
        buttonHuaweiSDK = findViewById(R.id.button_huawei_sdk);


        LimeBillingServices limeBillingServices = new LimeBillingServices(getApplicationContext())
                .init();


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
}