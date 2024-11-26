package com.example.data.usecasesimplementation;

import com.example.data.protocols.api.IPayCelcoinBillRepository;
import com.example.domain.usecases.IPayCelcoinBill;

public class PayCelcoinBill implements IPayCelcoinBill {

    private final IPayCelcoinBillRepository repository;

    public PayCelcoinBill(IPayCelcoinBillRepository repository) {
        this.repository = repository;
    }

    @Override
    public String payCelcoinBill(String transactionId) {
        return this.repository.payBill(transactionId);
    }
}
