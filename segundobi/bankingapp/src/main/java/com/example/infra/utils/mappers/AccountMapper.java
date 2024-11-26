package com.example.infra.utils.mappers;

import com.example.domain.dto.UserAccountDTO;
import com.example.domain.entities.Account;

public class AccountMapper {

    public static Account userAccountDTOtoAccount(UserAccountDTO dto){
        return new Account.AccountBuilder()
                .setId(dto.getId())
                .setDocument(dto.getDocument())
                .setName(dto.getName())
                .setEmail(dto.getEmail())
                .setPassword(dto.getPassword())
                .setAccountNumber(dto.getAccountNumber())
                .setCreatedAt(dto.getCreatedAt())
                .setDisableAt(dto.getDisableAt())
                .build();
    }

}
