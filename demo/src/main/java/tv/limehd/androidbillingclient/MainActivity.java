package tv.limehd.androidbillingclient;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;

import tv.limehd.androidbillingmodule.LimeBillingServices;
import tv.limehd.androidbillingmodule.interfaces.listeners.ExistenceServicesListener;
import tv.limehd.androidbillingmodule.servicesEnum.EnumPaymentService;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.e("TEST", "Verify");
        LimeBillingServices limeBillingServices = new LimeBillingServices()
                .init();


        limeBillingServices.verifyExistenceAllService(new ExistenceServicesListener() {
            @Override
            public void callBackExistenceServices(HashMap<EnumPaymentService, Boolean> existing) {
                for (EnumPaymentService service: EnumPaymentService.values()) {
                    Log.e("TEST", "service: " + service.name() + " " + existing.get(service));
                }
                ((TextView)findViewById(R.id.googleSdk)).setText("service: " + EnumPaymentService.google.name() + " " + existing.get(EnumPaymentService.google));
                ((TextView)findViewById(R.id.huaweiSdk)).setText("service: " + EnumPaymentService.huawei.name() + " " + existing.get(EnumPaymentService.huawei));
            }
        });
    }
}