package com.example.presentation.swing;

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
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.security.NoSuchAlgorithmException;

public class UserInterfaceSwingImplementation implements IUserInterface {

    private Account loggedAccount;
    private final ICreateAccount createAccountService;
    private final IAuthenticateAccount authenticateAccountService;
    private final IGeneratePixQrCode generatePixQrCodeService;
    private final IConsultBills consultBillService;
    private final IPayCelcoinBill payCelcoinBill;
    private final JFrame mainFrame;
    private final JPanel mainPanel;

    public UserInterfaceSwingImplementation(ICreateAccount createAccountService, IAuthenticateAccount authenticateAccountService,
                                            IGeneratePixQrCode generatePixQrCodeService, IConsultBills consultBillService, IPayCelcoinBill payCelcoinBill) {
        this.createAccountService = createAccountService;
        this.authenticateAccountService = authenticateAccountService;
        this.generatePixQrCodeService = generatePixQrCodeService;
        this.consultBillService = consultBillService;
        this.payCelcoinBill = payCelcoinBill;
        this.mainFrame = new JFrame("Banco do Pedrão");
        this.mainPanel = new JPanel(new CardLayout());
    }

    private void initializeUI() {
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(400, 300);

        mainPanel.add(createMainMenuPanel(), "MainMenu");
        mainPanel.add(createAccountFormPanel(), "CreateAccount");
        mainPanel.add(createLoginFormPanel(), "Login");

        mainFrame.add(mainPanel);
        mainFrame.setVisible(true);

        showPanel("MainMenu");
    }

    private JPanel createMainMenuPanel() {
        JPanel panel = new JPanel(new GridLayout(4, 1));
        JLabel label = new JLabel("O que deseja fazer?");
        JButton createAccountButton = new JButton("Criar Conta");
        JButton loginButton = new JButton("Entrar em Conta");
        JButton exitButton = new JButton("Sair");

        createAccountButton.addActionListener(e -> showPanel("CreateAccount"));
        loginButton.addActionListener(e -> showPanel("Login"));
        exitButton.addActionListener(e -> System.exit(0));

        panel.add(label);
        panel.add(createAccountButton);
        panel.add(loginButton);
        panel.add(exitButton);
        return panel;
    }

    private JPanel createAccountFormPanel() {
        JPanel panel = new JPanel(new GridLayout(5, 2));

        JLabel documentLabel = new JLabel("Documento (CPF/CNPJ):");
        JTextField documentField = new JTextField();

        JLabel nameLabel = new JLabel("Nome/Razão Social:");
        JTextField nameField = new JTextField();

        JLabel emailLabel = new JLabel("E-mail:");
        JTextField emailField = new JTextField();

        JLabel passwordLabel = new JLabel("Senha:");
        JPasswordField passwordField = new JPasswordField();

        JButton submitButton = new JButton("Criar Conta");
        submitButton.addActionListener(e -> {
            String document = documentField.getText();
            String name = nameField.getText();
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());

            try {
                password = PasswordSecurityUtils.hashPassword(password);
                UserAccountDTO dto = new UserAccountDTO.UserAccountDTOBuilder()
                        .setName(name)
                        .setDocument(document)
                        .setPassword(password)
                        .setEmail(email)
                        .build();
                Account account = createAccountService.createAccount(dto);
                if (account != null) {
                    loggedAccount = account;
                    JOptionPane.showMessageDialog(mainFrame, "Conta criada com sucesso!");
                    mainPanel.add(createHomeMenuPanel(), "Home");
                    showPanel("Home");
                } else {
                    JOptionPane.showMessageDialog(mainFrame, "Erro ao criar conta", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NoSuchAlgorithmException | AccountException | ExistentRecordException ex) {
                JOptionPane.showMessageDialog(mainFrame, "Erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        panel.add(documentLabel);
        panel.add(documentField);
        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(emailLabel);
        panel.add(emailField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(new JLabel());
        panel.add(submitButton);

        return panel;
    }

    private JPanel createLoginFormPanel() {
        JPanel panel = new JPanel(new GridLayout(3, 2));

        JLabel documentLabel = new JLabel("Documento (CPF/CNPJ):");
        JTextField documentField = new JTextField();

        JLabel passwordLabel = new JLabel("Senha:");
        JPasswordField passwordField = new JPasswordField();

        JButton submitButton = new JButton("Entrar");
        submitButton.addActionListener(e -> {
            String document = documentField.getText();
            String password = new String(passwordField.getPassword());

            try {
                password = PasswordSecurityUtils.hashPassword(password);
                LoginDTO dto = new LoginDTO.LoginDTOBuilder()
                        .setDocument(document)
                        .setPassword(password)
                        .build();
                Account account = authenticateAccountService.authenticate(dto);
                if (account != null) {
                    loggedAccount = account;
                    JOptionPane.showMessageDialog(mainFrame, "Login realizado com sucesso!");
                    mainPanel.add(createHomeMenuPanel(), "Home");
                    showPanel("Home");
                } else {
                    JOptionPane.showMessageDialog(mainFrame, "Erro ao fazer login.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NoSuchAlgorithmException | DatabaseException | AccountException ex) {
                JOptionPane.showMessageDialog(mainFrame, "Erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        panel.add(documentLabel);
        panel.add(documentField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(new JLabel());
        panel.add(submitButton);

        return panel;
    }

    private JPanel createHomeMenuPanel() {
        JPanel panel = new JPanel(new GridLayout(5, 1));
        JLabel welcomeLabel = new JLabel();
        if (loggedAccount != null) {
            welcomeLabel.setText("Bem-vindo(a), " + loggedAccount.getName() + "!");
        } else {
            welcomeLabel.setText("Bem-vindo(a)!");
        }

        JButton generatePixButton = new JButton("Gerar PIX");
        JButton consultBillButton = new JButton("Consultar Boleto");
        JButton payBillButton = new JButton("Pagar Boleto");
        JButton logoutButton = new JButton("Sair");

        generatePixButton.addActionListener(e -> generatePixQrCode());
        consultBillButton.addActionListener(e -> consultBill());
        payBillButton.addActionListener(e -> payBill());
        logoutButton.addActionListener(e -> {
            loggedAccount = null;
            showPanel("MainMenu");
        });

        panel.add(welcomeLabel);
        panel.add(generatePixButton);
        panel.add(consultBillButton);
        panel.add(payBillButton);
        panel.add(logoutButton);

        return panel;
    }

    private void generatePixQrCode() {
        String amount = JOptionPane.showInputDialog(mainFrame, "Insira o valor do PIX:");
        try {
            String qrCodeText = generatePixQrCodeService.generatePixQrCode(Double.parseDouble(amount));
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(qrCodeText, BarcodeFormat.QR_CODE, 300, 300);
            BufferedImage qrImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
            JOptionPane.showMessageDialog(mainFrame, new JLabel(new ImageIcon(qrImage)), "QR Code", JOptionPane.PLAIN_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(mainFrame, "Erro ao gerar QR Code: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void consultBill() {
        String digitableLine = JOptionPane.showInputDialog(mainFrame, "Insira a linha digitável do boleto:");
        BarCode barCode = new BarCode.Builder().digitable(digitableLine).type(0).build();
        BillsDTO dto = new BillsDTO.Builder().barCode(barCode).build();
        try {
            String billDetails = consultBillService.consultBills(dto);
            JOptionPane.showMessageDialog(mainFrame, billDetails, "Detalhes do Boleto", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(mainFrame, "Erro ao consultar boleto: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void payBill() {
        String transactionId = JOptionPane.showInputDialog(mainFrame, "Insira o ID da transação a ser paga:");
        try {
            String result = payCelcoinBill.payCelcoinBill(transactionId);
            JOptionPane.showMessageDialog(mainFrame, result, "Pagamento de Boleto", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(mainFrame, "Erro ao pagar boleto: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showPanel(String panelName) {
        CardLayout layout = (CardLayout) mainPanel.getLayout();
        layout.show(mainPanel, panelName);
    }

    @Override
    public void start() {
        initializeUI();
    }
}
