package com.mozzie.nbp.domain.models;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Account {
    private String accountId;
    private String firstName;
    private String lastName;
    private BigDecimal balancePLN;
}
