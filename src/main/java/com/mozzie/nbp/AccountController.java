package com.mozzie.nbp;

import com.mozzie.nbp.domain.DTOs.AccountDto;
import com.mozzie.nbp.domain.models.Account;
import com.mozzie.nbp.domain.services.AccountService;
import com.mozzie.nbp.domain.services.ExchangeRateService;
import java.math.BigDecimal;
import java.math.RoundingMode;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AccountController {
    private final AccountService accountService;
    private final ExchangeRateService exchangeRateService;

    public AccountController(AccountService accountService, ExchangeRateService exchangeRateService) {
        this.accountService = accountService;
        this.exchangeRateService = exchangeRateService;
    }

    @PostMapping("/accounts/create")
    public ResponseEntity<Account> createAccount(@RequestBody AccountDto accountDTO) {
        Account account = accountService.createAccount(accountDTO);
        return new ResponseEntity<>(account, HttpStatus.CREATED);
    }

    @GetMapping("/accounts/{accountId}")
    public ResponseEntity<Account> getAccountDetails(@PathVariable String accountId) {
        Account account = accountService.getAccountDetails(accountId);
        return ResponseEntity.ok(account);
    }

    @GetMapping("/pln-to-usd/{amountPln}")
    @SneakyThrows(NumberFormatException.class)
    public ResponseEntity<BigDecimal> getAmountAmericanDollars(@PathVariable BigDecimal amountPln) {
        BigDecimal exchangeRate = new BigDecimal(exchangeRateService.getUsdRate());
        BigDecimal amountUsd = amountPln.multiply(exchangeRate);
        return ResponseEntity.ok(amountUsd);
    }

    @GetMapping("/usd-to-pln/{amountUsd}")
    @SneakyThrows(NumberFormatException.class)
    public ResponseEntity<BigDecimal> getAmountPolishZlotych(@PathVariable BigDecimal amountUsd) {
        BigDecimal exchangeRate = new BigDecimal(exchangeRateService.getUsdRate());
        BigDecimal amountPln = amountUsd.divide(exchangeRate, 2, RoundingMode.HALF_UP);
        return ResponseEntity.ok(amountPln);
    }
}
