package com.example.fx.rate.ingestion.service.model;

import java.math.BigDecimal;
import java.util.Objects;

public class FXRateRaw {

  private String symbol;
  private BigDecimal bid;
  private BigDecimal ask;
  private long timestamp;
  private String exchange;

  public FXRateRaw() {}

  public FXRateRaw(String symbol, BigDecimal bid, BigDecimal ask, long timestamp, String exchange) {
    this.symbol = symbol;
    this.bid = bid;
    this.ask = ask;
    this.timestamp = timestamp;
    this.exchange = exchange;
  }

  public String getSymbol() {
    return symbol;
  }

  public void setSymbol(String symbol) {
    this.symbol = symbol;
  }

  public BigDecimal getBid() {
    return bid;
  }

  public void setBid(BigDecimal bid) {
    this.bid = bid;
  }

  public BigDecimal getAsk() {
    return ask;
  }

  public void setAsk(BigDecimal ask) {
    this.ask = ask;
  }

  public long getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(long timestamp) {
    this.timestamp = timestamp;
  }

  public String getExchange() {
    return exchange;
  }

  public void setExchange(String exchange) {
    this.exchange = exchange;
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof FXRateRaw fxRateRaw)) return false;
    return Objects.equals(getSymbol(), fxRateRaw.getSymbol())
            && Objects.equals(getBid(), fxRateRaw.getBid())
            && Objects.equals(getAsk(), fxRateRaw.getAsk())
            && Objects.equals(getTimestamp(), fxRateRaw.getTimestamp())
            && Objects.equals(getExchange(), fxRateRaw.getExchange());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getSymbol(), getBid(), getAsk(), getTimestamp(), getExchange());
  }
}
