package com.example.main.factories.interfaces;

import com.example.domain.usecases.*;
import com.example.main.factories.usecases.*;
import com.example.presentation.swing.UserInterfaceSwingImplementation;
import com.example.presentation.IUserInterface;

public class UserInterfaceSwingFactory {
    public static IUserInterface makeSwingUserInterface(int db) {
        IAuthenticateAccount authenticateAccount = AuthenticateAccountFactory.makeAuthenticateAccount(db);
        ICreateAccount createAccount = CreateAccountFactory.makeCreateAccount(db);

        //APIs
        IGeneratePixQrCode generatePixQrCode = GenerateCelcoinPixQrCodeFactory.makeGeneratePixQrCode();
        IConsultBills consultBills = ConsultBillsFactory.makeConsultBills();
        IPayCelcoinBill payCelcoinBill = PayCelcoinBillsFactory.makePayCelcoinBill();


        return new UserInterfaceSwingImplementation(
                createAccount,
                authenticateAccount,
                generatePixQrCode,
                consultBills,
                payCelcoinBill
        );
    }
}
