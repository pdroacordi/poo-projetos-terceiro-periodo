package com.example.main.factories.repositories.celcoin;

import com.example.data.protocols.api.IGenerateCelcoinPixQRCodeRepository;
import com.example.data.protocols.api.IGenerateCelcoinTokenRepository;
import com.example.infra.api.GeneratePixQrCodeRepository;

public class GenerateCelcoinPixQrCodeRepositoryFactory {

    public static IGenerateCelcoinPixQRCodeRepository getGenerateCelcoinPixQrCodeRepository(IGenerateCelcoinTokenRepository generateCelcoinTokenRepository) {
        return new GeneratePixQrCodeRepository(generateCelcoinTokenRepository);
    }
}
