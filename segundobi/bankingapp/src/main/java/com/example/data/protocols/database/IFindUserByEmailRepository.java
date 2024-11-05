package com.example.data.protocols.database;

import com.example.domain.entities.Account;

public interface IFindUserByEmailRepository {
    Account findUserByEmail(String email);
}
