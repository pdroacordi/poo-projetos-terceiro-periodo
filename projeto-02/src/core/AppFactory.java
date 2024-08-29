package core;

import data.repositories.CurrencyConversionRepository;
import data.usecasesimplementation.CurrencyConverter;
import data.usecasesimplementation.RomanNumeralConverter;
import domain.usecases.ICurrencyConverter;
import domain.usecases.IRomanNumeralConverter;
import infra.ICurrencyConversionRepository;
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
