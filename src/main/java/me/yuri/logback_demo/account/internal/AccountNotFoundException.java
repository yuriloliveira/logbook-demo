package me.yuri.logback_demo.account.internal;

public class AccountNotFoundException extends RuntimeException {
    AccountNotFoundException(String iban) {
        super("Account not found with IBAN: " + iban);
    }
}
