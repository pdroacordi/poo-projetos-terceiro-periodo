package com.example.data.usecasesimplementation;

import com.example.data.protocols.api.IGenerateCelcoinPixQRCodeRepository;
import com.example.domain.usecases.IGeneratePixQrCode;

public class GeneratePixQrCode implements IGeneratePixQrCode {

    private final IGenerateCelcoinPixQRCodeRepository generateCelcoinPixQRCodeRepository;

    public GeneratePixQrCode(IGenerateCelcoinPixQRCodeRepository generateCelcoinPixQRCodeRepository) {
        this.generateCelcoinPixQRCodeRepository = generateCelcoinPixQRCodeRepository;
    }

    @Override
    public String generatePixQrCode(double amount) {
        return this.generateCelcoinPixQRCodeRepository.getCelcoinPixQRCode(amount);
    }
}
