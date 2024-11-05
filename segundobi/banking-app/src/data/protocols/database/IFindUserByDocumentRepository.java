package data.protocols.database;

import domain.entities.Account;

public interface IFindUserByDocumentRepository {
    Account findUserByDocument(String document);
}
