package com.one.smartexchanges.operators;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.google.gson.Gson;
import com.one.smartexchanges.models.Exchange;

public class ExchangeConsultor {

    public Exchange exchangeCurrency(double valueToConvert, String baseCurrency, String targetCurrency){
        URI link = URI.create("https://v6.exchangerate-api.com/v6/75ed6e11e44dd0455fbf92b1/pair/" + baseCurrency + "/" + targetCurrency + "/" + valueToConvert);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(link)
                .build();
        try {
            HttpResponse<String> response = client
                    .send(request,HttpResponse.BodyHandlers.ofString());
            return new Gson().fromJson(response.body(),Exchange.class);
        } catch (Exception e) {
            throw new RuntimeException("Currency not found");
        }
    }
}
