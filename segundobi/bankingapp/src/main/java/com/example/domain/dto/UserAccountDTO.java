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

    public UserAccountDTO(String document, String password, String name, String email) {
        this.document = document;
        this.password = password;
        this.name = name;
        this.email = email;
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
}
