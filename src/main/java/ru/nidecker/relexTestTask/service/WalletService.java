package ru.nidecker.relexTestTask.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.nidecker.relexTestTask.entity.CurrencyName;
import ru.nidecker.relexTestTask.entity.User;
import ru.nidecker.relexTestTask.entity.Wallet;
import ru.nidecker.relexTestTask.repository.CurrencyNameRepository;
import ru.nidecker.relexTestTask.repository.WalletRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class WalletService {

    private final WalletRepository walletRepository;
    private final CurrencyNameRepository currencyNameRepository;

//    public Wallet createWalletForUser(CurrencyName currencyName, User user) {
//
//    }
}
