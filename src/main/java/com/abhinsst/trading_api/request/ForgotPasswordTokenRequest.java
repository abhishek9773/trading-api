package com.abhinsst.trading_api.request;

import com.abhinsst.trading_api.domain.VerificationType;

import lombok.Data;

@Data
public class ForgotPasswordTokenRequest {
  private String sandTo;
  private VerificationType verificationType;

}
