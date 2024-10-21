package presentation;

import domain.usecases.IRomanNumeralConverter;

import java.util.InputMismatchException;
import java.util.Scanner;

public class RomanNumeralConsole {

    private final IRomanNumeralConverter integerToRomanNumeralUseCase;

    public RomanNumeralConsole(IRomanNumeralConverter integerToRomanNumeralUseCase) {
        this.integerToRomanNumeralUseCase = integerToRomanNumeralUseCase;
    }

    public void handleRomanNumerals() {
        Scanner scanner = new Scanner(System.in);
        try{
            System.out.println("Insira o número inteiro a ser convertido para romano: ");
            int input = scanner.nextInt();
            String output = this.integerToRomanNumeralUseCase.toRoman(input);
            System.out.println("O número "+input+" em algarismos romanos é: "+output);
        }catch( InputMismatchException e){
            System.out.println("Entrada inválida");
            scanner.close();
            handleRomanNumerals();
        }
    }

}
