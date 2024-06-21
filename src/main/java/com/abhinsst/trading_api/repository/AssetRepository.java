package com.abhinsst.trading_api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abhinsst.trading_api.model.Asset;

public interface AssetRepository extends JpaRepository<Asset, Long> {

  List<Asset> findByUserId(Long userId);

  Asset findByUserIdAndCoinId(Long userId, String coinId);

}
