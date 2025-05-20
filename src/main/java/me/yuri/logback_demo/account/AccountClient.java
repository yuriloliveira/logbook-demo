package me.yuri.logback_demo.account;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class AccountClient {
    private final RestClient client;

    public AccountClient(RestClient client) {
        this.client = client;
    }

    public Account createAccount(String iban) {
        return client
            .post()
            .uri("/accounts")
            .body(iban)
            .retrieve()
            .body(Account.class);
    }

    public Account getAccountByIban(String iban) {
        return client
                .get()
                .uri(uriBuilder -> uriBuilder.pathSegment("accounts", iban).build())
                .retrieve()
                .onStatus(
                    status -> status.value() == 404,
                    (_, _) -> { throw new AccountNotFoundException(iban); }
                )
                .body(Account.class);
    }
}
