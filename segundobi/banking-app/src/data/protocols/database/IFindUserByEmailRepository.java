package data.protocols.database;

import domain.entities.Account;

public interface IFindUserByEmailRepository {
    Account findUserByEmail(String email);
}
