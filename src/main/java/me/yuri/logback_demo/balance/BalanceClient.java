package me.yuri.logback_demo.balance;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Objects;

@Component
public class BalanceClient {
    Logger log = LoggerFactory.getLogger(BalanceClient.class);
    private final RestClient client;

    BalanceClient(RestClient client) {
        this.client = client;
    }

    public BalanceAmount getBalanceByIban(String iban) {
        log.info("Getting balance for IBAN: {}", iban);
        BalanceAmount balanceAmount = client
            .get()
            .uri("/balances")
            .header("x-iban", iban)
            .retrieve()
            .body(BalanceAmount.class);
        log.info("Balance for IBAN: {} is {}", iban, Objects.requireNonNull(balanceAmount).amount());
        return balanceAmount;
    }
}
