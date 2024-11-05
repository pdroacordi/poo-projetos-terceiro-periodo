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
                0,
                3,
                null,
                options,
                options[0]
        );
        IUserInterface userInterface = null;
        switch (selected){
            case 0:
                userInterface = UserInterfaceConsoleFactory.makeConsoleUserInterface();
                break;
            case 1:
                userInterface = UserInterfaceSwingFactory.makeSwingUserInterface();
                break;
        }
        userInterface.start();
    }
}