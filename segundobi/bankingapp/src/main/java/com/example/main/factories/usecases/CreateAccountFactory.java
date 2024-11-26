package com.example.main.factories.usecases;

import com.example.data.protocols.database.ICreateUserAccountRepository;
import com.example.data.usecasesimplementation.CreateAccount;
import com.example.domain.usecases.ICreateAccount;
import com.example.main.factories.repositories.database.postgres.PostgresAccountRepositoriesUnitOfWork;
import com.example.main.factories.repositories.database.testdb.TestAccountRepositoriesUnitOfWork;

public class CreateAccountFactory {
    public static ICreateAccount makeCreateAccount(int db){
        ICreateUserAccountRepository repository = null;
        switch(db){
            case 0:
                repository = TestAccountRepositoriesUnitOfWork.getCreateUserAccountRepository();
                break;
            case 1:
                repository = PostgresAccountRepositoriesUnitOfWork.getCreateUserAccountRepository();
                break;
        }
        return new CreateAccount(
                repository
        );
    }
}
