package me.yuri.logback_demo.summary.internal;

import me.yuri.logback_demo.balance.BalanceAmount;
import me.yuri.logback_demo.balance.BalanceClient;
import me.yuri.logback_demo.transaction.Transaction;
import me.yuri.logback_demo.transaction.TransactionClient;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SummaryService {
    private final BalanceClient balanceClient;
    private final TransactionClient transactionClient;

    public SummaryService(BalanceClient balanceClient, TransactionClient transactionClient) {
        this.balanceClient = balanceClient;
        this.transactionClient = transactionClient;
    }

    public Summary loadSummaryByIban(String iban) {
        // Check if account exists

        // Load balance
        BalanceAmount balance = balanceClient.getBalanceByIban(iban);

        // Load last three transactions
        List<Transaction> lastThreeTransactions = transactionClient.getTransactionsByIban(iban, 3);

        return new Summary(balance, lastThreeTransactions);
    }
}
