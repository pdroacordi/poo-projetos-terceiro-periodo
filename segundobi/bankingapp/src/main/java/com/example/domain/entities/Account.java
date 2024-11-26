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

    private Account(AccountBuilder builder) {
        this.id = builder.id;
        this.document = builder.document;
        this.password = builder.password;
        this.name = builder.name;
        this.email = builder.email;
        this.accountNumber = builder.accountNumber;
        this.createdAt = builder.createdAt;
        this.disableAt = builder.disableAt;
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

    public static class AccountBuilder {
        private Integer id;
        private String document;
        private String password;
        private String name;
        private String email;
        private String accountNumber;
        private LocalDateTime createdAt;
        private LocalDateTime disableAt;

        public AccountBuilder() {
            // Default constructor
        }

        public AccountBuilder setId(Integer id) {
            this.id = id;
            return this;
        }

        public AccountBuilder setDocument(String document) {
            this.document = document;
            return this;
        }

        public AccountBuilder setPassword(String password) {
            this.password = password;
            return this;
        }

        public AccountBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public AccountBuilder setEmail(String email) {
            this.email = email;
            return this;
        }

        public AccountBuilder setAccountNumber(String accountNumber) {
            this.accountNumber = accountNumber;
            return this;
        }

        public AccountBuilder setCreatedAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public AccountBuilder setDisableAt(LocalDateTime disableAt) {
            this.disableAt = disableAt;
            return this;
        }

        public Account build() {
            return new Account(this);
        }
    }

}
