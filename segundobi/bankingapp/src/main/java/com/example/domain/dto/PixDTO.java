package com.example.domain.dto;

import com.example.domain.entities.Merchant;

import java.util.List;

public class PixDTO {
    private String key;
    private double amount;
    private String transactionIdentification;
    private Merchant merchant;
    private List<String> tags;
    private String additionalInformation;
    private boolean withdrawal;
    private String withdrawalServiceProvider;

    private PixDTO(Builder builder) {
        this.key = builder.key;
        this.amount = builder.amount;
        this.transactionIdentification = builder.transactionIdentification;
        this.merchant = builder.merchant;
        this.tags = builder.tags;
        this.additionalInformation = builder.additionalInformation;
        this.withdrawal = builder.withdrawal;
        this.withdrawalServiceProvider = builder.withdrawalServiceProvider;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getTransactionIdentification() {
        return transactionIdentification;
    }

    public void setTransactionIdentification(String transactionIdentification) {
        this.transactionIdentification = transactionIdentification;
    }

    public Merchant getMerchant() {
        return merchant;
    }

    public void setMerchant(Merchant merchant) {
        this.merchant = merchant;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getAdditionalInformation() {
        return additionalInformation;
    }

    public void setAdditionalInformation(String additionalInformation) {
        this.additionalInformation = additionalInformation;
    }

    public boolean isWithdrawal() {
        return withdrawal;
    }

    public void setWithdrawal(boolean withdrawal) {
        this.withdrawal = withdrawal;
    }

    public String getWithdrawalServiceProvider() {
        return withdrawalServiceProvider;
    }

    public void setWithdrawalServiceProvider(String withdrawalServiceProvider) {
        this.withdrawalServiceProvider = withdrawalServiceProvider;
    }

    public static class Builder {
        private String key;
        private double amount;
        private String transactionIdentification;
        private Merchant merchant;
        private List<String> tags;
        private String additionalInformation;
        private boolean withdrawal;
        private String withdrawalServiceProvider;

        public Builder key(String key) {
            this.key = key;
            return this;
        }

        public Builder amount(double amount) {
            this.amount = amount;
            return this;
        }

        public Builder transactionIdentification(String transactionIdentification) {
            this.transactionIdentification = transactionIdentification;
            return this;
        }

        public Builder merchant(Merchant merchant) {
            this.merchant = merchant;
            return this;
        }

        public Builder tags(List<String> tags) {
            this.tags = tags;
            return this;
        }

        public Builder additionalInformation(String additionalInformation) {
            this.additionalInformation = additionalInformation;
            return this;
        }

        public Builder withdrawal(boolean withdrawal) {
            this.withdrawal = withdrawal;
            return this;
        }

        public Builder withdrawalServiceProvider(String withdrawalServiceProvider) {
            this.withdrawalServiceProvider = withdrawalServiceProvider;
            return this;
        }

        public PixDTO build() {
            return new PixDTO(this);
        }
    }
}
