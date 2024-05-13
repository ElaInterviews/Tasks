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
    public ResponseEntity<AccountModel> createAccount(@RequestBody AccountDto accountDTO) {
        AccountModel accountModel = accountService.createAccount(accountDTO);
        return new ResponseEntity<>(accountModel, HttpStatus.CREATED);
    }

    @GetMapping("/{pesel}")
    public ResponseEntity<AccountModel> getAccountDetails(@PathVariable String pesel) {
        AccountModel accountModel = accountService.getAccountDetails(pesel);
        return ResponseEntity.ok(accountModel);
    }
}
