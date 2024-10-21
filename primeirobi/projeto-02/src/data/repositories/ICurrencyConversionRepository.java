package data.repositories;

import domain.entities.Currency;

public interface ICurrencyConversionRepository {

    public double getConversionRate(Currency fromCurrency, Currency toCurrency);
}
