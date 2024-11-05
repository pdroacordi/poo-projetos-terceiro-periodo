package com.example.data.usecasesimplementation;

import com.example.data.protocols.database.IFindUserByDocumentRepository;
import com.example.data.protocols.database.IFindUserByIdRepository;
import com.example.domain.dto.LoginDTO;
import com.example.domain.entities.Account;
import com.example.domain.exceptions.AccountException;
import com.example.domain.usecases.IAuthenticateAccount;

import java.util.NoSuchElementException;

public class AuthenticateAccount implements IAuthenticateAccount {

    private final IFindUserByDocumentRepository findUserByDocumentRepository;

    public AuthenticateAccount(IFindUserByDocumentRepository findUserByDocumentRepository) {
        this.findUserByDocumentRepository = findUserByDocumentRepository;
    }

    @Override
    public Account authenticate(LoginDTO authData) {
        Account account = findUserByDocumentRepository.findUserByDocument(authData.getDocument());
        if(account != null && !account.getPassword().equals(authData.getPassword())){
            throw new AccountException("Senha inválida.");
        }

        return account;
    }
}
