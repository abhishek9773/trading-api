package com.abhinsst.trading_api.service;

import com.abhinsst.trading_api.model.TwoFactorAuth;
import com.abhinsst.trading_api.model.TwoFactorOTP;
import com.abhinsst.trading_api.model.User;

public interface TwoFactorOptService {

  TwoFactorOTP createTwoFactorOpt(User user, String otp, String jwt);

  TwoFactorOTP findByUser(Long userId);

  TwoFactorOTP findById(String id);

  boolean verifyTwoFactorOpt(TwoFactorOTP towFactorOTP, String otp);

  void deleteTwoFactorOtp(TwoFactorOTP towFactorOTP);

}
