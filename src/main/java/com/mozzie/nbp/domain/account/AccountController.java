package com.mozzie.nbp.domain.account;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @PostMapping("/create")
    public ResponseEntity<String> createAccount(@RequestBody AccountDto accountDTO) {
        String accountModel = accountService.createAccount(accountDTO);
        return new ResponseEntity<>(accountModel, HttpStatus.CREATED);
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<AccountModel> getAccountDetails(@PathVariable String accountId) {
        AccountModel accountModel = accountService.getAccountDetails(accountId);
        return ResponseEntity.ok(accountModel);
    }
}
