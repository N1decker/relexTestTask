package ru.nidecker.relexTestTask.service;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.webjars.NotFoundException;
import ru.nidecker.relexTestTask.AbstractTest;
import ru.nidecker.relexTestTask.entity.User;
import ru.nidecker.relexTestTask.entity.Wallet;
import ru.nidecker.relexTestTask.exception.EntityAlreadyExistsException;
import ru.nidecker.relexTestTask.exception.NotValidFieldException;
import ru.nidecker.relexTestTask.repository.WalletAuditRepository;
import ru.nidecker.relexTestTask.repository.WalletRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class WalletServiceTest extends AbstractTest {

    private static final Long ID = 1L;

    @Mock
    private WalletRepository walletRepository;

    @Mock
    private WalletAuditRepository walletAuditRepository;

    @Mock
    private CurrencyNameService currencyNameService;

    @InjectMocks
    private WalletService walletService;

    @Test
    void createWalletForUser_shouldThrowException_whenCurrencyNotFound() {
        String currencyName = "RUB";
        User user = mock(User.class);
        when(currencyNameService.getAllCurrencyNames())
                .thenReturn(Collections.emptyList());

        assertThrows(NotFoundException.class,
                () -> walletService.createWalletForUser(currencyName, user));

        verify(currencyNameService, times(1))
                .getAllCurrencyNames();
    }

    @Test
    void createWalletForUser_shouldThrowException_whenWalletAlreadyCreatedForUser() {
        String currencyName = "RUB";
        User user = new User();
        user.setId(ID);
        when(currencyNameService.getAllCurrencyNames()).thenReturn(List.of(currencyName));
        when(walletRepository.findByNameAndUserId(currencyName, user.getId()))
                .thenReturn(Optional.of(mock(Wallet.class)));

        assertThrows(EntityAlreadyExistsException.class,
                () -> walletService.createWalletForUser(currencyName, user));

        verify(currencyNameService, times(1))
                .getAllCurrencyNames();
        verify(walletRepository, times(1))
                .findByNameAndUserId(currencyName, user.getId());
    }

    @Test
    void createWalletForUser_shouldReturnWallet_whenArgumentsIsOk() {
        String currencyName = "RUB";
        User user = new User();
        user.setId(ID);
        when(currencyNameService.getAllCurrencyNames())
                .thenReturn(List.of(currencyName));

        assertDoesNotThrow(() -> walletService.createWalletForUser(currencyName, user));

        verify(currencyNameService, times(1))
                .getAllCurrencyNames();
        verify(walletRepository, times(1))
                .findByNameAndUserId(currencyName, user.getId());
    }

    @Test
    void topUpBalance_shouldThrowException_whenWalletNotFound() {
        String walletName = "RUB";
        String balance = "1";
        User user = new User();
        user.setId(ID);
        when(walletRepository.findByNameAndUserId(walletName, user.getId()))
                .thenReturn(Optional.empty());

        assertThrows(NotFoundException.class,
                () -> walletService.topUpBalance(walletName, balance, user));

        verify(walletRepository, times(1))
                .findByNameAndUserId(walletName, user.getId());
    }

    @Test
    void topUpBalance_shouldThrowException_whenBalanceCantBeParsed() {
        String walletName = "RUB";
        String balance = "notValid";
        User user = new User();
        user.setId(ID);
        when(walletRepository.findByNameAndUserId(walletName, user.getId()))
                .thenReturn(Optional.of(mock(Wallet.class)));

        assertThrows(IllegalArgumentException.class,
                () -> walletService.topUpBalance(walletName, balance, user));

        verify(walletRepository, times(1))
                .findByNameAndUserId(walletName, user.getId());
    }

    @Test
    void topUpBalance_shouldReturnWallet_whenArgumentsIsOk() {
        String walletName = "RUB";
        String balance = "1";
        User user = new User();
        user.setId(ID);
        when(walletRepository.findByNameAndUserId(walletName, user.getId()))
                .thenReturn(Optional.of(mock(Wallet.class)));

        assertDoesNotThrow(() -> walletService.topUpBalance(walletName, balance, user));

        verify(walletRepository, times(1))
                .findByNameAndUserId(walletName, user.getId());
    }

    @Test
    void withdrawMoney_shouldThrowException_whenCurrencyCountCantBeParsed() {
        assertThrows(IllegalArgumentException.class,
                () -> walletService.withdrawMoney(mock(User.class), "walletName", "currencyCount", "creditCard"));
    }

    @Test
    void withdrawMoney_shouldThrowException_whenCurrencyCountLessThenBalance() {
        Wallet wallet = new Wallet();
        wallet.setName("RUB");
        wallet.setBalance(1d);
        User user = new User();
        user.setId(ID);
        String walletName = "RUB";
        String currencyCount = "2";
        String creditCard = "12345";
        when(walletRepository.findByNameAndUserId(walletName, user.getId()))
                .thenReturn(Optional.of(wallet));

        assertThrows(IllegalArgumentException.class,
                () -> walletService.withdrawMoney(user, walletName, currencyCount, creditCard));
    }

    @Test
    void withdrawMoney_shouldThrowException_whenCreditCardNotValid() {
        Wallet wallet = new Wallet();
        wallet.setName("RUB");
        wallet.setBalance(100d);
        User user = new User();
        user.setId(ID);
        String walletName = "RUB";
        String currencyCount = "1";
        String creditCard = "12345";
        when(walletRepository.findByNameAndUserId(walletName, user.getId()))
                .thenReturn(Optional.of(wallet));

        assertThrows(NotValidFieldException.class,
                () -> walletService.withdrawMoney(user, walletName, currencyCount, creditCard));
    }

    @Test
    void withdrawMoney_shouldReturnWallet_whenArgumentsIsOk() {
        Wallet wallet = new Wallet();
        wallet.setName("RUB");
        wallet.setBalance(100d);

        User user = new User();
        user.setId(ID);
        String walletName = "RUB";
        String currencyCount = "1";
        String creditCard = "1234 1234 1234 1234";
        when(walletRepository.findByNameAndUserId(walletName, user.getId()))
                .thenReturn(Optional.of(wallet));

        assertDoesNotThrow(() -> walletService.withdrawMoney(user, walletName, currencyCount, creditCard));
    }
}