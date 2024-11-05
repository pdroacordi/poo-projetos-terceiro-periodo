package data.usecasesimplementation;

import data.protocols.database.ICreateUserAccountRepository;
import domain.dto.UserAccountDTO;
import domain.entities.Account;
import domain.exceptions.AccountException;
import domain.usecases.ICreateAccount;
import domain.utils.validators.AccountValidator;

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
