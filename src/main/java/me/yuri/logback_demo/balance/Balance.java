package me.yuri.logback_demo.balance;

import java.math.BigDecimal;
import java.util.stream.Stream;

public record Balance(Stream<BigDecimal> transactionAmounts) {
    public BalanceAmount currentAmount() {
        return new BalanceAmount(transactionAmounts.reduce(BigDecimal.ZERO, BigDecimal::add));
    }
}
