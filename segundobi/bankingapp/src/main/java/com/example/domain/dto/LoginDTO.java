package com.example.domain.dto;

public class LoginDTO {
    private String document;
    private String password;

    private LoginDTO(LoginDTOBuilder builder) {
        this.document = builder.document;
        this.password = builder.password;
    }

    public String getDocument() {
        return document;
    }

    public String getPassword() {
        return password;
    }

    public static class LoginDTOBuilder {
        private String document;
        private String password;

        public LoginDTOBuilder() {
            // Construtor padrão
        }

        public LoginDTOBuilder setDocument(String document) {
            this.document = document;
            return this;
        }

        public LoginDTOBuilder setPassword(String password) {
            this.password = password;
            return this;
        }

        public LoginDTO build() {
            return new LoginDTO(this);
        }
    }
}
