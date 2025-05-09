package me.yuri.logback_demo.transfer.internal;

import me.yuri.logback_demo.balance.BalanceAmount;
import me.yuri.logback_demo.balance.BalanceClient;
import me.yuri.logback_demo.transaction.TransactionClient;
import me.yuri.logback_demo.transaction.TransactionRequest;
import me.yuri.logback_demo.transfer.TransferRequest;
import org.springframework.stereotype.Service;

@Service
public class TransferService {
    private final BalanceClient balanceClient;
    private final TransactionClient transactionClient;

    public TransferService(BalanceClient balanceClient, TransactionClient transactionClient) {
        this.balanceClient = balanceClient;
        this.transactionClient = transactionClient;
    }

    public void transferMoney(TransferRequest transferRequest) {
        BalanceAmount currentBalanceAmount = balanceClient.getBalanceByIban(transferRequest.sourceIban());
        try {
            currentBalanceAmount.decrease(transferRequest.amount());
            TransactionRequest transactionRequest = new TransactionRequest(
                transferRequest.sourceIban(),
                transferRequest.targetIban(),
                transferRequest.amount()
            );
            transactionClient.createTransaction(transactionRequest);
        } catch (IllegalArgumentException ex) {
            throw new InsufficientBalanceException(transferRequest.amount(), currentBalanceAmount.amount());
        }
    }
}
