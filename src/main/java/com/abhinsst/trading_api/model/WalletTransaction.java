package com.abhinsst.trading_api.model;

import java.time.LocalDate;

import com.abhinsst.trading_api.domain.WalletTransactionType;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Entity
@Data
public class WalletTransaction {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @ManyToOne
  private Wallet wallet;

  private WalletTransactionType type;

  private LocalDate date;

  private String transferId;

  private String purpose;

  private Long amount;

}
