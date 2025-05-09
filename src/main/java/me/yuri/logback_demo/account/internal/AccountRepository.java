package me.yuri.logback_demo.account.internal;

import me.yuri.logback_demo.account.Account;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class AccountRepository {

    private final Map<String, Account> accounts = new ConcurrentHashMap<>();

    AccountRepository() {
        accounts.put(Account.JOKER_IBAN, new Account(Account.JOKER_IBAN));
    }

    public Account save(Account account) {
        accounts.put(account.iban(), account);
        return account;
    }

    public Optional<Account> findByIban(String iban) {
        return Optional.ofNullable(accounts.get(iban));
    }

    public List<Account> findAll() {
        return List.copyOf(accounts.values());
    }

    public void deleteByIban(String iban) {
        accounts.remove(iban);
    }
}