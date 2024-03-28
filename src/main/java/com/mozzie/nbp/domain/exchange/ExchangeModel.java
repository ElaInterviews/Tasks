package com.mozzie.nbp.domain.exchange;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExchangeModel {
    private String currencyCode;
    private BigDecimal rate;
}
