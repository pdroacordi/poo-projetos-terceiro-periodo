package com.example.main.factories.repositories.database.postgres;

import com.example.data.protocols.database.ICreateUserAccountRepository;
import com.example.data.protocols.database.IFindUserByDocumentRepository;
import com.example.infra.database.postgres.PostgresAccountDatabase;

import java.sql.SQLException;

public class PostgresAccountRepositoriesUnitOfWork {
    private static final PostgresAccountDatabase postgresAccountDatabase = new PostgresAccountDatabase();

    public static ICreateUserAccountRepository getCreateUserAccountRepository() {
        return postgresAccountDatabase;
    }

    public static IFindUserByDocumentRepository getFindUserByDocumentRepository() {
        return postgresAccountDatabase;
    }

}
