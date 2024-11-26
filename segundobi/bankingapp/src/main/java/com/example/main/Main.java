package com.example.main;

import com.example.main.factories.interfaces.UserInterfaceConsoleFactory;
import com.example.main.factories.interfaces.UserInterfaceSwingFactory;
import com.example.presentation.IUserInterface;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        String[] options = {"Console (Linha de Comando)", "Interface Gráfica"};
        int selected = JOptionPane.showOptionDialog(null,
                "Como você deseja ver o sistema?",
                "Visualização",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
        );

        String[] databases = {"Salvar em Memória", "Persistir (Banco de Dados)"};
        int database = JOptionPane.showOptionDialog(null,
                "Como você deseja que os dados sejam salvos?",
                "Dados",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                databases,
                databases[0]
        );

        IUserInterface userInterface = null;
        switch (selected){
            case 0:
                userInterface = UserInterfaceConsoleFactory.makeConsoleUserInterface(database);
                break;
            case 1:
                userInterface = UserInterfaceSwingFactory.makeSwingUserInterface(database);
                break;
        }
        userInterface.start();
    }
}