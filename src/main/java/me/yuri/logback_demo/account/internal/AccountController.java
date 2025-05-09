package me.yuri.logback_demo.account.internal;

import me.yuri.logback_demo.account.Account;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity<Account> createAccount() {
        Account createdAccount = accountService.createAccount();
        return ResponseEntity.ok(createdAccount);
    }

    @GetMapping("/{iban}")
    public ResponseEntity<Account> getAccount(@PathVariable String iban) {
        return accountService.getAccount(iban)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Account>> getAllAccounts() {
        List<Account> accounts = accountService.getAllAccounts();
        return ResponseEntity.ok(accounts);
    }

    @DeleteMapping("/{iban}")
    public ResponseEntity<Void> deleteAccount(@PathVariable String iban) {
        accountService.deleteAccount(iban);
        return ResponseEntity.noContent().build();
    }
}