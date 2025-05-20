package me.yuri.logback_demo.account.internal;

import me.yuri.logback_demo.account.Account;
import me.yuri.logback_demo.account.AccountNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account createAccount() {
        Account account = new Account(randomIban());
        return accountRepository.save(account);
    }

    public Optional<Account> getAccount(String id) {
        return accountRepository.findByIban(id);
    }

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public void deleteAccount(String id) {
        accountRepository
            .findByIban(id)
            .ifPresentOrElse(
                    account -> accountRepository.deleteByIban(id),
                    () -> { throw new AccountNotFoundException("Account not found with id: " + id); }
            );
    }

    /**
     * Generates a random IBAN (International Bank Account Number).
     * Format: 2 letter country code + 2 check digits + up to 30 alphanumeric characters
     *
     * @return A randomly generated IBAN
     */
    private String randomIban() {
        String[] countryCodes = {"DE", "FR", "GB", "ES", "IT", "NL", "BE", "AT", "CH"};
        String countryCode = countryCodes[ThreadLocalRandom.current().nextInt(countryCodes.length)];

        // Generate check digits (2 digits)
        String checkDigits = String.format("%02d", ThreadLocalRandom.current().nextInt(0, 100));

        // Generate bank code (8 digits)
        String bankCode = String.format("%08d", ThreadLocalRandom.current().nextInt(0, 100000000));

        // Generate account number (10 digits)
        String accountNumber = String.format("%010d", ThreadLocalRandom.current().nextInt(0, 1000000000));

        return countryCode + checkDigits + bankCode + accountNumber;
    }
}