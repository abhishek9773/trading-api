package com.abhinsst.trading_api.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abhinsst.trading_api.domain.VerificationType;
import com.abhinsst.trading_api.model.ForgotPasswordToken;
import com.abhinsst.trading_api.model.User;
import com.abhinsst.trading_api.repository.ForgotPasswordRepository;

@Service
public class ForgotPasswordServiceImpl implements ForgotPasswordService {

  @Autowired
  private ForgotPasswordRepository forgotPasswordRepository;

  @Override
  public ForgotPasswordToken createToken(User user, String id, String otp, VerificationType verificationType,
      String sendTo) {
    ForgotPasswordToken token = new ForgotPasswordToken();
    token.setUser(user);
    token.setSendTo(sendTo);
    token.setOtp(otp);
    token.setVerficationType(verificationType);
    token.setId(id);

    return forgotPasswordRepository.save(token);
  }

  @Override
  public ForgotPasswordToken findById(String id) {
    Optional<ForgotPasswordToken> token = forgotPasswordRepository.findById(id);
    return token.orElse(null);
  }

  @Override
  public ForgotPasswordToken findByUser(Long userId) {
    return forgotPasswordRepository.findByUserId(userId);
  }

  @Override
  public void deleteToken(ForgotPasswordToken token) {
    forgotPasswordRepository.delete(token);

  }

}
