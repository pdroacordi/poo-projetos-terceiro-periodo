package com.example.infra.utils.mappers;

import com.example.domain.dto.UserAccountDTO;
import com.example.domain.entities.Account;

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
