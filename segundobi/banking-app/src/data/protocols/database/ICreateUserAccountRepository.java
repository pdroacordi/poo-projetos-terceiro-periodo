package data.protocols.database;

import domain.dto.UserAccountDTO;
import domain.entities.Account;

public interface ICreateUserAccountRepository {

    Account createUserAccount(UserAccountDTO userAccountDTO);

}
