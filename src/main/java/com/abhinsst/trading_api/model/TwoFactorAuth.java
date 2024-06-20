package com.abhinsst.trading_api.model;

import com.abhinsst.trading_api.domain.VerificationType;

import lombok.Data;

@Data
public class TwoFactorAuth {
  private boolean isEnable = false;

  private VerificationType sendTo;

}