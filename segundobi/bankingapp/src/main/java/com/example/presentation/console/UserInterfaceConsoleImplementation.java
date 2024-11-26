package com.example.presentation.console;

import com.example.domain.dto.BillsDTO;
import com.example.domain.dto.LoginDTO;
import com.example.domain.dto.UserAccountDTO;
import com.example.domain.entities.Account;
import com.example.domain.entities.BarCode;
import com.example.domain.exceptions.AccountException;
import com.example.domain.exceptions.DatabaseException;
import com.example.domain.exceptions.ExistentRecordException;
import com.example.domain.usecases.*;
import com.example.presentation.IUserInterface;
import com.example.presentation.utils.PasswordSecurityUtils;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.Console;
import java.security.NoSuchAlgorithmException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class UserInterfaceConsoleImplementation implements IUserInterface {

    private final Scanner scanner = new Scanner(System.in);
    private Account loggedAccount;

    private final ICreateAccount createAccountService;
    private final IAuthenticateAccount authenticateAccountService;
    private final IGeneratePixQrCode generatePixQrCodeService;
    private final IConsultBills consultBillService;
    private final IPayCelcoinBill payCelcoinBill;

    public UserInterfaceConsoleImplementation(ICreateAccount createAccountService, IAuthenticateAccount authenticateAccountService, IGeneratePixQrCode generatePixQrCodeService, IConsultBills consultBillService, IPayCelcoinBill payCelcoinBill) {
        this.loggedAccount = null;
        this.createAccountService = createAccountService;
        this.authenticateAccountService = authenticateAccountService;
        this.generatePixQrCodeService = generatePixQrCodeService;
        this.consultBillService = consultBillService;
        this.payCelcoinBill = payCelcoinBill;
    }

    @Override
    public void start() {
        System.out.println("- - - - - -  B A N C O    D O   P E D R Ã O  - - - - - -");
        int option = 0;
        while(true){
            System.out.println("O que deseja fazer?\n1. Criar Conta\n2. Entrar em conta\n3. Sair");
            option = getIntegerInput("Insira sua escolha: ");
            switch (option){
                case 1:
                    Account createdAccount = createAccount();
                    if(createdAccount != null){
                        loggedAccount = createdAccount;
                        showHomeMenu();
                    }else{
                        System.out.println("Não foi possível realizar abertura da conta. Tente novamente.");
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
                case 3:
                    System.exit(0);
                default:
                    System.out.println("Opção inválida. Tente novamente.");
                    break;
            }
        }
    }

    private void showHomeMenu() {
        clearConsole();
        while (loggedAccount != null){
            System.out.println("Bem-vindo(a), "+loggedAccount.getName()+"!");
            System.out.println("O que você deseja fazer? \n0. Sair\n1. Gerar Pix.\n2. Consultar boleto.\n3. Pagar boleto.");
            int option = getIntegerInput("Insira sua escolha: ");
            switch (option){
                case 0:
                    loggedAccount = null;
                    break;
                case 1:
                    showQrCode();
                    break;
                case 2:
                    consultBill();
                    break;
                case 3:
                    payCelcoinBill();
                    break;
                default:
                    System.out.println("Opção inválida.");
                    break;
            }
        }
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

        UserAccountDTO dto = new UserAccountDTO.UserAccountDTOBuilder()
                .setName(name)
                .setEmail(email)
                .setDocument(document)
                .setPassword(password)
                .build();

        Account account = null;
        try {
            account = createAccountService.createAccount(dto);
        }catch(AccountException | DatabaseException | ExistentRecordException e){
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

        LoginDTO dto = new LoginDTO.LoginDTOBuilder()
                .setDocument(document)
                .setPassword(password)
                .build();

        Account account = null;
        try{
            account = authenticateAccountService.authenticate(dto);
        }catch(AccountException | DatabaseException ex){
            System.out.println("Erro: "+ex.getMessage());
        }

        return account;
    }

    private void showQrCode(){
        clearConsole();

        double amount = getDoubleInput("Insira o valor do PIX a ser gerado:");
        String qrCodeText = null;
        try {
            qrCodeText = this.generatePixQrCodeService.generatePixQrCode(amount);
        }catch (Exception e){
            System.out.println("Erro: "+e.getMessage());
            return;
        }
        int width = 300;
        int height = 300;

        try {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(qrCodeText, BarcodeFormat.QR_CODE, width, height);

            BufferedImage qrImage = MatrixToImageWriter.toBufferedImage(bitMatrix);

            JFrame frame = new JFrame("QR Code");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setSize(width + 50, height + 50);
            JLabel label = new JLabel(new ImageIcon(qrImage));
            frame.getContentPane().add(label, BorderLayout.CENTER);
            frame.setVisible(true);
        } catch (WriterException e) {
            System.out.println("Não foi possível gerar QR Code: "+e.getMessage());
        }
    }

    private void payCelcoinBill(){
        String transactionId = getStringInput("Insira o ID da transação a ser paga:");
        try{
            System.out.println(payCelcoinBill.payCelcoinBill(transactionId));
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    private void consultBill(){
        clearConsole();
        String digitable = getStringInput("Insira a linha digitavel do boleto a ser consultado:");
        BarCode barCode = new BarCode.Builder()
                .digitable(digitable)
                .type(0)
                .build();
        BillsDTO dto = new BillsDTO.Builder()
                .barCode(barCode)
                .build();
        try{
            String bill = consultBillService.consultBills(dto);
            System.out.println(bill);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }


    private double getDoubleInput(String message){
        boolean isValid = false;
        double input = 0;
        while(!isValid){
            try{
                System.out.println(message);
                input = scanner.nextDouble();
                scanner.nextLine();
                isValid = true;
            }catch(InputMismatchException e){
                System.out.println("Valor inválido. Tente novamente.");
            }
        }
        return input;
    }

    private int getIntegerInput(String message){
        boolean isValid = false;
        int input = 0;
        while(!isValid){
            try{
                System.out.println(message);
                input = scanner.nextInt();
                scanner.nextLine();
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

        while (!isValid) {
            System.out.println(message);
            input = scanner.nextLine();

            if (!input.trim().isEmpty()) {
                isValid = true;
            } else {
                System.out.println("Valor inválido. Tente novamente.");
            }
        }
        return input;
    }


    private void clearConsole(){
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            }
        } catch (Exception e) {
        }
    }

}
