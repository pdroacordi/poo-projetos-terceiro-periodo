package com.example.presentation.console;

import com.example.domain.dto.LoginDTO;
import com.example.domain.dto.UserAccountDTO;
import com.example.domain.entities.Account;
import com.example.domain.exceptions.AccountException;
import com.example.domain.usecases.IAuthenticateAccount;
import com.example.domain.usecases.ICreateAccount;
import com.example.presentation.IUserInterface;
import com.example.presentation.utils.PasswordSecurityUtils;

import java.io.Console;
import java.security.NoSuchAlgorithmException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class UserInterfaceConsoleImplementation implements IUserInterface {

    private final Scanner scanner = new Scanner(System.in);
    private Account loggedAccount;

    private ICreateAccount createAccountService;
    private IAuthenticateAccount authenticateAccountService;

    public UserInterfaceConsoleImplementation(ICreateAccount createAccountService, IAuthenticateAccount authenticateAccountService) {
        this.loggedAccount = null;
        this.createAccountService = createAccountService;
        this.authenticateAccountService = authenticateAccountService;
    }

    @Override
    public void start() {
        System.out.println("- - - - - -  B A N C O    D O   P E D R Ã O  - - - - - -");
        System.out.println("O que deseja fazer?\n1. Criar Conta\n2. Entrar em conta\n3. Sair");
        int option = 0;
        while(option != 3){
            option = getIntegerInput("Insira sua escolha: ");
            switch (option){
                case 1:
                    Account createdAccount = createAccount();
                    if(createdAccount != null){
                        loggedAccount = createdAccount;
                        showHomeMenu();
                    }else{
                        System.out.println("Não foi possível realizar abertura da conta. Tente novamente mais tarde.");
                    }
                    break;
                case 2:
                    Account logged = login();
                    if(logged != null){
                        loggedAccount = logged;
                        showHomeMenu();
                    }else{
                        System.out.println("Não foi possível acessar a conta.");
                    }
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
                    break;
            }
        }
    }

    private void showHomeMenu() {
        clearConsole();
        System.out.println("Bem-vindo(a), "+loggedAccount.getName()+"!");
    }

    private Account createAccount() {
        System.out.println("- - - - - - A B E R T U R A   D E   C O N T A - - - - -");
        String document = getStringInput("Insira o Documento (CPF/CNPJ) para abertura da conta: ");
        String name = getStringInput("Insira o nome/razão social do titular da conta:");
        String email = getStringInput("Insira o e-mail principal da conta:");

        Console console = System.console();
        if (console == null) {
            System.out.println("Console não disponível.");
            System.exit(1);
        }
        char[] passwordArray = console.readPassword("Digite a senha para sua conta:");
        String password = new String(passwordArray);
        try {
            password = PasswordSecurityUtils.hashPassword(password);
        }catch (NoSuchAlgorithmException e){
            System.out.println("Não foi possível encriptografar a senha.");
            return null;
        }
        java.util.Arrays.fill(passwordArray, '*');

        UserAccountDTO dto = new UserAccountDTO( document,
                password,
                name,
                email
        );
        Account account = null;
        try {
            account = createAccountService.createAccount(dto);
        }catch(AccountException e){
            System.out.println("Erro: "+e.getMessage());
        }
        return account;
    }

    private Account login() {
        String document = getStringInput("Insira o documento (CNPJ/CPF) do titular da conta:");

        Console console = System.console();
        if (console == null) {
            System.out.println("Console não disponível.");
            System.exit(1);
        }
        char[] passwordArray = console.readPassword("Digite a senha para sua conta:");
        String password = new String(passwordArray);
        try {
            password = PasswordSecurityUtils.hashPassword(password);
        }catch (NoSuchAlgorithmException e){
            System.out.println("Não foi possível encriptografar a senha.");
            return null;
        }
        java.util.Arrays.fill(passwordArray, '*');

        LoginDTO dto = new LoginDTO(document, password);

        return authenticateAccountService.authenticate(dto);
    }


    private int getIntegerInput(String message){
        boolean isValid = false;
        int input = 0;
        while(!isValid){
            try{
                System.out.println(message);
                input = scanner.nextInt();
                isValid = true;
            }catch(InputMismatchException e){
                System.out.println("Valor inválido. Tente novamente.");
            }
        }
        return input;
    }

    private String getStringInput(String message){
        boolean isValid = false;
        String input = "";
        while(!isValid){
            try{
                System.out.println(message);
                input = scanner.nextLine();
                if(input.trim().isEmpty()) throw new InputMismatchException();
                isValid = true;
            }catch(InputMismatchException e){
                System.out.println("Valor inválido. Tente novamente.");
            }
        }
        return input;
    }

    private void clearConsole(){
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                // Comando para limpar o console no Windows
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                // Comando para limpar o console no Unix/Linux/macOS
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            }
        } catch (Exception e) {
        }
    }

}
