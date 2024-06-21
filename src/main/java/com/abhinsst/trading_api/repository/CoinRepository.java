package com.abhinsst.trading_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abhinsst.trading_api.model.Coin;

public interface CoinRepository extends JpaRepository<Coin, String> {

}
