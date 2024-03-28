package com.mozzie.nbp;

import com.mozzie.nbp.domain.DTOs.AccountDto;
import com.mozzie.nbp.domain.models.Account;
import com.mozzie.nbp.domain.services.AccountService;
import com.mozzie.nbp.domain.services.ExchangeRateService;
import java.math.BigDecimal;
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
    public ResponseEntity<BigDecimal> getAmountAmericanDollars(@PathVariable BigDecimal amountPln) {
        BigDecimal amountUsd = exchangeRateService.getAmountUsd(amountPln);
        return ResponseEntity.ok(amountUsd);
    }

    @GetMapping("/usd-to-pln/{amountUsd}")
    public ResponseEntity<BigDecimal> getAmountPolishZlotych(@PathVariable BigDecimal amountUsd) {
        BigDecimal amountPln = exchangeRateService.getAmountPln(amountUsd);
        return ResponseEntity.ok(amountPln);
    }
}
