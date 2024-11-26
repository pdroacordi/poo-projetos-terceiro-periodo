package com.example.infra.api;

import com.example.data.protocols.api.IGenerateCelcoinPixQRCodeRepository;
import com.example.data.protocols.api.IGenerateCelcoinTokenRepository;
import com.example.domain.dto.PixDTO;
import com.example.domain.entities.Merchant;
import com.example.domain.exceptions.ApiException;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;

public class GeneratePixQrCodeRepository implements IGenerateCelcoinPixQRCodeRepository {

    private final IGenerateCelcoinTokenRepository tokenRepository;

    public GeneratePixQrCodeRepository(IGenerateCelcoinTokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    @Override
    public String getCelcoinPixQRCode(double amount) {
        String token = tokenRepository.getCelcoinToken();
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create("https://sandbox.openfinance.celcoin.dev/pix/v1/brcode/static"))
                .header("authorization", "Bearer " + token)
                .header("content-type", "application/json")
                .header("accept", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(getStaticPixJson(amount)))
                .build();

        String result = null;

        try(HttpClient httpClient = HttpClient.newHttpClient()) {
            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            JsonObject jsonObject = JsonParser.parseString(response.body()).getAsJsonObject();

            if(response.statusCode() == 401){
                throw new ApiException("Credenciais não autorizadas.");
            }
            if(response.statusCode() == 400){
                throw new ApiException("Erro: "+ jsonObject.get("message").getAsString());
            }
            if(response.statusCode() == 500){
                throw new ApiException("Erro no servidor interno da Celcoin. Tente novamente mais tarde.");
            }
            result = jsonObject.get("emvqrcps").getAsString();


        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Erro de comunicação com API:" + e.getMessage());
        }

        return result;
    }

    private String getStaticPixJson(double amount){
        Merchant merchant = new Merchant.Builder()
                .postalCode("01201005")
                .city("Barueri")
                .categoryCode("5651")
                .name("Celcoin")
                .build();

        PixDTO pixDTO = new PixDTO.Builder()
                .key("testepix@celcoin.com.br")
                .amount(amount)
                .merchant(merchant)
                .additionalInformation("Referente ao teste do Sandrolaax")
                .build();

        Gson gson = new Gson();
        return gson.toJson(pixDTO);
    }
}
