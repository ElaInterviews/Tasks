package com.mozzie.nbp;

import com.mozzie.nbp.domain.models.Account;
import com.mozzie.nbp.domain.models.AccountRepository;
import com.mozzie.nbp.domain.models.ExchangeRateService;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final ExchangeRateService exchangeRateService;

    public Account createAccount(AccountDTO accountDTO) {

        String accountId = UUID.randomUUID().toString();

        Account account = new Account();
        account.setAccountId(accountId);
        account.setFirstName(accountDTO.getFirstName());
        account.setLastName(accountDTO.getLastName());
        account.setBalancePLN(accountDTO.getInitialBalancePLN().multiply(
            new BigDecimal(exchangeRateService.getUSDRateFromNbp())));

        accountRepository.save(account);

        return account;
    }

    public Account getAccountDetails(String accountId) {

        Optional<Account> optionalAccount = accountRepository.findById(accountId);

        if (optionalAccount.isPresent()) {
            return optionalAccount.get(); // Zwrócenie danych o koncie, jeśli istnieje
        } else {
            throw new RuntimeException("Konto o podanym identyfikatorze nie istnieje.");
        }
    }
}
