package me.yuri.logback_demo.account;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class AccountClient {
    private static final Logger log = LoggerFactory.getLogger(AccountClient.class);
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
        log.info("Retrieving account {}", iban);
        Account account = client
            .get()
            .uri(uriBuilder -> uriBuilder.pathSegment("accounts", iban).build())
            .retrieve()
            .onStatus(
                status -> status.value() == 404,
                (_, _) -> {
                    log.info("Account {} not found", iban);
                    throw new AccountNotFoundException(iban);
                }
            )
            .body(Account.class);
        log.info("Account {} retrieved", iban);
        return account;
    }
}
