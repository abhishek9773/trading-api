package com.abhinsst.trading_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abhinsst.trading_api.model.VerificationCode;

public interface VerificationCodeRepository extends JpaRepository<VerificationCode, Long> {

  public VerificationCode findByUserId(Long userId);

}
