package presentation;

import java.util.Scanner;

public class ConsoleController {

    private final CurrencyConverterConsole currencyConsole;
    private final RomanNumeralConsole romanConsole;

    public ConsoleController(CurrencyConverterConsole currencyConsole, RomanNumeralConsole romanConsole) {
        this.currencyConsole = currencyConsole;
        this.romanConsole = romanConsole;
    }

    public void start(){
        Scanner scanner = new Scanner(System.in);
        char option = 'n';
        do{
            System.out.println("O que você deseja utilizar?\n r. Conversor para romano\n m. Conversor de moedas.\n s. Sair");
            option = scanner.next().charAt(0);
            switch (option){
                case 's':
                    System.out.println("Saindo...");
                    break;
                case 'r':
                    romanConsole.handleRomanNumerals();
                    break;
                case 'm':
                    currencyConsole.handleCurrencyConverter();
                    break;
                default:
                    System.out.println("Opção inválida.");
                    break;
            }
        }while(option != 's');
    }
}
