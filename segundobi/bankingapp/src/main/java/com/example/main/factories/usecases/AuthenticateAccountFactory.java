package com.example.main.factories.usecases;

import com.example.data.usecasesimplementation.AuthenticateAccount;
import com.example.domain.usecases.IAuthenticateAccount;
import com.example.main.factories.repositories.AccountRepositoriesUnitOfWork;

public class AuthenticateAccountFactory {

    public static IAuthenticateAccount makeAuthenticateAccount() {
        return new AuthenticateAccount(
                AccountRepositoriesUnitOfWork.getFindUserByDocumentRepository()
        );
    }
}
