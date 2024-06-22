package com.abhinsst.trading_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abhinsst.trading_api.model.Watchlist;

public interface WatchlistRepository extends JpaRepository<Watchlist, Long> {

  Watchlist findByUserId(Long userId);

}
