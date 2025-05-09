package me.yuri.logback_demo.transaction;

import java.math.BigDecimal;

public record TransactionRequest(
    String sourceIban,
    String targetIban,
    BigDecimal amount
) {}