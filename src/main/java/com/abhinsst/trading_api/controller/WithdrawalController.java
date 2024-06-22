package com.abhinsst.trading_api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abhinsst.trading_api.model.User;
import com.abhinsst.trading_api.model.Wallet;
import com.abhinsst.trading_api.model.WalletTransaction;
import com.abhinsst.trading_api.model.Withdrawal;
import com.abhinsst.trading_api.service.UserService;
import com.abhinsst.trading_api.service.WalletService;
import com.abhinsst.trading_api.service.WalletServiceImpl;
import com.abhinsst.trading_api.service.WithdrawalService;

@RestController
@RequestMapping("/api/withdrawal")
public class WithdrawalController {

  @Autowired
  private WithdrawalService withdrawalService;

  @Autowired
  private WalletService walletService;

  @Autowired
  private UserService userService;

  // @Autowired
  // private WalletTransactionService walletTransactionService;

  @PostMapping("/api/withdrawal/{amount}")
  public ResponseEntity<?> withdrawalRequest(@PathVariable Long amount, @RequestHeader("Authorization") String jwt)
      throws Exception {
    User user = userService.findUserProfileByJwt(jwt);
    Wallet userWallet = walletService.getUserWellet(user);

    Withdrawal withdrawal = withdrawalService.requestWithdrawal(amount, user);
    walletService.addBalance(userWallet, -withdrawal.getAmount());

    return new ResponseEntity<>(withdrawal, HttpStatus.OK);

  }

  @PatchMapping("/api/admin/withdrawal/{id}/proceed/{accept}")
  public ResponseEntity<?> proceedWithdrawal(
      @PathVariable Long id,
      @PathVariable boolean accept,
      @RequestHeader("Authorization") String jwt) throws Exception {
    User user = userService.findUserProfileByJwt(jwt);

    Withdrawal withdrawal = withdrawalService.procedWithdrawal(id, accept);
    Wallet userWallet = walletService.getUserWellet(user);
    if (!accept) {
      walletService.addBalance(userWallet, withdrawal.getAmount());

    }
    return new ResponseEntity<>(withdrawal, HttpStatus.OK);
  }

  @GetMapping("/api/withdrawal")
  public ResponseEntity<List<Withdrawal>> getWithdrawalHistory(@RequestHeader("Authorization") String jwt)
      throws Exception {
    User user = userService.findUserProfileByJwt(jwt);
    List<Withdrawal> withdrawal = withdrawalService.getUserWithdrawalHistory(user);

    return new ResponseEntity<>(withdrawal, HttpStatus.OK);
  }

  public ResponseEntity<List<Withdrawal>> getAllwithdrawalRequest(@RequestHeader("Authorization") String jwt)
      throws Exception {
    User user = userService.findUserProfileByJwt(jwt);

    List<Withdrawal> withdrawal = withdrawalService.getAllWithdrawalRequest();
    return new ResponseEntity<>(withdrawal, HttpStatus.OK);
  }
}
