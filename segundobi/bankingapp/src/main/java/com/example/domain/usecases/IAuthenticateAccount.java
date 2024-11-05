package com.example.domain.usecases;

import com.example.domain.dto.LoginDTO;
import com.example.domain.entities.Account;

public interface IAuthenticateAccount {

    Account authenticate(LoginDTO authData);

}
