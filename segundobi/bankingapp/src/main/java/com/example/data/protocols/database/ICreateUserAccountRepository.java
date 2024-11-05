package com.example.data.protocols.database;

import com.example.domain.dto.UserAccountDTO;
import com.example.domain.entities.Account;

public interface ICreateUserAccountRepository {

    Account createUserAccount(UserAccountDTO userAccountDTO);

}
