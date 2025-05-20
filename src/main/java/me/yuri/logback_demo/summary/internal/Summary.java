package me.yuri.logback_demo.summary.internal;

import me.yuri.logback_demo.account.Account;
import me.yuri.logback_demo.balance.BalanceAmount;
import me.yuri.logback_demo.transaction.Transaction;

import java.util.List;

public record Summary(
    Account account,
    BalanceAmount balance,
    List<Transaction> lastThreeTransactions
) {}
