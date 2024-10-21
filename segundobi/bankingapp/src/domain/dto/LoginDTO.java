package domain.dto;

public class LoginDTO {
    private String document;
    private String password;

    public LoginDTO(String document, String password) {
        this.document = document;
        this.password = password;
    }

    public String getDocument() {
        return document;
    }

    public String getPassword() {
        return password;
    }
}
