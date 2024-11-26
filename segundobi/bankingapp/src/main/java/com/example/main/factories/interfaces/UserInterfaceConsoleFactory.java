package com.example.main.factories.interfaces;

import com.example.data.protocols.api.IGenerateCelcoinPixQRCodeRepository;
import com.example.data.protocols.api.IGenerateCelcoinTokenRepository;
import com.example.domain.usecases.*;
import com.example.infra.api.GenerateCelcoinTokenRepository;
import com.example.main.factories.repositories.celcoin.GenerateCelcoinPixQrCodeRepositoryFactory;
import com.example.main.factories.repositories.celcoin.GenerateCelcoinTokenRepositoryFactory;
import com.example.main.factories.usecases.*;
import com.example.presentation.console.UserInterfaceConsoleImplementation;
import com.example.presentation.IUserInterface;

public class UserInterfaceConsoleFactory {
    public static IUserInterface makeConsoleUserInterface(int db) {
        IAuthenticateAccount authenticateAccount = AuthenticateAccountFactory.makeAuthenticateAccount(db);
        ICreateAccount createAccount = CreateAccountFactory.makeCreateAccount(db);

        //APIs
        IGeneratePixQrCode generatePixQrCode = GenerateCelcoinPixQrCodeFactory.makeGeneratePixQrCode();
        IConsultBills consultBills = ConsultBillsFactory.makeConsultBills();
        IPayCelcoinBill payCelcoinBill = PayCelcoinBillsFactory.makePayCelcoinBill();

        return new UserInterfaceConsoleImplementation(
                createAccount,
                authenticateAccount,
                generatePixQrCode,
                consultBills,
                payCelcoinBill
        );
    }
}
