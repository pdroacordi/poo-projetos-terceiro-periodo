package infra;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import data.repositories.ICurrencyConversionRepository;
import domain.entities.Currency;
import domain.exceptions.ApiException;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class CurrencyConversionRepository implements ICurrencyConversionRepository {

    private final String API_KEY = "fca_live_fAhL2dBSpujTbtBbXWfN1qNpauQ2AMGQVFEXyihz";

    @Override
    public double getConversionRate(Currency fromCurrency, Currency toCurrency) {
        HttpClient client = HttpClient.newHttpClient();

        UrlBuilder urlBuilder = new UrlBuilder("https://api.freecurrencyapi.com/v1/latest");
        String url = urlBuilder.addParam("apikey", API_KEY)
                .addParam("base_currency", fromCurrency.getCode())
                .addParam("currencies", toCurrency.getCode())
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if(response.statusCode() != 200) {
                throw new ApiException("Erro de comunicação com API de conversão de moedas.");
            }
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(response.body());
            JsonNode conversionRate = rootNode.path("data").path(toCurrency.getCode());
            return conversionRate.asDouble();
        }catch(IOException | InterruptedException e){
            e.printStackTrace();
        }catch(ApiException e){
            System.out.println(e.getMessage());
        }
        return 0;
    }
}
