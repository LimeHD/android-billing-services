package tv.limehd.androidbillingmodule.service;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class SkuDetailData {
    private String productId;
    private String price;
    private long priceAmountMicros;
    private String priceCurrencyCode;
    private String subscriptionPeriod;
    private String title;
    private String description;
    private String originJSON;
    private String promoPeriod;

    public SkuDetailData(@NonNull Builder builder) {
        productId = builder.productId;
        price = builder.price;
        priceAmountMicros = builder.priceAmountMicros;
        priceCurrencyCode = builder.priceCurrencyCode;
        subscriptionPeriod = builder.subscriptionPeriod;
        title = builder.title;
        description = builder.description;
        originJSON = builder.originJSON;
    }

    public String getProductId() {
        return productId;
    }

    public String getPrice() {
        return price;
    }

    public long getPriceAmountMicros() {
        return priceAmountMicros;
    }

    public String getPriceCurrencyCode() {
        return priceCurrencyCode;
    }

    public String getSubscriptionPeriod() {
        return subscriptionPeriod;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getOriginJSON() {
        return originJSON;
    }

    @Nullable
    public String getPromoPeriod() {
        return promoPeriod;
    }

    public static class Builder {
        private String productId;
        private String price;
        private long priceAmountMicros;
        private String priceCurrencyCode;
        private String subscriptionPeriod;
        private String title;
        private String description;
        private String originJSON;
        private String freeTrialPeriod;

        public Builder setProductId(String productId) {
            this.productId = productId;
            return this;
        }

        public Builder setPrice(String price) {
            this.price = price;
            return this;
        }

        public Builder setPriceAmountMicros(long priceAmountMicros) {
            this.priceAmountMicros = priceAmountMicros;
            return this;
        }

        public Builder setPriceCurrencyCode(String priceCurrencyCode) {
            this.priceCurrencyCode = priceCurrencyCode;
            return this;
        }

        public Builder setSubscriptionPeriod(String subscriptionPeriod) {
            this.subscriptionPeriod = subscriptionPeriod;
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder setOriginJSON(String originJSON) {
            this.originJSON = originJSON;
            return this;
        }

        public Builder setFreeTrialPeriod(String freeTrialPeriod) {
            this.freeTrialPeriod = freeTrialPeriod;
            return this;
        }

        public SkuDetailData build() {
            return new SkuDetailData(this);
        }
    }
}
