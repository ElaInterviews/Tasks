package com.mozzie.nbp.domain.exchange;

import java.math.BigDecimal;
import java.math.RoundingMode;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/exchange/")
public class ExchangeController {
    private final ExchangeService exchangeService;

    public ExchangeController(ExchangeService exchangeService) {
        this.exchangeService = exchangeService;
    }

    @GetMapping("/usd-pln/{amountUsd}")
    @SneakyThrows(NumberFormatException.class)
    public ResponseEntity<BigDecimal> getAmountAmericanDollars(@PathVariable BigDecimal amountUsd) {
        BigDecimal exchangeRate = new BigDecimal(exchangeService.getUsdRate());
        BigDecimal amountPln = amountUsd.multiply(exchangeRate);
        return ResponseEntity.ok(amountPln);
    }

    @GetMapping("/pln-usd/{amountPln}")
    @SneakyThrows(NumberFormatException.class)
    public ResponseEntity<BigDecimal> getAmountPolishZlotych(@PathVariable BigDecimal amountPln) {
        BigDecimal exchangeRate = new BigDecimal(exchangeService.getUsdRate());
        BigDecimal amountUsd = amountPln.divide(exchangeRate, 2, RoundingMode.HALF_UP);
        return ResponseEntity.ok(amountUsd);
    }
}
