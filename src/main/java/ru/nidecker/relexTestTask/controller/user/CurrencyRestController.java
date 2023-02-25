package ru.nidecker.relexTestTask.controller.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.nidecker.relexTestTask.service.CurrencyNameService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "rest")
@RequiredArgsConstructor
public class CurrencyRestController {

    private final CurrencyNameService currencyNameService;

    @GetMapping(value = "currencies", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<String> getCurrencies() {
        return currencyNameService.getAllCurrencyNames();
    }
}
