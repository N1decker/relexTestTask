package ru.nidecker.relexTestTask.service;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import ru.nidecker.relexTestTask.AbstractTest;
import ru.nidecker.relexTestTask.entity.CurrencyName;
import ru.nidecker.relexTestTask.entity.User;
import ru.nidecker.relexTestTask.entity.enums.Role;
import ru.nidecker.relexTestTask.exception.DontHaveEnoughRightsException;
import ru.nidecker.relexTestTask.exception.EntityAlreadyExistsException;
import ru.nidecker.relexTestTask.repository.CurrencyNameRepository;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CurrencyNameServiceTest extends AbstractTest {

    @Mock
    private CurrencyNameRepository currencyNameRepository;

    @InjectMocks
    private CurrencyNameService currencyNameService;

    @Test
    void createCurrency_shouldThrowException_whenUserIsNotAdmin() {
        User user = new User();
        user.setRoles(Set.of(Role.USER));
        String str = "anyString";

        assertThrows(DontHaveEnoughRightsException.class, () -> currencyNameService.creatCurrency(str, user));
    }

    @Test
    void createCurrency_shouldThrowException_whenCurrencyIsAlreadyExists() {
        User user = new User();
        user.setRoles(Set.of(Role.ADMIN));
        String currencyName = "RUB";
        CurrencyName currency = new CurrencyName("RUB");
        when(currencyNameRepository.findAll()).thenReturn(List.of(currency));

        assertThrows(EntityAlreadyExistsException.class, () -> currencyNameService.creatCurrency(currencyName, user));

        verify(currencyNameRepository, Mockito.times(1)).findAll();
    }
}