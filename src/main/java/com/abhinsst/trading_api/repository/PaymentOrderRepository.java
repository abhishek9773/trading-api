package com.abhinsst.trading_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abhinsst.trading_api.model.PaymentOrder;

public interface PaymentOrderRepository extends JpaRepository<PaymentOrder, Long> {

}
