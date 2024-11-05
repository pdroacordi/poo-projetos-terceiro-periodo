package com.example.main.factories.usecases;

import com.example.data.usecasesimplementation.CreateAccount;
import com.example.domain.usecases.ICreateAccount;
import com.example.main.factories.repositories.AccountRepositoriesUnitOfWork;

public class CreateAccountFactory {
    public static ICreateAccount makeCreateAccount(){
        return new CreateAccount(
                AccountRepositoriesUnitOfWork.createUserAccountRepository()
        );
    }
}
