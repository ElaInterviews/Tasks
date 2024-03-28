package com.mozzie.nbp.domain.DTOs;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class AccountDto {
    @NonNull
    private String firstName;
    @NonNull
    private String lastName;
    @NonNull
    private BigDecimal initialBalancePLN;
    private BigDecimal initialBalanceUSD;
}
