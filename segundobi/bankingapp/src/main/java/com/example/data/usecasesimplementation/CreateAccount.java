package com.example.data.usecasesimplementation;

import com.example.data.protocols.database.ICreateUserAccountRepository;
import com.example.domain.dto.UserAccountDTO;
import com.example.domain.entities.Account;
import com.example.domain.exceptions.AccountException;
import com.example.domain.usecases.ICreateAccount;
import com.example.data.utils.validators.AccountValidator;

public class CreateAccount implements ICreateAccount {

    private final ICreateUserAccountRepository createUserAccountRepository;

    public CreateAccount(ICreateUserAccountRepository createUserAccountRepository) {
        this.createUserAccountRepository = createUserAccountRepository;
    }

    @Override
    public Account createAccount(UserAccountDTO account) {
        if( !AccountValidator.isValidDocument(account.getDocument())) throw new AccountException("CPF/CNPJ inválido.");
        if( !AccountValidator.isValidEmail(account.getEmail())) throw new AccountException("E-mail inválido.");
        if( !AccountValidator.isValidName(account.getName())) throw new AccountException("Nome inválido.");
        return createUserAccountRepository.createUserAccount(account);
    }
}
