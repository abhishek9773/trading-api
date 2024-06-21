package com.abhinsst.trading_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abhinsst.trading_api.model.Wallet;

public interface WalletRepository extends JpaRepository<Wallet, Long> {

  Wallet findByUserId(Long userId);

}
