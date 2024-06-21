package com.abhinsst.trading_api.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Coin {

  @Id
  @JsonProperty("id")
  private String id;

  @JsonProperty("symbol")
  private String symbol;

  @JsonProperty("name")
  private String name;

  @JsonProperty("image")
  private String image;

  @JsonProperty("current_price")
  private double currentPrice;

  @JsonProperty("market_cap")
  private long marketCap;

  @JsonProperty("market_cap_rank")
  private int marketCapRank;

  @JsonProperty("fully_diluted_valuation")
  private BigDecimal fullyDilutedValuation;

  @JsonProperty("total_volume")
  private long totalVolume;

  @JsonProperty("high_24h")
  private double high24h;

  @JsonProperty("low_24h")
  private double low24h;

  @JsonProperty("price_change_24h")
  private double priceChange24h;

  @JsonProperty("price_change_percentage_24h")
  private double priceChangePercentage24h;

  @JsonProperty("market_cap_change_24h")
  private Long marketCapChange24h;

  @JsonProperty("market_cap_change_percentage_24h")
  private Long marketCapChangePercentage24h;

  @JsonProperty("circulating_supply")
  private BigDecimal circulatingSupply;

  @JsonProperty("total_supply")
  private BigDecimal totalSupply;

  @JsonProperty("max_supply")
  private BigDecimal maxSupply;

  @JsonProperty("ath")
  private BigDecimal ath;

  @JsonProperty("ath_change_percentage")
  private BigDecimal athChangePercentage;

  @JsonProperty("ath_date")
  private LocalDateTime athDate;

  @JsonProperty("atl")
  private BigDecimal atl;

  @JsonProperty("atl_change_percentage")
  private BigDecimal atlChangePercentage;

  @JsonProperty("atl_date")
  private LocalDateTime atlDate;

  @JsonProperty("roi")
  private String roi;

  @JsonProperty("last_updated")
  private LocalDateTime lastUpdated;

}
