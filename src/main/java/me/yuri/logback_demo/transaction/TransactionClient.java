package me.yuri.logback_demo.transaction;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriBuilder;

import java.util.List;

@Component
public class TransactionClient {
    private final RestClient client;

    public TransactionClient(RestClient client) {
        this.client = client;
    }

    public void createTransaction(TransactionRequest transactionRequest) {
        client
            .post()
            .uri("/transactions")
            .body(transactionRequest)
            .retrieve()
            .toBodilessEntity();
    }

    public List<Transaction> getTransactionsByIban(String iban) {
        return getTransactionsByIban(iban, null);
    }

    public List<Transaction> getTransactionsByIban(String iban, Integer count) {
        return client
                .get()
                .uri(uriBuilder ->
                    withCountQueryParamIfNotNull(uriBuilder, count)
                        .path("/transactions")
                        .queryParam("iban", iban)
                        .build()
                )
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});
    }

    private static UriBuilder withCountQueryParamIfNotNull(UriBuilder uriBuilder, Integer count) {
        if (count != null) {
            return uriBuilder.queryParam("count", count);
        }

        return uriBuilder;
    }
}
