package com.example.main.factories.usecases;

import com.example.data.protocols.api.IConsultCelcoinBillRepository;
import com.example.data.usecasesimplementation.ConsultBills;
import com.example.domain.usecases.IConsultBills;
import com.example.main.factories.repositories.celcoin.ConsultBillsRepositoryFactory;

public class ConsultBillsFactory {
    public static IConsultBills makeConsultBills(){
        IConsultCelcoinBillRepository consultCelcoinBillRepository = ConsultBillsRepositoryFactory.makeConsultBillsRepository();
        return new ConsultBills(consultCelcoinBillRepository);
    }
}
