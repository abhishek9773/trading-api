package com.abhinsst.trading_api.service;

import com.abhinsst.trading_api.model.PaymentDetails;
import com.abhinsst.trading_api.model.User;

public interface PaymentDetailsService {

  public PaymentDetails addPaymentDetails(String accountNumber, String acountHolder, String ifsc, String backName,
      User user);

  public PaymentDetails getUserPaymentDetails(User user);

}
