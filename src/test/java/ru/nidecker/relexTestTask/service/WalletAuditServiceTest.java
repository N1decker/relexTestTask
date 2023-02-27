package ru.nidecker.relexTestTask.service;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import ru.nidecker.relexTestTask.AbstractTest;
import ru.nidecker.relexTestTask.entity.User;
import ru.nidecker.relexTestTask.entity.enums.Role;
import ru.nidecker.relexTestTask.exception.DontHaveEnoughRightsException;
import ru.nidecker.relexTestTask.repository.WalletAuditRepository;

import java.time.LocalDate;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class WalletAuditServiceTest extends AbstractTest {

    @Mock
    private WalletAuditRepository walletAuditRepository;

    @InjectMocks
    private WalletAuditService walletAuditService;

    @Test
    void countWalletTransactions_shouldReturnMap_whenArgumentsIsOk() {
        User user = new User();
        user.setRoles(Set.of(Role.ADMIN));
        LocalDate dateFrom = LocalDate.now();
        LocalDate dateTo = LocalDate.now();
        when(walletAuditRepository.countTransactions(dateFrom, dateTo)).thenReturn(1L);

        assertEquals(Map.of("transaction_count", 1L), walletAuditService.countWalletTransactions(user, dateFrom, dateTo));

        Mockito.verify(walletAuditRepository, Mockito.times(1)).countTransactions(dateFrom, dateTo);
    }

    @Test
    void countWalletTransactions_shouldThrowException_whenUserIsNotAdmin() {
        User user = mock(User.class);
        LocalDate dateFrom = LocalDate.now();
        LocalDate dateTo = LocalDate.now();

        assertThrows(DontHaveEnoughRightsException.class, () -> walletAuditService.countWalletTransactions(user, dateFrom, dateTo));
    }
}