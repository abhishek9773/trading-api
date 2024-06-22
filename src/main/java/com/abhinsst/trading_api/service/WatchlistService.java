package com.abhinsst.trading_api.service;

import com.abhinsst.trading_api.model.Coin;
import com.abhinsst.trading_api.model.User;
import com.abhinsst.trading_api.model.Watchlist;

public interface WatchlistService {
  Watchlist findUserWatchlist(Long userId) throws Exception;

  Watchlist createWatchlist(User user);

  Watchlist findById(Long id) throws Exception;

  Coin addItemToWatchlist(Coin coin, User user) throws Exception;

}
