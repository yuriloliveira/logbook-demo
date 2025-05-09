package me.yuri.logback_demo.transaction.internal;

import me.yuri.logback_demo.transaction.Transaction;
import me.yuri.logback_demo.transaction.TransactionRequest;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.List;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;

    TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public Transaction createTransaction(TransactionRequest transactionRequest) {
        try {
            Transaction newTransaction = Transaction.from(
                Math.abs(SecureRandom.getInstanceStrong().nextLong()),
                transactionRequest
            );
            transactionRepository.save(newTransaction);
            return newTransaction;
        } catch (NoSuchAlgorithmException ex) {
            throw new RuntimeException(ex);
        }
    }

    public List<Transaction> getTransactionsByIban(String iban) {
        return transactionRepository.findByIban(iban);
    }
}
