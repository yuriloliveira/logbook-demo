package me.yuri.logback_demo.balance.internal;

import me.yuri.logback_demo.balance.Balance;
import me.yuri.logback_demo.balance.BalanceAmount;
import me.yuri.logback_demo.transaction.TransactionClient;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.stream.Stream;

@Service
public class BalanceService {
    private final TransactionClient transactionClient;

    BalanceService(TransactionClient transactionClient) {
        this.transactionClient = transactionClient;
    }

    public BalanceAmount getBalanceByIban(String iban) {
        Stream<BigDecimal> transactionsAmounts = transactionClient
                .getTransactionsByIban(iban)
                .stream()
                .map(transaction -> transaction.getAmountForIban(iban));
        return new Balance(transactionsAmounts).currentAmount();
    }
}
