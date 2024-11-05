package domain.utils.validators;

import domain.entities.Account;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AccountValidator {

    public static boolean isValidDocument(String document){
        return ( isValidCNPJ(document) || isValidCPF(document) );
    }

    public static boolean isValidEmail(String email){
        return email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
    }

    public static boolean isValidName(String name){
        return name.matches("^[a-zA-Z]+$");
    }

    private static boolean isValidCPF(String cpf){
        cpf = cpf.replace(".", "").replace("-", "");
        if (!cpf.matches("\\d{11}")) {
            return false;
        }
        if (cpf.matches("(\\d)\\1{10}")) {
            return false;
        }
        try {
            int firstDigit = computeVeryfingDigitCPF(cpf.substring(0, 9), 10);
            int secondDigit = computeVeryfingDigitCPF(cpf.substring(0, 9) + firstDigit, 11);

            String cpfCalculado = cpf.substring(0, 9) + firstDigit + secondDigit;
            return cpf.equals(cpfCalculado);
        } catch (Exception e) {
            return false;
        }
    }

    private static int computeVeryfingDigitCPF(String cpfPartial, int weightInitial) {
        int sum = 0;
        int weight = weightInitial;

        for (int i = 0; i < cpfPartial.length(); i++) {
            int num = Character.getNumericValue(cpfPartial.charAt(i));
            sum += num * weight;
            weight--;
        }

        int resto = sum % 11;

        if (resto < 2) {
            return 0;
        } else {
            return 11 - resto;
        }
    }

    private static boolean isValidCNPJ(String cnpj) {
        cnpj = cnpj.replace(".", "")
                .replace("-", "")
                .replace("/", "");

        if (!cnpj.matches("\\d{14}")) {
            return false;
        }
        if (cnpj.matches("(\\d)\\1{13}")) {
            return false;
        }
        try {
            char digit13 = computeVeryfingDigit(cnpj.substring(0, 12));
            char digit14 = computeVeryfingDigit(cnpj.substring(0, 12) + digit13);
            return (digit13 == cnpj.charAt(12)) && (digit14 == cnpj.charAt(13));
        } catch (Exception e) {
            return false;
        }

    }
    private static char computeVeryfingDigit(String partialCNPJ) {
        int sum = 0;
        int weight = 2;

        for (int i = partialCNPJ.length() - 1; i >= 0; i--) {
            int num = partialCNPJ.charAt(i) - '0';
            sum += num * weight;
            weight = (weight < 9) ? weight + 1 : 2;
        }

        int resto = sum % 11;

        if (resto == 0 || resto == 1) {
            return '0';
        } else {
            return (char) ((11 - resto) + '0');
        }
    }
}

