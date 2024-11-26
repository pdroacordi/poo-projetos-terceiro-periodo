package com.example.main.factories.repositories.celcoin;

import com.example.data.protocols.api.IConsultCelcoinBillRepository;
import com.example.data.protocols.api.IGenerateCelcoinTokenRepository;
import com.example.infra.api.ConsultCelcoinBillRepository;

public class ConsultBillsRepositoryFactory {
    public static IConsultCelcoinBillRepository makeConsultBillsRepository() {
        IGenerateCelcoinTokenRepository tokenRepository = GenerateCelcoinTokenRepositoryFactory.getGenerateCelcoinTokenRepository();
        return new ConsultCelcoinBillRepository(tokenRepository);
    }
}
