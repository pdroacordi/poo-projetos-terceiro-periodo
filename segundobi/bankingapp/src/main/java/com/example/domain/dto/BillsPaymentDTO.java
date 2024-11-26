package com.example.domain.dto;

public class BillsPaymentDTO {
    private int externalNSU;
    private String externalTerminal;

    public BillsPaymentDTO(Builder builder) {
        this.externalNSU = builder.externalNSU;
        this.externalTerminal = builder.externalTerminal;
    }

    public int getExternalNSU() {
        return externalNSU;
    }

    public void setExternalNSU(int externalNSU) {
        this.externalNSU = externalNSU;
    }

    public String getExternalTerminal() {
        return externalTerminal;
    }

    public void setExternalTerminal(String externalTerminal) {
        this.externalTerminal = externalTerminal;
    }

    public static class Builder{
        private int externalNSU;
        private String externalTerminal;

        public Builder externalNSU(int externalNSU) {
            this.externalNSU = externalNSU;
            return this;
        }

        public Builder externalTerminal(String externalTerminal) {
            this.externalTerminal = externalTerminal;
            return this;
        }
        public BillsPaymentDTO build(){
            return new BillsPaymentDTO(this);
        }
    }
}
