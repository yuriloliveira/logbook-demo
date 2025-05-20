package me.yuri.logback_demo;

import me.yuri.logback_demo.account.Account;
import me.yuri.logback_demo.account.AccountClient;
import me.yuri.logback_demo.transfer.TransferClient;
import me.yuri.logback_demo.transfer.TransferRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

@Component
public class DemoDataInitializer implements ApplicationRunner {
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_CYAN = "\u001B[36m";

    private static final Logger log = LoggerFactory.getLogger(DemoDataInitializer.class);
    private final AccountClient accountClient;
    private final TransferClient transferClient;

    public DemoDataInitializer(AccountClient accountClient, TransferClient transferClient) {
        this.accountClient = accountClient;
        this.transferClient = transferClient;
    }

    @Override
    public void run(ApplicationArguments args) throws NoSuchAlgorithmException {
        log.info("Initializing demo data...");

        log.info("Creating account...");
        Account account = accountClient.createAccount("NL00RABO0000000000");
        log.info("Created account with iban '{}'", account.iban());

        for (int i = 0; i < 10; i++) {
            BigDecimal transferAmount = randomTransferAmount();
            log.info("Transferring {}{}{} from {}{}{} to {}{}{}...",
                ANSI_CYAN,
                transferAmount,
                ANSI_RESET,
                ANSI_RED,
                Account.JOKER_IBAN,
                ANSI_RESET,
                ANSI_GREEN,
                account.iban(),
                ANSI_RESET
            );
            transferClient.transfer(
                new TransferRequest(Account.JOKER_IBAN, account.iban(), transferAmount)
            );
            log.info("Successfully transferred money from {}{}{} to {}{}{}",
                ANSI_RED,
                Account.JOKER_IBAN,
                ANSI_RESET,
                ANSI_GREEN,
                account.iban(),
                ANSI_RESET
            );
        }

        log.info("Demo data initialized.");
        log.info("Use iban {}{}{} to demo.", ANSI_GREEN, account.iban(), ANSI_RESET);
    }

    private BigDecimal randomTransferAmount() throws NoSuchAlgorithmException {
        return BigDecimal
                .valueOf(SecureRandom.getInstanceStrong().nextDouble(0.1, 10000.0))
                .setScale(2, RoundingMode.HALF_UP);
    }
}
