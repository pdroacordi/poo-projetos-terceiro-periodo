package data.protocols.database;

import domain.entities.Account;

public interface IFindUserByIdRepository {

    Account findUserById(Integer id);
}
