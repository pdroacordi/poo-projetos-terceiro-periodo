package core;

import infra.CurrencyConversionRepository;
import data.usecasesimplementation.CurrencyConverter;
import data.usecasesimplementation.RomanNumeralConverter;
import domain.usecases.ICurrencyConverter;
import domain.usecases.IRomanNumeralConverter;
import data.repositories.ICurrencyConversionRepository;
import presentation.ConsoleController;
import presentation.CurrencyConverterConsole;
import presentation.RomanNumeralConsole;

public class AppFactory {

    public ConsoleController createConsoleController(){
        IRomanNumeralConverter romanNumeralConverter = new RomanNumeralConverter();
        ICurrencyConversionRepository currencyConversionRepository = new CurrencyConversionRepository();
        ICurrencyConverter currencyConverter = new CurrencyConverter( currencyConversionRepository );

        CurrencyConverterConsole currencyConverterConsole = new CurrencyConverterConsole(currencyConverter);
        RomanNumeralConsole romanNumeralConsole = new RomanNumeralConsole(romanNumeralConverter);

        return new ConsoleController(currencyConverterConsole, romanNumeralConsole);
    }
}
