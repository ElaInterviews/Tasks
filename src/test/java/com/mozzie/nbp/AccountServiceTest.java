package com.mozzie.nbp;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.mozzie.nbp.domain.account.AccountDto;
import com.mozzie.nbp.domain.account.AccountModel;
import com.mozzie.nbp.domain.account.AccountRepository;
import com.mozzie.nbp.domain.account.AccountService;
import com.mozzie.nbp.domain.exchange.ExchangeService;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class AccountServiceTest {

    @Test
    void createAccount_SuccessfullyCreatesAccount() {
        // Given
        AccountRepository accountRepository = mock(AccountRepository.class);
        ExchangeService exchangeService = mock(ExchangeService.class);

        AccountDto accountDto = new AccountDto("John", "Doe", new BigDecimal("1000"), null);
        BigDecimal exchangeRate = new BigDecimal("4.0");
        when(exchangeService.getUsdRate()).thenReturn("4.0");

        AccountService accountService = new AccountService(accountRepository, exchangeService);

        // When
        AccountModel createdAccount = accountService.createAccount(accountDto);

        // Then
        assertNotNull(createdAccount);
        assertEquals("John", createdAccount.getFirstName());
        assertEquals("Doe", createdAccount.getLastName());
        assertEquals(new BigDecimal("1000"), createdAccount.getBalancePLN());

        BigDecimal expectedBalanceUSD = new BigDecimal("1000").divide(exchangeRate, 2, RoundingMode.HALF_UP);
        assertEquals(expectedBalanceUSD, createdAccount.getBalanceUSD());
        verify(accountRepository, times(1)).save(any(AccountModel.class));
    }

    @Test
    void getAccountDetails_ExistingAccount_ReturnsAccount() {
        // Given
        AccountRepository accountRepository = mock(AccountRepository.class);
        AccountModel mockAccount = new AccountModel();
        String accountId = UUID.randomUUID().toString();
        mockAccount.setAccountId(accountId);
        when(accountRepository.findById(accountId)).thenReturn(Optional.of(mockAccount));

        AccountService accountService = new AccountService(accountRepository, null);

        // When
        AccountModel retrievedAccount = accountService.getAccountDetails(accountId);

        // Then
        assertNotNull(retrievedAccount);
        assertEquals(accountId, retrievedAccount.getAccountId());
        verify(accountRepository, times(1)).findById(accountId);
    }

    @Test
    void getAccountDetails_NonexistentAccount_ThrowsException() {
        // Given
        AccountRepository accountRepository = mock(AccountRepository.class);
        when(accountRepository.findById(anyString())).thenReturn(Optional.empty());

        AccountService accountService = new AccountService(accountRepository, null);

        // When, Then
        assertThrows(RuntimeException.class, () -> accountService.getAccountDetails("nonexistentAccountId"));
        verify(accountRepository, times(1)).findById("nonexistentAccountId");
    }
}
