package ru.nidecker.relexTestTask.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;
import ru.nidecker.relexTestTask.entity.User;
import ru.nidecker.relexTestTask.entity.Wallet;
import ru.nidecker.relexTestTask.exception.EntityAlreadyExistsException;
import ru.nidecker.relexTestTask.repository.WalletRepository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class WalletService {

    public static final String WALLET_NOT_FOUND = "Wallet %s not found on your account or did you make a mistake in the name";
    public static final String CURRENCY_NOT_FOUND = "Currency by name - %s not found";

    private final WalletRepository walletRepository;
    private final CurrencyNameService currencyNameService;

    public Map<String, Double> getAllWalletsName(User user) {
        return walletRepository.findAllByUserId(user.getId())
                .stream()
                .collect(Collectors.toMap(Wallet::getName, Wallet::getBalance));
    }

    @Transactional
    public Wallet createWalletForUser(String currencyName,
                                      User user) {
        List<String> allCurrencyNames = currencyNameService.getAllCurrencyNames();

        log.info(allCurrencyNames.toString());

        if (!allCurrencyNames.contains(currencyName)) {
            throw new NotFoundException(String.format(CURRENCY_NOT_FOUND, currencyName));
        }

        if (walletRepository.findByNameAndUserId(currencyName, user.getId()).isPresent()) {
            throw new EntityAlreadyExistsException(currencyName + " wallet already created");
        }

        return walletRepository.save(new Wallet(currencyName, user, 0d));
    }

    @Transactional
    public Wallet topUpBalance(String walletName,
                               String balance,
                               User user) {
        Wallet wallet =
                walletRepository.findByNameAndUserId(walletName, user.getId())
                        .orElseThrow(() ->
                                new NotFoundException(String.format(WALLET_NOT_FOUND, walletName)));

        double parsedBalance;
        try {
            parsedBalance = Double.parseDouble(balance);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Enter a number");
        }

        wallet.setBalance(parsedBalance + wallet.getBalance());

        return walletRepository.save(wallet);
    }
}
