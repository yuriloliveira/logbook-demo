package me.yuri.logback_demo.transfer;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class TransferClient {
    private final RestClient restClient;

    public TransferClient(RestClient restClient) {
        this.restClient = restClient;
    }

    public void transfer(TransferRequest request) {
        restClient
            .post()
            .uri("/transfers")
            .body(request)
            .retrieve()
            .toBodilessEntity();
    }
}
