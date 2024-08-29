package data.usecasesimplementation;

import domain.entities.Currency;
import domain.exceptions.CurrencyException;
import domain.usecases.ICurrencyConverter;
import infra.ICurrencyConversionRepository;

import java.util.ArrayList;
import java.util.List;

public class CurrencyConverter implements ICurrencyConverter {

    private final ICurrencyConversionRepository currencyConversionRepository;
    private final ArrayList<String> currencies = new ArrayList<>(List.of("USD", "EUR", "GBP", "JPY", "BRL"));

    public CurrencyConverter(ICurrencyConversionRepository currencyConversionRepository) {
        this.currencyConversionRepository = currencyConversionRepository;
    }

    @Override
    public double convert(double amount, Currency from, Currency to) {
        if( !isValidCurrency( from.getCode() ) )
            throw new CurrencyException("Moeda indisponível: "+from.getCode());
        if( !isValidCurrency( to.getCode() ) )
            throw new CurrencyException("Moeda indisponível: "+to.getCode());
        double rate = currencyConversionRepository.getConversionRate(from, to);
        return amount * rate;
    }

    @Override
    public double convert(Currency from, Currency to) {
        if( !isValidCurrency( from.getCode() ) )
            throw new CurrencyException("Moeda indisponível: "+from.getCode());
        if( !isValidCurrency( to.getCode() ) )
            throw new CurrencyException("Moeda indisponível: "+to.getCode());
        return currencyConversionRepository.getConversionRate(from, to);
    }

    @Override
    public String seeCurrencies() {
        return "[ "+ String.join(", ", currencies) +" ]";
    }

    public boolean isValidCurrency(String code) {
        return currencies.contains(code);
    }
}
