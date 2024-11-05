package domain.usecases;

import domain.dto.UserAccountDTO;
import domain.entities.Account;

public interface ICreateAccount {

    Account createAccount(UserAccountDTO account);

}
