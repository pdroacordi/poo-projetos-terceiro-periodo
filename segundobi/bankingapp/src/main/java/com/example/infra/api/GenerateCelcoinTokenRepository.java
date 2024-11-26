package com.example.infra.api;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.data.protocols.api.IGenerateCelcoinTokenRepository;
import com.example.domain.exceptions.ApiException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Date;

public class GenerateCelcoinTokenRepository implements IGenerateCelcoinTokenRepository {

    private static String TOKEN = null;

    @Override
    public String getCelcoinToken() {
        if(TOKEN != null && isJWTValid(TOKEN)){
            return TOKEN;
        }

        String GRANT_TYPE_HOMOLOG = "client_credentials";
        String CLIENT_SECRET_HOMOLOG = "e9d15cde33024c1494de7480e69b7a18c09d7cd25a8446839b3be82a56a044a3";
        String CLIENT_ID_HOMOLOG = "41b44ab9a56440.teste.celcoinapi.v5";


        StringBuilder params = new StringBuilder();
        params.append("client_id=").append(URLEncoder.encode(CLIENT_ID_HOMOLOG, StandardCharsets.UTF_8));
        params.append("&grant_type=").append(URLEncoder.encode(GRANT_TYPE_HOMOLOG, StandardCharsets.UTF_8));
        params.append("&client_secret=").append(URLEncoder.encode(CLIENT_SECRET_HOMOLOG, StandardCharsets.UTF_8));

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://sandbox.openfinance.celcoin.dev/v5/token"))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString(params.toString()))
                .build();

        try(HttpClient client = HttpClient.newHttpClient()) {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            JsonObject jsonObject = JsonParser.parseString(response.body()).getAsJsonObject();

            if (response.statusCode() == 400) {
                throw new ApiException("Erro de comunicação com API: "+ jsonObject.get("message").getAsString());
            }
            if (response.statusCode() == 500) {
                throw new ApiException("Erro no servidor interno da API (500). Tente novamente mais tarde.");
            }


            TOKEN = jsonObject.get("access_token").getAsString();
        }catch (ApiException e){
            throw new ApiException(e.getMessage());
        }catch(Exception e){
            throw new RuntimeException("Não foi possível comunicar-se com API: "+e.getMessage());
        }
        return TOKEN;
    }

    private boolean isJWTValid(String token) {
        DecodedJWT decodedJWT = JWT.decode(token);

        Date expiresAt = decodedJWT.getExpiresAt();
        return expiresAt.before(new Date());
    }
}
