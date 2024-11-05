package infra.database.testdb;

import data.protocols.database.ICreateUserAccountRepository;
import data.protocols.database.IFindUserByDocumentRepository;
import data.protocols.database.IFindUserByEmailRepository;
import domain.dto.UserAccountDTO;
import domain.entities.Account;
import domain.exceptions.AccountException;
import domain.exceptions.ExistentRecordException;
import infra.utils.mappers.AccountMapper;

import java.util.ArrayList;
import java.util.List;

public class InMemoryAccountDatabase implements ICreateUserAccountRepository, IFindUserByEmailRepository, IFindUserByDocumentRepository {

    private List<Account> accounts;

    public InMemoryAccountDatabase() {
        this.accounts = new ArrayList<>();
    }


    @Override
    public Account createUserAccount(UserAccountDTO userAccountDTO) {
        if( findUserByEmail(userAccountDTO.getEmail()) != null ) throw new ExistentRecordException("Já existe uma conta cadastrada com este e-mail");
        if( findUserByDocument(userAccountDTO.getDocument()) != null ) throw new ExistentRecordException("Já existe uma conta cadastrada com este e-mail");

        Account account = AccountMapper.userAccountDTOtoAccount(userAccountDTO);
        accounts.add(account);
        return account;
    }

    @Override
    public Account findUserByEmail(String email) {
        Account account = null;
        for(Account cur : this.accounts){
            if( cur.getEmail().equals(email) ) account = cur;
        }
        return account;
    }

    @Override
    public Account findUserByDocument(String document) {
        Account account = null;
        for(Account cur : this.accounts){
            if( cur.getDocument().equals(document)) account = cur;
        }
        return account;
    }
}
