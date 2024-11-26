package com.example.main.factories.repositories.database.testdb;

import com.example.data.protocols.database.ICreateUserAccountRepository;
import com.example.data.protocols.database.IFindUserByDocumentRepository;
import com.example.data.protocols.database.IFindUserByEmailRepository;
import com.example.infra.database.testdb.InMemoryAccountDatabase;

public class TestAccountRepositoriesUnitOfWork {
    private static final InMemoryAccountDatabase inMemoryAccountDatabase = new InMemoryAccountDatabase();

    public static ICreateUserAccountRepository getCreateUserAccountRepository() {
        return inMemoryAccountDatabase;
    }

    public static IFindUserByEmailRepository getFindUserByEmailRepository() {
        return inMemoryAccountDatabase;
    }

    public static IFindUserByDocumentRepository getFindUserByDocumentRepository() {
        return inMemoryAccountDatabase;
    }

}
