package ru.nidecker.relexTestTask.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nidecker.relexTestTask.entity.CurrencyName;
import ru.nidecker.relexTestTask.entity.enums.Role;
import ru.nidecker.relexTestTask.entity.User;
import ru.nidecker.relexTestTask.exception.DontHaveEnoughRightsException;
import ru.nidecker.relexTestTask.exception.EntityAlreadyExistsException;
import ru.nidecker.relexTestTask.repository.CurrencyNameRepository;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CurrencyNameService {

    private final CurrencyNameRepository currencyNameRepository;

    //TODO: cache for currency names
    public List<String> getAllCurrencyNames() {
        return currencyNameRepository.findAll().stream().map(CurrencyName::getName).toList();
    }

    @Transactional
    public CurrencyName creatCurrency(String currencyName, User user) {
        if (!user.getRoles().contains(Role.ADMIN)) {
            throw new DontHaveEnoughRightsException("You don't have enough rights for this operation");
        }

        List<String> currencyNames = currencyNameRepository.findAll().stream().map(CurrencyName::getName).toList();
        if (currencyNames.contains(currencyName)) {
            throw new EntityAlreadyExistsException(currencyName + " already exists");
        }

        log.info(currencyName + " currency created");
        return currencyNameRepository.save(new CurrencyName(currencyName));
    }
}
