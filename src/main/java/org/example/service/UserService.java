package org.example.service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import okhttp3.*;
import org.example.entity.User;
import org.example.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.ApplicationScope;

import java.io.IOException;

@ApplicationScope
@Component
public class UserService {


    private final UserRepo repository;
    @Autowired
    public UserService(UserRepo repository){
        this.repository = repository;

    }
    public User saveNewUser(User user){
        return repository.save(user);
    }


    public boolean checkBalanceEUR(User userInfo, double amount){
        User user = repository.findById(userInfo.getId()).orElse(null);
        if (user != null){
            double balance = user.getAccountStatus();

            return balance >= amount;
        }
        return false;
    }
    public boolean checkBalanceBTC(User userInfo, double amount){
        User user = repository.findById(userInfo.getId()).orElse(null);
        if (user != null){
            double balance = user.getAccountStatus();

            return balance >= amount;
        }
        return false;
    }
    public void performTransaction(User userInfo, double amount) {
        // Update the user's balance in the database
        User user = repository.findById(userInfo.getId()).orElse(null);
        if (user != null) {
            double balance = user.getAccountStatus();
            user.setAccountStatus(balance - amount);
            repository.save(user);
        }
    }
    public void performTransactionBTC(User userInfo, double amount) {
        // Update the user's balance in the database
        User user = repository.findById(userInfo.getId()).orElse(null);
        if (user != null) {
            double balance = user.getBTCAmount();
            user.setAccountStatus(balance - amount);
            repository.save(user);
        }
    }

    public String getCurrentValueOfBTC() throws IOException {
        OkHttpClient client = new OkHttpClient();

        RequestBody body = new FormBody.Builder()
                .add("key1", "value1")
                .add("key2", "value2")
                .build();

        Request request = new Request.Builder()
                .url("https://rest.coinapi.io/v1/exchangerate/BTC/EUR")
                .post(body)
                .addHeader("X-CoinAPI-Key", "73034021-THIS-IS-SAMPLE-KEYs")
                .build();

        Response response = client.newCall(request).execute();
        // Process the response as needed
        if (response.isSuccessful()) {
            // Parse the response body
            String responseBody = response.body().string();

            // Process the response as needed
            // Example: Extract the current value of BTC from the response
            double currentValueOfBTC = parseCurrentValueFromResponse(responseBody);

            // Perform your transaction logic using the current value of BTC and the provided amount
            // ...

            return "Transaction successful";
        } else {
            // Handle the case when the request was not successful
            // Example: Log an error message
            System.out.println("Error: " + response.code() + " " + response.message());

            return "Transaction failed";
        }
    }

    private double parseCurrentValueFromResponse(String responseBody) {
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(responseBody, JsonObject.class);
        double currentValue = jsonObject.get("rate").getAsDouble();
        return currentValue;
    }
}

