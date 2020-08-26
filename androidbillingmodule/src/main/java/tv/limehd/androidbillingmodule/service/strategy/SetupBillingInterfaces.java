package tv.limehd.androidbillingmodule.service.strategy;

import java.util.HashMap;
import java.util.Set;

import tv.limehd.androidbillingmodule.service.EnumPaymentService;
import tv.limehd.androidbillingmodule.service.strategy.google.callBacks.GoogleSetupCallBacks;
import tv.limehd.androidbillingmodule.service.strategy.huawei.callBacks.HuaweiSetupCallBacks;

public class SetupBillingInterfaces {
    private HashMap<EnumPaymentService, ServiceSetupCallBack> setupCallBackHashMap;

    public SetupBillingInterfaces() {
        setupCallBackHashMap = new HashMap<>();
    }

    public static class Builder {
        private SetupBillingInterfaces setupBillingInterfaces;

        public Builder() {
            setupBillingInterfaces = new SetupBillingInterfaces();
        }

        public Builder setGoogleSetupCallBacks(GoogleSetupCallBacks googleSetupCallBacks) {
            setupBillingInterfaces.setupCallBackHashMap.put(EnumPaymentService.google, googleSetupCallBacks);
            return this;
        }

        public Builder setHuaweiSetupCallBacks(HuaweiSetupCallBacks huaweiSetupCallBacks) {
            setupBillingInterfaces.setupCallBackHashMap.put(EnumPaymentService.huawei, huaweiSetupCallBacks);
            return this;
        }

        public SetupBillingInterfaces build() {
            return setupBillingInterfaces;
        }
    }

    public <T extends ServiceSetupCallBack> ServiceSetupCallBack getServiceSetupCallback(EnumPaymentService enumPaymentService) {
        return setupCallBackHashMap.get(enumPaymentService);
    }

    public Set<EnumPaymentService> keySet(){
        return setupCallBackHashMap.keySet();
    }
}
