package me.yuri.logback_demo.transaction;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDateTime;

public record Transaction(
    Long id,
    String sourceIban,
    String targetIban,
    BigDecimal amount,
    LocalDateTime timestamp
) {
    public BigDecimal getAmountForIban(String iban) {
        if (targetIban.equals(iban)) {
            return amount;
        }
        return amount.negate();
    }

    public static Transaction from(Long id, TransactionRequest transactionRequest) {
        return new Transaction(
            id,
            transactionRequest.sourceIban(),
            transactionRequest.targetIban(),
            transactionRequest.amount(),
            LocalDateTime.now(Clock.systemUTC())
        );
    }
}