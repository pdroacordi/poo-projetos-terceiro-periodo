package com.example.domain.usecases;

import com.example.domain.dto.UserAccountDTO;
import com.example.domain.entities.Account;

public interface ICreateAccount {

    Account createAccount(UserAccountDTO account);

}
