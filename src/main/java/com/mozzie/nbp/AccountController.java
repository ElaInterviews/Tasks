package com.mozzie.nbp;

import com.mozzie.nbp.domain.models.Account;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/accounts")
public class AccountController {
    private final AccountService accountService;

    @PostMapping("/create")
    public ResponseEntity<Account> createAccount(@RequestParam String firstName,
                                                 @RequestParam String lastName,
                                                 @RequestParam BigDecimal initialBalancePLN) {
        Account account = accountService.createAccount(firstName, lastName, initialBalancePLN);
        return new ResponseEntity<>(account, HttpStatus.CREATED);
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<Account> getAccountDetails(@PathVariable String accountId) {
        Account account = accountService.getAccountDetails(accountId);
        return ResponseEntity.ok(account);
    }
}
