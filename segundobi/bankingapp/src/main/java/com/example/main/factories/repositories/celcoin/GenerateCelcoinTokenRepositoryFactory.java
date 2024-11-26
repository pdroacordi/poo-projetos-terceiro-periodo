package com.example.main.factories.repositories.celcoin;

import com.example.data.protocols.api.IGenerateCelcoinTokenRepository;
import com.example.infra.api.GenerateCelcoinTokenRepository;

public class GenerateCelcoinTokenRepositoryFactory {

    public static IGenerateCelcoinTokenRepository getGenerateCelcoinTokenRepository() {
        return new GenerateCelcoinTokenRepository();
    }
}
