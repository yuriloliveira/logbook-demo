package me.yuri.logback_demo.transaction.internal;

import me.yuri.logback_demo.account.Account;
import me.yuri.logback_demo.transaction.Transaction;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class TransactionRepository {
    private final Map<Long, Transaction> transactions = new ConcurrentHashMap<>();

    TransactionRepository() {
        Transaction jokerTransaction = new Transaction(
                0L,
                null,
                Account.JOKER_IBAN,
                BigDecimal.valueOf(999999999999L),
                LocalDateTime.now(Clock.systemUTC())
        );
        transactions.put(0L, jokerTransaction);
    }

    public void save(Transaction newTransaction) {
        new Account(newTransaction.sourceIban());
        transactions.put(newTransaction.id(), newTransaction);
    }

    public List<Transaction> findByIban(String iban) {
        return transactions.values().stream()
            .filter(transaction ->
                iban.equals(transaction.sourceIban()) || iban.equals(transaction.targetIban())
            ).toList();
    }
}
