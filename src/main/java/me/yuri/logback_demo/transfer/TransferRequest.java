package me.yuri.logback_demo.transfer;

import java.math.BigDecimal;

public record TransferRequest(String sourceIban, String targetIban, BigDecimal amount) {}
