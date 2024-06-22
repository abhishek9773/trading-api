package com.abhinsst.trading_api.service;

import java.util.List;

import com.abhinsst.trading_api.model.User;
import com.abhinsst.trading_api.model.Withdrawal;

public interface WithdrawalService {

  Withdrawal requestWithdrawal(Long amount, User user);

  Withdrawal procedWithdrawal(Long withDrawalId, boolean accept) throws Exception;

  List<Withdrawal> getUserWithdrawalHistory(User user);

  List<Withdrawal> getAllWithdrawalRequest();

}