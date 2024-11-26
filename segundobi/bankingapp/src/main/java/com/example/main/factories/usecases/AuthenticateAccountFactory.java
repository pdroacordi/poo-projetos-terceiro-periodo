package com.example.main.factories.usecases;

import com.example.data.protocols.database.IFindUserByDocumentRepository;
import com.example.data.usecasesimplementation.AuthenticateAccount;
import com.example.domain.usecases.IAuthenticateAccount;
import com.example.main.factories.repositories.database.postgres.PostgresAccountRepositoriesUnitOfWork;
import com.example.main.factories.repositories.database.testdb.TestAccountRepositoriesUnitOfWork;

public class AuthenticateAccountFactory {

    public static IAuthenticateAccount makeAuthenticateAccount(int db) {
        IFindUserByDocumentRepository repository = null;
        switch(db){
            case 0:
                repository = TestAccountRepositoriesUnitOfWork.getFindUserByDocumentRepository();
                break;
            case 1:
                repository = PostgresAccountRepositoriesUnitOfWork.getFindUserByDocumentRepository();
                break;
        }
        return new AuthenticateAccount(
                repository
        );
    }
}
