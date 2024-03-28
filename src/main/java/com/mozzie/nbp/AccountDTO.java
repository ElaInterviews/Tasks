package com.mozzie.nbp;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountDTO {
    private String firstName;
    private String lastName;
    private BigDecimal initialBalancePLN;
}
