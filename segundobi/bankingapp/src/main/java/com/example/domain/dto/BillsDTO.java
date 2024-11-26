package com.example.domain.dto;

import com.example.domain.entities.BarCode;

public class BillsDTO {

    private BarCode barCode;

    public BillsDTO(Builder builder){
        this.barCode = builder.barCode;
    }

    public BarCode getBarCode() {
        return barCode;
    }

    public void setBarCode(BarCode barCode) {
        this.barCode = barCode;
    }

    public static class Builder {
        private BarCode barCode;

        public Builder barCode(BarCode barCode) {
            this.barCode = barCode;
            return this;
        }

        public BillsDTO build(){
            return new BillsDTO(this);
        }
    }
}
