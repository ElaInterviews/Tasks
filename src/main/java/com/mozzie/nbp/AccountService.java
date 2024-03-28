package com.mozzie.nbp;

import com.mozzie.nbp.domain.models.Account;
import com.mozzie.nbp.domain.models.AccountRepository;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    public Account createAccount(String firstName, String lastName, BigDecimal initialBalancePLN) {

        String accountId = UUID.randomUUID().toString();

        Account account = new Account();
        account.setAccountId(accountId);
        account.setFirstName(firstName);
        account.setLastName(lastName);
        account.setBalancePLN(initialBalancePLN);

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
