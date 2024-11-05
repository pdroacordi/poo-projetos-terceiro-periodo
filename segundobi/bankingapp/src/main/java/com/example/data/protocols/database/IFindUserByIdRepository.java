package com.example.data.protocols.database;

import com.example.domain.entities.Account;

public interface IFindUserByIdRepository {

    Account findUserById(Integer id);
}
