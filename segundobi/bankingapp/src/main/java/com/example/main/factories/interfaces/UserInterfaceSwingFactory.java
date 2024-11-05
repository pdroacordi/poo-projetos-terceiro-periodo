package com.example.main.factories.interfaces;

import com.example.domain.usecases.IAuthenticateAccount;
import com.example.domain.usecases.ICreateAccount;
import com.example.main.factories.usecases.AuthenticateAccountFactory;
import com.example.main.factories.usecases.CreateAccountFactory;
import com.example.presentation.swing.UserInterfaceSwingImplementation;
import com.example.presentation.IUserInterface;

public class UserInterfaceSwingFactory {
    public static IUserInterface makeSwingUserInterface() {
        IAuthenticateAccount authenticateAccount = AuthenticateAccountFactory.makeAuthenticateAccount();
        ICreateAccount createAccount = CreateAccountFactory.makeCreateAccount();

        return new UserInterfaceSwingImplementation(
                createAccount,
                authenticateAccount
        );
    }
}
