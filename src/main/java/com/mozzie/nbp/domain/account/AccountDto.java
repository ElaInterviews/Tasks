package com.mozzie.nbp.domain.account;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AccountDto {
    @NonNull
    private String pesel;
    @NonNull
    private String firstName;
    @NonNull
    private String lastName;
    @NonNull
    private BigDecimal initialBalancePLN;
}
