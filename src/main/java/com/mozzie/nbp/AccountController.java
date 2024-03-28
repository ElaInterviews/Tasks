package com.mozzie.nbp;

import com.mozzie.nbp.domain.models.Account;
import java.math.BigDecimal;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/create")
    public ResponseEntity<Account> createAccount(@RequestBody AccountDTO accountDTO) {
        Account account = accountService.createAccount(accountDTO);
        return new ResponseEntity<>(account, HttpStatus.CREATED);
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<Account> getAccountDetails(@PathVariable String accountId) {
        Account account = accountService.getAccountDetails(accountId);
        return ResponseEntity.ok(account);
    }
}
