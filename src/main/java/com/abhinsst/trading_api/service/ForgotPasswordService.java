package com.abhinsst.trading_api.service;

import com.abhinsst.trading_api.domain.VerificationType;
import com.abhinsst.trading_api.model.ForgotPasswordToken;
import com.abhinsst.trading_api.model.User;

public interface ForgotPasswordService {

  ForgotPasswordToken createToken(User user, String id, String otp, VerificationType verificationType, String sendTo);

  ForgotPasswordToken findById(String id);

  ForgotPasswordToken findByUser(Long userId);

  void deleteToken(ForgotPasswordToken token);

}
