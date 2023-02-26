package ru.nidecker.relexTestTask.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.nidecker.relexTestTask.entity.User;
import ru.nidecker.relexTestTask.entity.enums.Role;
import ru.nidecker.relexTestTask.exception.DontHaveEnoughRightsException;
import ru.nidecker.relexTestTask.repository.WalletAuditRepository;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class WalletAuditService {

    private final WalletAuditRepository walletAuditRepository;

    public Map<String, Long> countWalletTransactions(User user,
                                                     LocalDate dateFrom,
                                                     LocalDate dateTo) {
        if (!user.getRoles().contains(Role.ADMIN)) {
            throw new DontHaveEnoughRightsException("You don't have enough rights for this operation");
        }

        Map<String, Long> map = new HashMap<>();
        map.put("transaction_count", walletAuditRepository.countTransactions(dateFrom, dateTo));
        return map;
    }
}
