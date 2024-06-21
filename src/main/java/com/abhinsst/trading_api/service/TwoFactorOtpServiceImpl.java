package com.abhinsst.trading_api.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abhinsst.trading_api.model.TwoFactorOTP;
import com.abhinsst.trading_api.model.User;
import com.abhinsst.trading_api.repository.TwoFactorOtpRepository;

@Service
public class TwoFactorOtpServiceImpl implements TwoFactorOptService {

  @Autowired
  private TwoFactorOtpRepository twoFactorOtpRepository;

  @Override
  public TwoFactorOTP createTwoFactorOpt(User user, String otp, String jwt) {
    UUID uuid = UUID.randomUUID();
    String id = uuid.toString();
    TwoFactorOTP twoFactorOTP = new TwoFactorOTP();
    twoFactorOTP.setOtp(otp);
    twoFactorOTP.setJwt(jwt);
    twoFactorOTP.setId(id);
    twoFactorOTP.setUser(user);
    return twoFactorOtpRepository.save(twoFactorOTP);

  }

  @Override
  public TwoFactorOTP findByUser(Long userId) {
    return twoFactorOtpRepository.findByUserId(userId);
  }

  @Override
  public TwoFactorOTP findById(String id) {
    Optional<TwoFactorOTP> opt = twoFactorOtpRepository.findById(id);
    return opt.orElse(null);
  }

  @Override
  public boolean verifyTwoFactorOpt(TwoFactorOTP towFactorOTP, String otp) {
    return towFactorOTP.getOtp().equals(otp);
  }

  @Override
  public void deleteTwoFactorOtp(TwoFactorOTP towFactorOTP) {
    twoFactorOtpRepository.delete(towFactorOTP);
  }

}
