package me.yuri.logback_demo.account;

public class AccountNotFoundException extends RuntimeException {
    public AccountNotFoundException(String iban) {
        super("Account with IBAN '" + iban + "' was not found");
    }
}
