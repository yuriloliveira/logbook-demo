package me.yuri.logback_demo.transaction;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

@Component
public class TransactionClient {
    private final RestClient client;

    public TransactionClient(RestClient client) {
        this.client = client;
    }

    public void createTransaction(TransactionRequest transactionRequest) {

    }

    public List<Transaction> getTransactionsByIban(String iban) {
        return client
                .get()
                .uri(uriBuilder -> uriBuilder.path("/transactions").queryParam("iban", iban).build())
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});
    }
}
