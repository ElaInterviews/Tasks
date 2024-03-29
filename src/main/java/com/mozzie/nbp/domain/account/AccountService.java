package com.mozzie.nbp.domain.account;

import com.mozzie.nbp.domain.exceptions.AccountNotFoundException;
import com.mozzie.nbp.domain.exchange.ExchangeService;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final ExchangeService exchangeService;

    public AccountModel createAccount(AccountDto accountDTO) {

        String accountId = UUID.randomUUID().toString();

        AccountModel accountModel = new AccountModel();
        accountModel.setAccountId(accountId);
        accountModel.setFirstName(accountDTO.getFirstName());
        accountModel.setLastName(accountDTO.getLastName());
        accountModel.setBalancePLN(accountDTO.getInitialBalancePLN());
        accountModel.setBalanceUSD(accountDTO.getInitialBalancePLN().divide(
            new BigDecimal(exchangeService.getUsdRate()), 2, RoundingMode.HALF_UP));
        accountRepository.save(accountModel);

        return accountModel;
    }

    public AccountModel getAccountDetails(String accountId) {

        Optional<AccountModel> optionalAccount = accountRepository.findById(accountId);

        if (optionalAccount.isPresent()) {
            return optionalAccount.get();
        } else {
            throw new AccountNotFoundException("Konto o podanym identyfikatorze nie istnieje: " + accountId);
        }
    }
}
