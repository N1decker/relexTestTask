package ru.nidecker.relexTestTask.controller.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.nidecker.relexTestTask.entity.User;
import ru.nidecker.relexTestTask.service.WalletAuditService;

import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping(value = "rest/admin")
@RequiredArgsConstructor
@Slf4j
public class TransactionCountAdminRestController {

    private final WalletAuditService walletAuditService;

    @GetMapping("transactions")
    @Cacheable("transactions")
    public Map<String, Long> getTransactionCount(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFrom,
                                                 @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateTo,
                                                 @AuthenticationPrincipal User user) {
        return walletAuditService.countWalletTransactions(user, dateFrom, dateTo);
    }
}
