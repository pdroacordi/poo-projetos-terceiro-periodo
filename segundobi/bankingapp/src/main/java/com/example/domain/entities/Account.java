package com.example.domain.entities;

import java.time.LocalDateTime;

public class Account {

    private Integer id;
    private String document;
    private String password;
    private String name;
    private String email;
    private String accountNumber;
    private LocalDateTime createdAt;
    private LocalDateTime disableAt;

    public Account(Integer id, String document, String password, String name, String email, String accountNumber) {
        this.id = id;
        this.document = document;
        this.password = password;
        this.name = name;
        this.email = email;
        this.accountNumber = accountNumber;
    }

    public Account(Integer id, String document, String password, String name, String email, String accountNumber, LocalDateTime createdAt, LocalDateTime disableAt) {
        this.id = id;
        this.document = document;
        this.password = password;
        this.name = name;
        this.email = email;
        this.accountNumber = accountNumber;
        this.createdAt = createdAt;
        this.disableAt = disableAt;
    }

    public Integer getId() {
        return id;
    }

    public String getDocument() {
        return document;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getDisableAt() {
        return disableAt;
    }

    public void setDisableAt(LocalDateTime disableAt) {
        this.disableAt = disableAt;
    }


}
