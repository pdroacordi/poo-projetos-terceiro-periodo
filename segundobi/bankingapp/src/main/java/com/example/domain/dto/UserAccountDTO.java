package com.example.domain.dto;

import java.time.LocalDateTime;

public class UserAccountDTO {

    private Integer id;
    private String document;
    private String password;
    private String name;
    private String email;
    private String accountNumber;
    private LocalDateTime createdAt;
    private LocalDateTime disableAt;

    private UserAccountDTO(UserAccountDTOBuilder builder) {
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

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
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

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public static class UserAccountDTOBuilder {
        private Integer id;
        private String document;
        private String password;
        private String name;
        private String email;
        private String accountNumber;
        private LocalDateTime createdAt;
        private LocalDateTime disableAt;

        public UserAccountDTOBuilder setId(Integer id) {
            this.id = id;
            return this;
        }

        public UserAccountDTOBuilder setDocument(String document) {
            this.document = document;
            return this;
        }

        public UserAccountDTOBuilder setPassword(String password) {
            this.password = password;
            return this;
        }

        public UserAccountDTOBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public UserAccountDTOBuilder setEmail(String email) {
            this.email = email;
            return this;
        }

        public UserAccountDTOBuilder setAccountNumber(String accountNumber) {
            this.accountNumber = accountNumber;
            return this;
        }

        public UserAccountDTOBuilder setCreatedAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public UserAccountDTOBuilder setDisableAt(LocalDateTime disableAt) {
            this.disableAt = disableAt;
            return this;
        }

        public UserAccountDTO build() {
            return new UserAccountDTO(this);
        }
    }
}
