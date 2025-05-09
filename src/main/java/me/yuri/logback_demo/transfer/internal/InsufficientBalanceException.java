package me.yuri.logback_demo.transfer.internal;

import java.math.BigDecimal;

public class InsufficientBalanceException extends RuntimeException {
    public InsufficientBalanceException(BigDecimal transferAmount, BigDecimal currentBalance) {
        super("Insufficient balance for transfer of " + transferAmount + " with current balance of " + currentBalance);
    }
}
