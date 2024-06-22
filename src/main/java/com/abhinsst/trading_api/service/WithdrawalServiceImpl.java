package com.abhinsst.trading_api.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abhinsst.trading_api.domain.WithdrawalStatus;
import com.abhinsst.trading_api.model.User;
import com.abhinsst.trading_api.model.Withdrawal;
import com.abhinsst.trading_api.repository.WithdrawalRepository;

@Service
public class WithdrawalServiceImpl implements WithdrawalService {

  @Autowired
  private WithdrawalRepository withdrawalRepository;

  @Override
  public Withdrawal requestWithdrawal(Long amount, User user) {
    Withdrawal withdrawal = new Withdrawal();
    withdrawal.setAmount(amount);
    withdrawal.setUser(user);
    withdrawal.setStatus(WithdrawalStatus.PANDING);
    return withdrawalRepository.save(withdrawal);
  }

  @Override
  public Withdrawal procedWithdrawal(Long withDrawalId, boolean accept) throws Exception {
    Optional<Withdrawal> withdrawal = withdrawalRepository.findById(withDrawalId);
    if (withdrawal.isEmpty()) {
      throw new Exception("withdrawal not found");
    }
    Withdrawal withdrawal1 = withdrawal.get();
    withdrawal1.setDate(LocalDateTime.now());
    if (accept) {
      withdrawal1.setStatus(WithdrawalStatus.SUCCESS);
    } else {
      withdrawal1.setStatus(WithdrawalStatus.PANDING);
    }
    return withdrawalRepository.save(withdrawal1);
  }

  @Override
  public List<Withdrawal> getUserWithdrawalHistory(User user) {
    return withdrawalRepository.findByUserId(user.getId());
  }

  @Override
  public List<Withdrawal> getAllWithdrawalRequest() {
    return withdrawalRepository.findAll();
  }

}
