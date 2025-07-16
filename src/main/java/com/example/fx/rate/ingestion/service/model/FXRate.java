package com.example.fx.rate.ingestion.service.model;

import java.math.BigDecimal;

public record FXRate(
        String currencyPair,
        BigDecimal bid,
        BigDecimal ask,
        BigDecimal mid,
        long timestamp
) {}
