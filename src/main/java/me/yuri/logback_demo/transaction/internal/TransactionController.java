package me.yuri.logback_demo.transaction.internal;

import me.yuri.logback_demo.transaction.Transaction;
import me.yuri.logback_demo.transaction.TransactionRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    public ResponseEntity<Transaction> createTransaction(@RequestBody TransactionRequest transactionRequest) {
        Transaction createdTransaction = transactionService.createTransaction(transactionRequest);
        return new ResponseEntity<>(createdTransaction, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Transaction>> getTransactionsByIban(
        @RequestParam @NonNull String iban,
        @RequestParam(required = false) Integer count // TODO: support count
    ) {
        List<Transaction> transactions = transactionService.getTransactionsByIban(iban, count);
        return ResponseEntity.ok(transactions);
    }
}