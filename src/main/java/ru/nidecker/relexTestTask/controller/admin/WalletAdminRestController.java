package ru.nidecker.relexTestTask.controller.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.nidecker.relexTestTask.dto.WalletDTO;
import ru.nidecker.relexTestTask.entity.User;
import ru.nidecker.relexTestTask.service.WalletService;

@RestController
@RequestMapping(value = "rest/admin")
@RequiredArgsConstructor
@Slf4j
public class WalletAdminRestController {

    private final WalletService walletService;

    @GetMapping("currency_sum")
    public WalletDTO getCurrencySum(@AuthenticationPrincipal User user,
                                    String currencyName) {
        return walletService.getCurrencySum(user, currencyName);
    }
}
