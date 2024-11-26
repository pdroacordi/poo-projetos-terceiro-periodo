package com.example.infra.api;

import com.example.data.protocols.api.IConsultCelcoinBillRepository;
import com.example.data.protocols.api.IGenerateCelcoinTokenRepository;
import com.example.domain.dto.BillsDTO;
import com.example.domain.exceptions.ApiException;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConsultCelcoinBillRepository implements IConsultCelcoinBillRepository {

    private final IGenerateCelcoinTokenRepository tokenRepository;

    public ConsultCelcoinBillRepository(IGenerateCelcoinTokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    @Override
    public String consultBills(BillsDTO billsDTO) {
        String token = tokenRepository.getCelcoinToken();

        Gson gson = new Gson();
        String requestBody = gson.toJson(billsDTO);

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create("https://sandbox.openfinance.celcoin.dev/v5/transactions/billpayments/authorize"))
                .header("authorization", "Bearer "+token)
                .header("content-type", "application/json")
                .header("accept", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        String result = null;

        try(HttpClient httpClient = HttpClient.newHttpClient()) {
            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            JsonObject jsonObject = JsonParser.parseString(response.body()).getAsJsonObject();

            if( response.statusCode() == 401 ){
                throw new ApiException("Não foi possível comunicar-se com servidor para verificar boletos, pois as credenciais são inválidas.");
            }
            if( response.statusCode() == 400 ){
                throw new ApiException("Não foi possível comunicar-se com servidor para verificar boletos, por conta de: " + jsonObject.get("message").getAsString());
            }
            if( response.statusCode() == 500 ){
                throw new ApiException("Não foi possível comunicar-se com servidor para verificar boletos, pois houve um erro interno. Tente novamente mais tarde.");
            }
            result = formatBillData(jsonObject);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Erro de comunicação com API: "+ e.getMessage());
        }
        return result;
    }


    private String formatBillData(JsonObject jsonData) {
        JsonObject registerData = jsonData.getAsJsonObject("registerData");
        StringBuilder sb = new StringBuilder();

        int totalWidth = 60;
        int labelWidth = 30;
        int valueWidth = totalWidth - 4 - labelWidth;

        int maxValueLength = getMaxValueLength(registerData);
        if (maxValueLength + labelWidth + 4 > totalWidth) {
            totalWidth = maxValueLength + labelWidth + 4;
            valueWidth = totalWidth - 4 - labelWidth;
        }

        String horizontalLine = "═";
        String topBorder = "╔" + repeat(horizontalLine, totalWidth - 2) + "╗\n";
        String title = "║" + centerText("Detalhes da Conta", totalWidth - 2) + "║\n";
        String separator = "╠" + repeat(horizontalLine, totalWidth - 2) + "╣\n";

        sb.append(topBorder);
        sb.append(title);
        sb.append(separator);

        sb.append(formatLine("ID da Transação", jsonData.get("transactionId"), labelWidth, valueWidth));
        sb.append(formatLine("Cedente", jsonData.get("assignor"), labelWidth, valueWidth));
        sb.append(formatLine("Recebedor", registerData.get("recipient"), labelWidth, valueWidth));
        sb.append(formatLine("Documento do Recebedor", registerData.get("documentRecipient"), labelWidth, valueWidth));
        sb.append(formatLine("Pagador", registerData.get("payer"), labelWidth, valueWidth));
        sb.append(formatLine("Documento do Pagador", registerData.get("documentPayer"), labelWidth, valueWidth));
        sb.append(formatLine("Válido até", registerData.get("payDueDate"), labelWidth, valueWidth));
        sb.append(formatLine("Valor de desconto", registerData.get("discountValue"), labelWidth, valueWidth));
        sb.append(formatLine("Multa calculada", registerData.get("fineValueCalculated"), labelWidth, valueWidth));
        sb.append(formatLine("Valor original", registerData.get("originalValue"), labelWidth, valueWidth));
        sb.append(formatLine("Total Atualizado", registerData.get("totalUpdated"), labelWidth, valueWidth));
        sb.append(formatLine("Total pago", registerData.get("totalValuePaid"), labelWidth, valueWidth));

        String bottomBorder = "╚" + repeat(horizontalLine, totalWidth - 2) + "╝\n";
        sb.append(bottomBorder);

        return sb.toString();
    }

    private String repeat(String s, int count) {
        return String.valueOf(s).repeat(Math.max(0, count));
    }

    private String centerText(String text, int width) {
        if (text.length() >= width) {
            return text.substring(0, width);
        }
        int padding = (width - text.length()) / 2;
        return " ".repeat(padding) + text + " ".repeat(width - text.length() - padding);
    }

    private String formatLine(String label, JsonElement value, int labelWidth, int valueWidth) {
        String labelWithColon = "  " + label + ":";
        int spacesAfterLabel = labelWidth - labelWithColon.length();
        spacesAfterLabel = Math.max(spacesAfterLabel, 1);

        String spaces = repeat(" ", spacesAfterLabel);
        String formattedValue = value != null && !value.isJsonNull()
                ? String.format("%-" + valueWidth + "s", value.getAsString())
                : "N/A";
        return "║" + labelWithColon + spaces + formattedValue + " ║\n";
    }

    private int getMaxValueLength(JsonObject registerData) {
        int max = 0;
        for (String key : registerData.keySet()) {
            JsonElement value = registerData.get(key);
            if (value != null && !value.isJsonNull()) {
                max = Math.max(max, value.getAsString().length());
            }
        }
        return max;
    }

}
