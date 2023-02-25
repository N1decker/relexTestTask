package ru.nidecker.relexTestTask.controller.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.nidecker.relexTestTask.entity.CurrencyName;
import ru.nidecker.relexTestTask.entity.User;
import ru.nidecker.relexTestTask.service.CurrencyNameService;

@RestController
@RequestMapping(value = "rest/admin")
@RequiredArgsConstructor
@Slf4j
public class CurrencyAdminRestController {

    private final CurrencyNameService currencyNameService;

    @PostMapping(value = "currencies", produces = MediaType.APPLICATION_JSON_VALUE)
    public CurrencyName createCurrency(@AuthenticationPrincipal User user,
                                       String currencyName) {
        return currencyNameService.creatCurrency(currencyName, user);
    }
}
