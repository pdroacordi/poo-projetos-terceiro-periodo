package com.example.main.factories.repositories.celcoin;

import com.example.data.protocols.api.IGenerateCelcoinTokenRepository;
import com.example.data.protocols.api.IPayCelcoinBillRepository;
import com.example.infra.api.PayCelcoinBillRepository;

public class PayCelcoinBillsRepositoryFactory {
    public static IPayCelcoinBillRepository makePayCelcoinBillRepository() {
        IGenerateCelcoinTokenRepository tokenRepository = GenerateCelcoinTokenRepositoryFactory.getGenerateCelcoinTokenRepository();
        return new PayCelcoinBillRepository(tokenRepository);
    }
}
