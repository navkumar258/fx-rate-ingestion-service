package com.example.fx.rate.ingestion.service.model;

import java.math.BigDecimal;

public record FXRateRaw(
        String symbol,
        BigDecimal bid,
        BigDecimal ask,
        long timestamp,
        String exchange
) {}
