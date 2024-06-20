package com.abhinsst.trading_api.service;

import com.abhinsst.trading_api.domain.VerificationType;
import com.abhinsst.trading_api.model.User;
import com.abhinsst.trading_api.model.VerificationCode;

public interface VerificationCodeService {
  VerificationCode sendVerificationCode(User user, VerificationType verificationType);

  VerificationCode getVerificationCodeById(Long id) throws Exception;

  VerificationCode getVerificationCodeByUser(Long userId);

  void deleteVerificationCodeById(VerificationCode verificationCode);

}
