package infra;

import domain.entities.Currency;

public interface ICurrencyConversionRepository {

    public double getConversionRate(Currency fromCurrency, Currency toCurrency);
}
