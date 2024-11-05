package com.example.data.protocols.database;

import com.example.domain.entities.Account;

public interface IFindUserByDocumentRepository {
    Account findUserByDocument(String document);
}
