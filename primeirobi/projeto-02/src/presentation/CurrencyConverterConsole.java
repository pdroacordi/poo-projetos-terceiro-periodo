package presentation;

import domain.entities.Currency;
import domain.usecases.ICurrencyConverter;

import java.util.InputMismatchException;
import java.util.Scanner;

public class CurrencyConverterConsole {

    private final ICurrencyConverter currencyConverter;
    private final Scanner scanner = new Scanner(System.in);

    public CurrencyConverterConsole(ICurrencyConverter currencyConverter) {
        this.currencyConverter = currencyConverter;
    }

    public void handleCurrencyConverter(){

        try{
            char option = 'n';
            do{
                System.out.println("O que você deseja fazer?\ns. Sair\nm. Ver as moedas passíveis de conversão.\nv. Converter valor.\nt. Ver taxa de conversão.");
                option = scanner.next().charAt(0);
                switch(option){
                    case 's':
                        System.out.println("Saindo...");
                        break;
                    case 'm':
                        System.out.println(currencyConverter.seeCurrencies());
                        break;
                    case 'v':
                        handleAmountOption();
                        break;
                    case 't':
                        handleRateOption();
                        break;
                    default:
                        System.out.println("Opção inválida");
                        break;
                }
            }while(option != 's');
        }catch( InputMismatchException e){
            System.out.println("Entrada inválida");
            scanner.close();
            handleCurrencyConverter();
        }
    }

    private void handleAmountOption(){
        scanner.nextLine();
        System.out.println("Qual a moeda base? "+currencyConverter.seeCurrencies());
        Currency from = new Currency(scanner.nextLine());
        System.out.println("Qual a moeda alvo? "+currencyConverter.seeCurrencies());
        Currency to = new Currency(scanner.nextLine());
        System.out.println("Qual o valor a ser convertido?");
        double amount = scanner.nextDouble();
        double result = currencyConverter.convert(amount, from, to);
        System.out.printf( "\n%.2f %s em %s é: %.2f%s\n",amount, from.getCode(), to.getCode(), result, to.getCode() );
    }

    private void handleRateOption(){
        scanner.nextLine();
        System.out.println("Qual a moeda base? "+currencyConverter.seeCurrencies());
        Currency from = new Currency(scanner.nextLine());
        System.out.println("Qual a moeda alvo? "+currencyConverter.seeCurrencies());
        Currency to = new Currency(scanner.nextLine());
        double result = currencyConverter.convert(from, to);
        System.out.printf("\nA taxa de conversão de %s para %s é: %.2f\n", from.getCode(), to.getCode(), result);
    }
}
