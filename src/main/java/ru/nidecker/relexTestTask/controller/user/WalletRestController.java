package ru.nidecker.relexTestTask.controller.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.nidecker.relexTestTask.entity.User;
import ru.nidecker.relexTestTask.entity.Wallet;
import ru.nidecker.relexTestTask.service.WalletService;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping(value = "rest/user")
@RequiredArgsConstructor
public class WalletRestController {

    public final WalletService walletService;

    @GetMapping(value = "wallets", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Double> getAllWallets(@AuthenticationPrincipal User user) {
        return walletService.getAllWalletsName(user);
    }

    @PostMapping(value = "wallets", produces = MediaType.APPLICATION_JSON_VALUE)
    public Wallet createWallet(@AuthenticationPrincipal User user,
                               String currencyName) {
        return walletService.createWalletForUser(currencyName, user);
    }

    @PutMapping(value = "wallets", produces = MediaType.APPLICATION_JSON_VALUE)
    public Wallet topUpBalance(@AuthenticationPrincipal User user,
                               @RequestParam String wallet,
                               String balance) {
        return walletService.topUpBalance(wallet, balance, user);
    }
}
