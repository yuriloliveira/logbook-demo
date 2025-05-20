package me.yuri.logback_demo.summary.internal;

import me.yuri.logback_demo.account.Account;
import me.yuri.logback_demo.account.AccountClient;
import me.yuri.logback_demo.account.AccountNotFoundException;
import me.yuri.logback_demo.balance.BalanceAmount;
import me.yuri.logback_demo.balance.BalanceClient;
import me.yuri.logback_demo.transaction.Transaction;
import me.yuri.logback_demo.transaction.TransactionClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SummaryService {
    private final BalanceClient balanceClient;
    private final TransactionClient transactionClient;
    private final AccountClient accountClient;

    private static final Logger log = LoggerFactory.getLogger(SummaryService.class);

    public SummaryService(BalanceClient balanceClient, TransactionClient transactionClient, AccountClient accountClient) {
        this.balanceClient = balanceClient;
        this.transactionClient = transactionClient;
        this.accountClient = accountClient;
    }

    public Optional<Summary> loadSummaryByIban(String iban) {
        try {
            // Load account details
            Account account = accountClient.getAccountByIban(iban);

            // Load balance
            BalanceAmount balance = balanceClient.getBalanceByIban(iban);

            // Load last three transactions
            List<Transaction> lastThreeTransactions = transactionClient.getTransactionsByIban(iban, 3);

            return Optional.of(new Summary(account, balance, lastThreeTransactions));
        } catch (AccountNotFoundException anfe) {
            log.error("Account could not be found...", anfe);
            return Optional.empty();
        }
    }
}
