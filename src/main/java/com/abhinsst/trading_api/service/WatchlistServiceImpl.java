package com.abhinsst.trading_api.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abhinsst.trading_api.model.Coin;
import com.abhinsst.trading_api.model.User;
import com.abhinsst.trading_api.model.Watchlist;
import com.abhinsst.trading_api.repository.WatchlistRepository;

@Service
public class WatchlistServiceImpl implements WatchlistService {

  @Autowired
  private WatchlistRepository watchlistRepository;

  @Override
  public Watchlist findUserWatchlist(Long userId) throws Exception {
    Watchlist watchlist = watchlistRepository.findByUserId(userId);
    if (watchlist == null) {
      throw new Exception("watchlist no found");
    }
    return watchlist;

  }

  @Override
  public Watchlist createWatchlist(User user) {
    Watchlist watchlist = new Watchlist();
    watchlist.setUser(user);

    return watchlistRepository.save(watchlist);
  }

  @Override
  public Watchlist findById(Long id) throws Exception {
    Optional<Watchlist> watchlistOptional = watchlistRepository.findById(id);
    if (watchlistOptional.isEmpty()) {
      throw new Exception("WatchList not found");
    }
    return watchlistOptional.get();
  }

  @Override
  public Coin addItemToWatchlist(Coin coin, User user) throws Exception {
    Watchlist watchlist = findUserWatchlist(user.getId());
    if (watchlist.getCoins().contains(coin)) {
      watchlist.getCoins().remove(coin);
    } else {
      watchlist.getCoins().add(coin);
    }
    watchlistRepository.save(watchlist);
    return coin;
  }

}
