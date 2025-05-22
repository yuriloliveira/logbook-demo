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
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Optional;

@Service
public class SummaryService {
    private final BalanceClient balanceClient;
    private final TransactionClient transactionClient;
    private final AccountClient accountClient;

    private static final Logger log = LoggerFactory.getLogger(SummaryService.class);

    public SummaryService(@Qualifier("RestClientWithLogging") RestClient restClient) {
        this.balanceClient = new BalanceClient(restClient);
        this.transactionClient = new TransactionClient(restClient);
        this.accountClient = new AccountClient(restClient);
    }

    public Optional<Summary> loadSummaryByIban(String iban) {
        log.info("Received iban {}", iban);

        if (!isIbanValid(iban.toUpperCase())) throw new IllegalArgumentException("Provided IBAN is not valid");

        try {
            // Load account details
            log.info("Loading account details for IBAN {}", iban);
            Account account = accountClient.getAccountByIban(iban);

            // Load balance
            log.info("Loading balance for IBAN {}", iban);
            BalanceAmount balance = balanceClient.getBalanceByIban(iban);


            // Load last three transactions
            log.info("Loading last three transactions for IBAN {}", iban);
            List<Transaction> lastThreeTransactions = transactionClient.getTransactionsByIban(iban, 3);

            Summary summary = new Summary(account, balance, lastThreeTransactions);

            log.info("Summary for IBAN {}: {}", iban, summary);

            return Optional.of(summary);
        } catch (AccountNotFoundException anfe) {
            log.error("Account could not be found...", anfe);
            return Optional.empty();
        }
    }

    private boolean isIbanValid(String iban) {
        boolean isIbanValid = iban != null && iban.matches("^[A-Z]{2}[0-9]{2}[A-Z0-9]{4}[0-9]+$");

        if (isIbanValid) log.info("IBAN is valid: {}", iban);
        else log.info("Requested IBAN is invalid: {}", iban);

        return isIbanValid;
    }
}
