package infra.utils.mappers;

import domain.dto.UserAccountDTO;
import domain.entities.Account;

public class AccountMapper {

    public static Account userAccountDTOtoAccount(UserAccountDTO dto){
        return new Account(dto.getId(),
                dto.getDocument(),
                dto.getPassword(),
                dto.getName(),
                dto.getEmail(),
                dto.getAccountNumber(),
                dto.getCreatedAt(),
                dto.getDisableAt()
        );
    }

}
