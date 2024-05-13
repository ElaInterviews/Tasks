package com.mozzie.nbp.domain.account;

import com.mozzie.nbp.domain.exceptions.AccountNotFoundException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;

    public AccountModel createAccount(AccountDto accountDTO) {

        Optional<AccountModel> optionalAccount = accountRepository.findById(accountDTO.getPesel());

        if (optionalAccount.isPresent()) {
            throw new AccountNotFoundException("Konto o podanym peselu już istnieje: " + accountDTO.getPesel());
        } else {
            AccountModel accountModel = new AccountModel();
            accountModel.setPesel(accountDTO.getPesel());
            accountModel.setFirstName(accountDTO.getFirstName());
            accountModel.setLastName(accountDTO.getLastName());
            accountModel.setBalancePLN(accountDTO.getInitialBalancePLN());
            accountRepository.save(accountModel);
            return accountModel;
        }
    }

    public AccountModel getAccountDetails(String pesel) {

        Optional<AccountModel> optionalAccount = accountRepository.findById(pesel);

        if (optionalAccount.isPresent()) {
            return optionalAccount.get();
        } else {
            throw new AccountNotFoundException("Konto o podanym peselu nie istnieje: " + pesel);
        }
    }
}
