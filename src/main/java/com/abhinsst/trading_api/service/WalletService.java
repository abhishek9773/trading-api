package com.abhinsst.trading_api.service;

import com.abhinsst.trading_api.model.Order;
import com.abhinsst.trading_api.model.User;
import com.abhinsst.trading_api.model.Wallet;

public interface WalletService {

  Wallet getUserWellet(User user);

  Wallet addBalance(Wallet wallet, Long money);

  Wallet findWalletById(Long id) throws Exception;

  Wallet walletToWalletTransfer(User sander, Wallet receiverWallet, Long amount) throws Exception;

  Wallet payOrderPayment(Order order, User user) throws Exception;
}
