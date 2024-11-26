package com.example.domain.entities;

public class BarCode {
    int type;
    String digitable;

    public BarCode(Builder builder) {
        this.type = builder.type;
        this.digitable = builder.digitable;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDigitable() {
        return digitable;
    }

    public void setDigitable(String digitable) {
        this.digitable = digitable;
    }

    public static class Builder {
        int type;
        String digitable;

        public BarCode build() {
            return new BarCode(this);
        }

        public Builder type(int type) {
            this.type = type;
            return this;
        }

        public Builder digitable(String digitable) {
            this.digitable = digitable;
            return this;
        }
    }
}
