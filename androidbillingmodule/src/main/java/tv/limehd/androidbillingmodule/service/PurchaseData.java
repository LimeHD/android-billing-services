package tv.limehd.androidbillingmodule.service;

public class PurchaseData {
    private String orderId;
    private String purchaseToken;
    private long purchaseTime;
    private int purchaseState;
    private boolean isAcknowledged;
    private String packageName;
    private String originJson;

    public PurchaseData(Builder builder){
        orderId = builder.orderId;
        purchaseToken = builder.purchaseToken;
        purchaseTime = builder.purchaseTime;
        purchaseState = builder.purchaseState;
        isAcknowledged = builder.isAcknowledged;
        packageName = builder.packageName;
        originJson = builder.originJson;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getPurchaseToken() {
        return purchaseToken;
    }

    public long getPurchaseTime() {
        return purchaseTime;
    }

    public int getPurchaseState() {
        return purchaseState;
    }

    public boolean isAcknowledged() {
        return isAcknowledged;
    }

    public String getPackageName() {
        return packageName;
    }

    public String getOriginJson() {
        return originJson;
    }

    public static class Builder {
        private String orderId;
        private String purchaseToken;
        private long purchaseTime;
        private int purchaseState;
        private boolean isAcknowledged;
        private String packageName;
        private String originJson;

        public Builder setOrderId(String orderId) {
            this.orderId = orderId;
            return this;
        }

        public Builder setPurchaseToken(String purchaseToken) {
            this.purchaseToken = purchaseToken;
            return this;
        }

        public Builder setPurchaseTime(long purchaseTime) {
            this.purchaseTime = purchaseTime;
            return this;
        }

        public Builder setPurchaseState(int purchaseState) {
            this.purchaseState = purchaseState;
            return this;
        }

        public Builder setAcknowledged(boolean acknowledged) {
            isAcknowledged = acknowledged;
            return this;
        }

        public Builder setPackageName(String packageName) {
            this.packageName = packageName;
            return this;
        }

        public Builder setOriginJson(String originJson) {
            this.originJson = originJson;
            return this;
        }

        public PurchaseData build(){
            return new PurchaseData(this);
        }
    }
}
