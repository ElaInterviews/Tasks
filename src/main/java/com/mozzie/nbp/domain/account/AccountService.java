package com.mozzie.nbp.domain.account;

import com.mozzie.nbp.domain.exceptions.AccountAlreadyExistsException;
import com.mozzie.nbp.domain.exceptions.AccountNotFoundException;
import com.mozzie.nbp.domain.exceptions.PersonNotAdultException;
import com.mozzie.nbp.domain.exceptions.PeselInvalidException;
import java.time.LocalDate;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;

    public AccountModel createAccount(AccountDto accountDTO) {

        String pesel = accountDTO.getPesel();

        Optional<AccountModel> optionalAccount = accountRepository.findById(pesel);

        if (optionalAccount.isPresent())
            throw new AccountAlreadyExistsException("Konto o peselu: " + pesel + " już istnieje" );

        if (!isPeselValid(pesel))
            throw new PeselInvalidException("Pesel: " + pesel + " nie jest poprawny");

        if (!isAdult(pesel))
            throw new PersonNotAdultException("Osoba o peselu: " + pesel + " nie jest pełnoletnia");


        AccountModel accountModel = new AccountModel();
        accountModel.setPesel(accountDTO.getPesel());
        accountModel.setFirstName(accountDTO.getFirstName());
        accountModel.setLastName(accountDTO.getLastName());
        accountModel.setBalancePLN(accountDTO.getInitialBalancePLN());
        accountRepository.save(accountModel);
        return accountModel;

    }

    public AccountModel getAccountDetails(String pesel) {

        Optional<AccountModel> optionalAccount = accountRepository.findById(pesel);

        if (optionalAccount.isPresent()) {
            return optionalAccount.get();
        } else {
            throw new AccountNotFoundException("Konto o podanym peselu nie istnieje: " + pesel);
        }
    }

    public static boolean isAdult(String pesel) {
            int year = Integer.parseInt(pesel.substring(0, 2));
            int month = Integer.parseInt(pesel.substring(2, 4));
            int day = Integer.parseInt(pesel.substring(4, 6));

            if (month > 80 && month <= 92) {
                month -= 80; // Konwersja numeru miesiąca na odpowiedni dla Javy
                year += 1800;
            } else if (month > 0 && month <= 12) {
                year += 1900;
            } else if (month > 20 && month <= 32) {
                month -= 20;
                year += 2000;
            } else if (month > 40 && month <= 52) {
                month -= 40;
                year += 2100;
            } else if (month > 60 && month <= 72) {
                month -= 60;
                year += 2200;
            } else {
                return false; // Nieprawidłowy miesiąc
            }

            try {
                LocalDate birthDate = LocalDate.of(year, month, day);
                LocalDate currentDate = LocalDate.now();
                LocalDate adultDate = currentDate.minusYears(18);

                return birthDate.isBefore(adultDate) || birthDate.equals(adultDate);
            } catch (Exception e) {
                return false;
            }
    }
    public static boolean isPeselValid(String pesel) {
        // Sprawdź czy ciąg zawiera tylko cyfry i ma długość 11
        if (!pesel.matches("\\d{11}")) {
            return false;
        }

        // Sprawdź poprawność daty urodzenia
        int year = Integer.parseInt(pesel.substring(0, 2));
        int month = Integer.parseInt(pesel.substring(2, 4));
        int day = Integer.parseInt(pesel.substring(4, 6));

        if (month > 80 && month <= 92) {
            year += 1800;
            month -= 80;
        } else if (month > 0 && month <= 12) {
            year += 1900;
        } else if (month > 20 && month <= 32) {
            year += 2000;
            month -= 20;
        } else if (month > 40 && month <= 52) {
            year += 2100;
            month -= 40;
        } else if (month > 60 && month <= 72) {
            year += 2200;
            month -= 60;
        } else {
            return false; // Nieprawidłowy miesiąc
        }

        try {
            LocalDate birthDate = LocalDate.of(year, month, day);
            LocalDate currentDate = LocalDate.now();
            if (birthDate.isAfter(currentDate)) {
                return false; // Data urodzenia w przyszłości
            }
        } catch (Exception e) {
            return false; // Nieprawidłowa data urodzenia
        }

        // Sprawdź poprawność cyfry kontrolnej
        return isControlDigitValid(pesel);
    }

    private static boolean isControlDigitValid(String pesel) {
        int[] weights = {1, 3, 7, 9, 1, 3, 7, 9, 1, 3};
        int sum = 0;
        for (int i = 0; i < 10; i++) {
            sum += Integer.parseInt(pesel.substring(i, i + 1)) * weights[i];
        }

        int controlDigit = Integer.parseInt(pesel.substring(10, 11));
        int modulo = sum % 10;
        int expectedControlDigit = (10 - modulo) % 10;

        return controlDigit == expectedControlDigit;
    }

}
