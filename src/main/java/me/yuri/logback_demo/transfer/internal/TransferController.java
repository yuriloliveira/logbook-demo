package me.yuri.logback_demo.transfer.internal;

import me.yuri.logback_demo.transfer.TransferRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransferController {
    private final TransferService transferService;

    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }

    @PostMapping("/transfers")
    public void createTransfer(@RequestBody TransferRequest transferRequest) {
        transferService.transferMoney(transferRequest);
    }
}
