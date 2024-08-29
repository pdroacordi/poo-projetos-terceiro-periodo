package domain.usecases;

import domain.entities.Currency;

public interface ICurrencyConverter {

    public double convert(double amount, Currency from, Currency to);
    public double convert(Currency from, Currency to);
    public String seeCurrencies();

}
