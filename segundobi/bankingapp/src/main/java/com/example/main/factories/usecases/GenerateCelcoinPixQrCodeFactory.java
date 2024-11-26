package com.example.main.factories.usecases;

import com.example.data.protocols.api.IGenerateCelcoinPixQRCodeRepository;
import com.example.data.protocols.api.IGenerateCelcoinTokenRepository;
import com.example.data.usecasesimplementation.GeneratePixQrCode;
import com.example.domain.usecases.IGeneratePixQrCode;
import com.example.main.factories.repositories.celcoin.GenerateCelcoinPixQrCodeRepositoryFactory;
import com.example.main.factories.repositories.celcoin.GenerateCelcoinTokenRepositoryFactory;

public class GenerateCelcoinPixQrCodeFactory {
    public static IGeneratePixQrCode makeGeneratePixQrCode() {
        IGenerateCelcoinTokenRepository generateCelcoinTokenRepository = GenerateCelcoinTokenRepositoryFactory.getGenerateCelcoinTokenRepository();
        IGenerateCelcoinPixQRCodeRepository generateCelcoinPixQRCodeRepository = GenerateCelcoinPixQrCodeRepositoryFactory.getGenerateCelcoinPixQrCodeRepository(generateCelcoinTokenRepository);
        return new GeneratePixQrCode(generateCelcoinPixQRCodeRepository);
    }
}
