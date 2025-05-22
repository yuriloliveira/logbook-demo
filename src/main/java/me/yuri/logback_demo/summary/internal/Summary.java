package me.yuri.logback_demo.summary.internal;

import com.fasterxml.jackson.annotation.JsonProperty;
import me.yuri.logback_demo.account.Account;
import me.yuri.logback_demo.balance.BalanceAmount;
import me.yuri.logback_demo.transaction.Transaction;

import java.util.List;

public class Summary {
    @JsonProperty
    public final Account account;
    @JsonProperty
    private final BalanceAmount balance;
    @JsonProperty
    private final List<Transaction> lastThreeTransactions;

    public Summary(Account account, BalanceAmount balance, List<Transaction> lastThreeTransactions) {
        this.account = account;
        this.balance = balance;
        this.lastThreeTransactions = lastThreeTransactions;
    }
}