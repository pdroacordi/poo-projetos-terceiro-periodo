package com.example.presentation.swing;

import com.example.domain.dto.LoginDTO;
import com.example.domain.dto.UserAccountDTO;
import com.example.domain.entities.Account;
import com.example.domain.exceptions.AccountException;
import com.example.domain.usecases.IAuthenticateAccount;
import com.example.domain.usecases.ICreateAccount;
import com.example.presentation.IUserInterface;
import com.example.presentation.utils.PasswordSecurityUtils;

import javax.swing.*;
import java.awt.*;
import java.security.NoSuchAlgorithmException;

public class UserInterfaceSwingImplementation implements IUserInterface {

    private Account loggedAccount;
    private final ICreateAccount createAccountService;
    private final IAuthenticateAccount authenticateAccountService;
    private final JFrame mainFrame;
    private final JPanel mainPanel;

    public UserInterfaceSwingImplementation(ICreateAccount createAccountService, IAuthenticateAccount authenticateAccountService) {
        this.createAccountService = createAccountService;
        this.authenticateAccountService = authenticateAccountService;
        mainFrame = new JFrame("Banco do Pedrão");
        mainPanel = new JPanel(new CardLayout());


    }

    private void initializeUI() {
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(400, 300);

        // Adiciona os paineis ao CardLayout
        mainPanel.add(createMainMenuPanel(), "MainMenu");
        mainPanel.add(createAccountFormPanel(), "CreateAccount");
        mainPanel.add(createLoginFormPanel(), "Login");

        mainFrame.add(mainPanel);
        mainFrame.setVisible(true);

        showPanel("MainMenu"); // Exibe o menu principal ao iniciar
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
            System.out.println(name);
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());

            try {
                password = PasswordSecurityUtils.hashPassword(password);
                UserAccountDTO dto = new UserAccountDTO(document, password, name, email);
                Account account = createAccountService.createAccount(dto);
                if (account != null) {
                    loggedAccount = account;
                    JOptionPane.showMessageDialog(mainFrame, "Conta criada com sucesso!");
                    mainPanel.add(createHomeMenuPanel(), "Home");
                    showPanel("Home");
                } else {
                    JOptionPane.showMessageDialog(mainFrame, "Erro ao criar conta.");
                }
            } catch (NoSuchAlgorithmException | AccountException ex) {
                JOptionPane.showMessageDialog(mainFrame, "Erro: " + ex.getMessage());
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
                LoginDTO dto = new LoginDTO(document, password);
                Account account = authenticateAccountService.authenticate(dto);
                if (account != null) {
                    loggedAccount = account;
                    JOptionPane.showMessageDialog(mainFrame, "Login realizado com sucesso!");
                    mainPanel.add(createHomeMenuPanel(), "Home");
                    showPanel("Home");
                } else {
                    JOptionPane.showMessageDialog(mainFrame, "Erro ao fazer login.");
                }
            } catch (NoSuchAlgorithmException ex) {
                JOptionPane.showMessageDialog(mainFrame, "Erro: " + ex.getMessage());
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
        JPanel panel = new JPanel(new GridLayout(3, 1));
        JLabel welcomeLabel = new JLabel();
        System.out.println("Nome da conta: "+loggedAccount.getDocument());
        if (loggedAccount != null) {
            welcomeLabel.setText("Bem-vindo(a), " + loggedAccount.getName() + "!");
        } else {
            welcomeLabel.setText("Bem-vindo(a)!");
        }

        JButton logoutButton = new JButton("Sair");
        logoutButton.addActionListener(e -> {
            loggedAccount = null;
            showPanel("MainMenu");
        });

        panel.add(welcomeLabel);
        panel.add(new JLabel()); // Placeholder
        panel.add(logoutButton);

        return panel;
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
