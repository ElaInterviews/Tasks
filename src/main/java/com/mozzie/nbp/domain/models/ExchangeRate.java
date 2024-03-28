package com.mozzie.nbp.domain.models;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExchangeRate {
    private String currencyCode;
    private BigDecimal rate;
}
