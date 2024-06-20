package com.abhinsst.trading_api.model;

import com.abhinsst.trading_api.domain.VerificationType;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Entity
@Data
public class ForgotPasswordToken {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private String id;

  @OneToOne
  private User user;

  private String otp;

  private VerificationType VerficationType;

  private String sendTo;

}
