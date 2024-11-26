package com.example.infra.api;

import com.example.data.protocols.api.IGenerateCelcoinTokenRepository;
import com.example.data.protocols.api.IPayCelcoinBillRepository;
import com.example.domain.dto.BillsPaymentDTO;
import com.example.domain.exceptions.ApiException;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class PayCelcoinBillRepository implements IPayCelcoinBillRepository {

    private final IGenerateCelcoinTokenRepository tokenRepository;

    public PayCelcoinBillRepository(IGenerateCelcoinTokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    @Override
    public String payBill(String transactionId) {
        String token = tokenRepository.getCelcoinToken();
        String uri = "https://sandbox.openfinance.celcoin.dev/v5/transactions/billpayments/" +transactionId+ "/capture";
        Gson gson = new Gson();
        BillsPaymentDTO dto = new BillsPaymentDTO.Builder()
                .externalNSU(1234)
                .externalTerminal("teste123")
                .build();
        String requestBody = gson.toJson(dto);

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .header("accept", "application/json")
                .header("authorization", "Bearer " + token)
                .header("content-type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        String result = null;

        try(HttpClient httpClient = HttpClient.newHttpClient()) {
            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            JsonObject jsonObject = JsonParser.parseString(response.body()).getAsJsonObject();


            if(response.statusCode() == 401){
                throw new ApiException("Não foi possível comunicar-se com servidor para pagar boleto, pois as credenciais são inválidas.");
            }
            if( response.statusCode() == 400 ){
                throw new ApiException("Não foi possível pagar boleto, por conta de: " + jsonObject.get("message").getAsString());
            }
            if( response.statusCode() == 500 ){
                throw new ApiException("Não foi possível comunicar-se com servidor para pagar boleto, pois houve um erro interno. Tente novamente mais tarde.");
            }
            result = "Pagamento efetivado: "+jsonObject.get("message").getAsString();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e.getMessage());
        }
        return result;
    }
}
