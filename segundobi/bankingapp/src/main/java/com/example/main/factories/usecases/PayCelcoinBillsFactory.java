package com.example.main.factories.usecases;

import com.example.data.protocols.api.IPayCelcoinBillRepository;
import com.example.data.usecasesimplementation.PayCelcoinBill;
import com.example.domain.usecases.IPayCelcoinBill;
import com.example.main.factories.repositories.celcoin.PayCelcoinBillsRepositoryFactory;

public class PayCelcoinBillsFactory {
    public static IPayCelcoinBill makePayCelcoinBill() {
        IPayCelcoinBillRepository repository = PayCelcoinBillsRepositoryFactory.makePayCelcoinBillRepository();
        return new PayCelcoinBill(repository);
    }
}
