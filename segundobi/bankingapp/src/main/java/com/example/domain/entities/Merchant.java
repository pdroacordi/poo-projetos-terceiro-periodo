package com.example.domain.entities;

import com.google.gson.annotations.SerializedName;

public class Merchant {
    private String postalCode;
    private String city;
    @SerializedName("merchantCategoryCode")
    private String categoryCode;
    private String name;

    private Merchant(Builder builder) {
        this.postalCode = builder.postalCode;
        this.city = builder.city;
        this.categoryCode = builder.categoryCode;
        this.name = builder.name;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getCity() {
        return city;
    }

    public String getCategoryCode() {
        return categoryCode;
    }

    public String getName() {
        return name;
    }

    public static class Builder {
        private String postalCode;
        private String city;
        private String categoryCode;
        private String name;

        public Builder postalCode(String postalCode) {
            this.postalCode = postalCode;
            return this;
        }

        public Builder city(String city) {
            this.city = city;
            return this;
        }

        public Builder categoryCode(String categoryCode) {
            this.categoryCode = categoryCode;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Merchant build() {
            return new Merchant(this);
        }
    }
}
