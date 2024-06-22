package com.abhinsst.trading_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abhinsst.trading_api.model.PaymentDetails;

public interface PaymentDetailsRepository extends JpaRepository<PaymentDetails, Long> {

  PaymentDetails findByUserId(Long userId);

}
