package me.yuri.logback_demo.balance.internal;

import me.yuri.logback_demo.balance.BalanceAmount;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BalanceController {
    
    private final BalanceService balanceService;

    public BalanceController(BalanceService balanceService) {
        this.balanceService = balanceService;
    }

    @GetMapping("/balances")
    public BalanceAmount getBalance(@RequestHeader("x-iban") @NonNull String iban) {
        return balanceService.getBalanceByIban(iban);
    }
}
