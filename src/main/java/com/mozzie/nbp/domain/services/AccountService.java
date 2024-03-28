package com.mozzie.nbp.domain.services;

import com.mozzie.nbp.domain.DTOs.AccountDto;
import com.mozzie.nbp.domain.models.Account;
import com.mozzie.nbp.domain.AccountRepository;
import com.mozzie.nbp.domain.services.ExchangeRateService;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final ExchangeRateService exchangeRateService;

    public Account createAccount(AccountDto accountDTO) {

        String accountId = UUID.randomUUID().toString();

        Account account = new Account();
        account.setAccountId(accountId);
        account.setFirstName(accountDTO.getFirstName());
        account.setLastName(accountDTO.getLastName());
        account.setBalancePLN(accountDTO.getInitialBalancePLN());
        account.setBalanceUSD(accountDTO.getInitialBalancePLN().divide(
            new BigDecimal(exchangeRateService.getUsdRate()), 2, RoundingMode.HALF_UP));
        accountRepository.save(account);

        return account;
    }

    public Account getAccountDetails(String accountId) {

        Optional<Account> optionalAccount = accountRepository.findById(accountId);

        if (optionalAccount.isPresent()) {
            return optionalAccount.get();
        } else {
            throw new RuntimeException("Konto o podanym identyfikatorze nie istnieje.");
        }
    }
}
