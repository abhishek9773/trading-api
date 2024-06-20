package com.abhinsst.trading_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abhinsst.trading_api.model.TwoFactorOTP;

public interface TwoFactorOtpRepository extends JpaRepository<TwoFactorOTP, String> {

  TwoFactorOTP findByUserId(Long userId);

}
