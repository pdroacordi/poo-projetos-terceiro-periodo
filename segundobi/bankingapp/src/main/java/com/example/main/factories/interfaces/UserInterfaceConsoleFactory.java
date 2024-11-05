package com.example.main.factories.interfaces;

import com.example.domain.usecases.IAuthenticateAccount;
import com.example.domain.usecases.ICreateAccount;
import com.example.main.factories.usecases.AuthenticateAccountFactory;
import com.example.main.factories.usecases.CreateAccountFactory;
import com.example.presentation.console.UserInterfaceConsoleImplementation;
import com.example.presentation.IUserInterface;

public class UserInterfaceConsoleFactory {
    public static IUserInterface makeConsoleUserInterface() {
        IAuthenticateAccount authenticateAccount = AuthenticateAccountFactory.makeAuthenticateAccount();
        ICreateAccount createAccount = CreateAccountFactory.makeCreateAccount();

        return new UserInterfaceConsoleImplementation(
                createAccount,
                authenticateAccount
        );
    }
}
