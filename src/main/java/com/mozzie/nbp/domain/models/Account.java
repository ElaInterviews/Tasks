package com.mozzie.nbp.domain.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Account {
    @Id
    private String accountId;
    private String firstName;
    private String lastName;
    private BigDecimal balancePLN;
}
