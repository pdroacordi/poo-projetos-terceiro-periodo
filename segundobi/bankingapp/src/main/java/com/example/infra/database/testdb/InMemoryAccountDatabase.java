package com.example.infra.database.testdb;

import com.example.data.protocols.database.ICreateUserAccountRepository;
import com.example.data.protocols.database.IFindUserByDocumentRepository;
import com.example.data.protocols.database.IFindUserByEmailRepository;
import com.example.domain.dto.UserAccountDTO;
import com.example.domain.entities.Account;
import com.example.domain.exceptions.ExistentRecordException;
import com.example.infra.utils.mappers.AccountMapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class InMemoryAccountDatabase implements ICreateUserAccountRepository, IFindUserByEmailRepository, IFindUserByDocumentRepository {

    private List<Account> accounts;

    public InMemoryAccountDatabase() {
        this.accounts = new ArrayList<>();
    }


    @Override
    public Account createUserAccount(UserAccountDTO userAccountDTO) {
        if( findUserByEmail(userAccountDTO.getEmail()) != null ) throw new ExistentRecordException("Já existe uma conta cadastrada com este e-mail");
        if( findUserByDocument(userAccountDTO.getDocument()) != null ) throw new ExistentRecordException("Já existe uma conta cadastrada com este e-mail");

        userAccountDTO.setAccountNumber(generateAccountNumberWithCheckDigits());
        userAccountDTO.setCreatedAt(LocalDateTime.now());
        userAccountDTO.setId( accounts.size() + 1 );

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

    public static String generateAccountNumberWithCheckDigits() {
        String accountNumber = String.format("%06d", new Random().nextInt(1000000));

        int firstCheckDigit = calculateCheckDigit(accountNumber, new int[]{7, 6, 5, 4, 3, 2});

        String accountWithFirstCheckDigit = accountNumber + firstCheckDigit;

        int secondCheckDigit = calculateCheckDigit(accountWithFirstCheckDigit, new int[]{8, 7, 6, 5, 4, 3, 2});

        return accountWithFirstCheckDigit + secondCheckDigit;
    }

    private static int calculateCheckDigit(String number, int[] weights) {
        int sum = 0;
        for (int i = 0; i < weights.length; i++) {
            int digit = Character.getNumericValue(number.charAt(i));
            sum += digit * weights[i];
        }
        int remainder = (sum * 10) % 11;
        return remainder == 10 ? 0 : remainder;
    }
}
