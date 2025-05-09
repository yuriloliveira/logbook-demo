package me.yuri.logback_demo.balance;

import java.math.BigDecimal;

public record BalanceAmount(BigDecimal amount) {
    public BalanceAmount decrease(BigDecimal amount) {
        if (this.amount.compareTo(amount) < 0) {
            throw new IllegalArgumentException("New balance is lower than zero.");
        }

        return new BalanceAmount(this.amount.subtract(amount));
    }
}
