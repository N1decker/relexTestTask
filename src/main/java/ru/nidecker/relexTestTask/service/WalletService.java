package ru.nidecker.relexTestTask.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;
import ru.nidecker.relexTestTask.dto.WalletDTO;
import ru.nidecker.relexTestTask.entity.User;
import ru.nidecker.relexTestTask.entity.Wallet;
import ru.nidecker.relexTestTask.entity.WalletAudit;
import ru.nidecker.relexTestTask.entity.enums.Role;
import ru.nidecker.relexTestTask.exception.DontHaveEnoughRightsException;
import ru.nidecker.relexTestTask.exception.EntityAlreadyExistsException;
import ru.nidecker.relexTestTask.exception.NotValidFieldException;
import ru.nidecker.relexTestTask.repository.WalletAuditRepository;
import ru.nidecker.relexTestTask.repository.WalletRepository;

import java.time.LocalDate;
import java.util.List;

import static ru.nidecker.relexTestTask.entity.enums.WalletAuditType.*;
import static ru.nidecker.relexTestTask.util.ValidationUtil.isValidCreditCard;
import static ru.nidecker.relexTestTask.util.ValidationUtil.isValidWalletAddress;

@Service
@Slf4j
@RequiredArgsConstructor
public class WalletService {

    public static final String WALLET_NOT_FOUND = "Wallet '%s' not found on your account or did you make a mistake in the name";
    public static final String CURRENCY_NOT_FOUND = "Currency by name '%s' not found";

    private final WalletRepository walletRepository;

    public final WalletAuditRepository walletAuditRepository;
    private final CurrencyNameService currencyNameService;

    public List<WalletDTO> getAllWalletsForUser(User user) {
        return walletRepository.findAllByUserId(user.getId())
                .stream()
                .map(wallet -> new WalletDTO(wallet.getName(), wallet.getBalance()))
                .toList();
    }

    public Wallet getWalletByUserAndWalletName(User user,
                                               String walletName) {
        return walletRepository.findByNameAndUserId(walletName, user.getId())
                .orElseThrow(() ->
                        new NotFoundException(String.format(WALLET_NOT_FOUND, walletName)));
    }

    @Transactional
    public Wallet createWalletForUser(String currencyName,
                                      User user) {
        List<String> allCurrencyNames = currencyNameService.getAllCurrencyNames();

        if (!allCurrencyNames.contains(currencyName)) {
            throw new NotFoundException(String.format(CURRENCY_NOT_FOUND, currencyName));
        }

        if (walletRepository.findByNameAndUserId(currencyName, user.getId()).isPresent()) {
            throw new EntityAlreadyExistsException(currencyName + " wallet already created");
        }

        walletAuditRepository.save(new WalletAudit(CREATE, user.getEmail(), currencyName, LocalDate.now()));

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

        walletAuditRepository.save(new WalletAudit(TOPUP, user.getEmail(), walletName, LocalDate.now()));

        return walletRepository.save(wallet);
    }

    public Wallet withdrawMoney(User user, String walletName, String currencyCount, String creditCardOrWalletAddress) {

        double parsedBalance;
        try {
            parsedBalance = Double.parseDouble(currencyCount);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Enter a number");
        }

        Wallet wallet = getWalletByUserAndWalletName(user, walletName);

        if (wallet.getBalance() < parsedBalance) {
            throw new IllegalArgumentException("Insufficient funds in the " + walletName + " wallet");
        }

        if (!isValidCreditCard(creditCardOrWalletAddress) || !isValidWalletAddress(creditCardOrWalletAddress)) {
                throw new NotValidFieldException(creditCardOrWalletAddress + " is invalid");
        }

        wallet.setBalance(wallet.getBalance() - parsedBalance);

        walletAuditRepository.save(new WalletAudit(WITHDRAW, user.getEmail(), walletName, LocalDate.now()));

        return walletRepository.save(wallet);
    }

    public WalletDTO getCurrencySum(User user, String currencyName) {
        if (!user.getRoles().contains(Role.ADMIN)) {
            throw new DontHaveEnoughRightsException("You don't have enough rights for this operation");
        }

        return new WalletDTO(currencyName, walletRepository.getSumOfCurrenciesByWalletName(currencyName));
    }
}
