package com.example.infra.database.postgres;

import com.example.data.protocols.database.IConnectToDatabase;
import com.example.data.protocols.database.ICreateUserAccountRepository;
import com.example.data.protocols.database.IFindUserByDocumentRepository;
import com.example.data.protocols.database.IFindUserByEmailRepository;
import com.example.domain.dto.UserAccountDTO;
import com.example.domain.entities.Account;
import com.example.domain.exceptions.AccountException;
import com.example.domain.exceptions.DatabaseException;
import com.example.domain.exceptions.ExistentRecordException;
import com.example.infra.utils.mappers.AccountMapper;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.Random;

public class PostgresAccountDatabase implements IConnectToDatabase, ICreateUserAccountRepository, IFindUserByDocumentRepository, IFindUserByEmailRepository {

    private Connection connection;

    public PostgresAccountDatabase() {
        this.connection = connectToDatabase();
    }

    @Override
    public Connection connectToDatabase() {
        Connection connection = null;
        try {
            String DB_URL = "jdbc:postgresql://aws-0-sa-east-1.pooler.supabase.com:6543/postgres?user=postgres.nowjjxeoayeifxfxczkz&password=projetosuperfodadosandro";
            connection = DriverManager.getConnection(DB_URL);
        } catch (SQLException e) {
            throw new DatabaseException("Não foi possível conectar ao PostgreSQL database");
        }
        return connection;
    }

    @Override
    public Account createUserAccount(UserAccountDTO userAccountDTO) {
        if( findUserByDocument(userAccountDTO.getDocument()) != null ) throw new ExistentRecordException("Já existe uma conta cadastrada com este documento");
        if( findUserByEmail(userAccountDTO.getEmail()) != null ) throw new ExistentRecordException("Já existe uma conta cadastrada com este e-mail");

        userAccountDTO.setAccountNumber(generateAccountNumberWithCheckDigits());
        userAccountDTO.setCreatedAt(LocalDateTime.now());
        Account account = null;
        String insert = "INSERT INTO account(document, password, name, email, account_number, created_at) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(insert);
            preparedStatement.setString(1, userAccountDTO.getDocument());
            preparedStatement.setString(2, userAccountDTO.getPassword());
            preparedStatement.setString(3, userAccountDTO.getName());
            preparedStatement.setString(4, userAccountDTO.getEmail());
            preparedStatement.setString(5, userAccountDTO.getAccountNumber());
            preparedStatement.setTimestamp(6, java.sql.Timestamp.valueOf(userAccountDTO.getCreatedAt()));

            int rowsAffected = preparedStatement.executeUpdate();
            if(rowsAffected > 0) {
                account = AccountMapper.userAccountDTOtoAccount(userAccountDTO);
            }
        }catch(SQLException e) {
            throw new DatabaseException("Erro no banco de dados: "+e.getSQLState());
        }
        return account;
    }

    @Override
    public Account findUserByDocument(String document) {
        Account account = null;
        try {
            String query = "SELECT * FROM account WHERE document=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, document);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                account = resultSetToAccount(resultSet);
            }
        }catch(SQLException e) {
            throw new DatabaseException("Erro no banco de dados: "+e.getSQLState());
        }
        return account;
    }

    private Account resultSetToAccount(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String doc = resultSet.getString("document");
        String password = resultSet.getString("password");
        String name = resultSet.getString("name");
        String email = resultSet.getString("email");
        String accountNumber = resultSet.getString("account_number");
        Timestamp createdAtTS = resultSet.getTimestamp("created_at");
        LocalDateTime createdAt = (createdAtTS != null) ? createdAtTS.toLocalDateTime() : null;
        Timestamp disabledAtTS = resultSet.getTimestamp("disabled_at");
        LocalDateTime disabledAt = (disabledAtTS != null) ? disabledAtTS.toLocalDateTime() : null;

        return new Account.AccountBuilder()
                .setDocument(doc)
                .setAccountNumber(accountNumber)
                .setCreatedAt(createdAt)
                .setDisableAt(disabledAt)
                .setId(id)
                .setEmail(email)
                .setName(name)
                .setPassword(password)
                .build();
    }

    @Override
    public Account findUserByEmail(String email) {
        Account account = null;
        try {
            String query = "SELECT * FROM account WHERE email=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                account = resultSetToAccount(resultSet);
            }
        }catch(SQLException e) {
            throw new DatabaseException("Erro no banco de dados: "+e.getSQLState());
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
