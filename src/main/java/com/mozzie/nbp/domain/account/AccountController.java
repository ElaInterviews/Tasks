package com.mozzie.nbp.domain.account;

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
    public ResponseEntity<AccountModel> createAccount(@RequestBody AccountDto accountDTO) {
        AccountModel accountModel = accountService.createAccount(accountDTO);
        return new ResponseEntity<>(accountModel, HttpStatus.CREATED);
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<AccountModel> getAccountDetails(@PathVariable String accountId) {
        AccountModel accountModel = accountService.getAccountDetails(accountId);
        return ResponseEntity.ok(accountModel);
    }
}
