package com.mozzie.nbp.domain.exchange;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExchangeModel {
    private String currencyCode;
    private Double rate;
}
