package com.abhinsst.trading_api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abhinsst.trading_api.model.Withdrawal;

public interface WithdrawalRepository extends JpaRepository<Withdrawal, Long>{

  List<Withdrawal> findByUserId(Long userId);

  
}