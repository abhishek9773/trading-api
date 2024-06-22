package com.abhinsst.trading_api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abhinsst.trading_api.model.PaymentDetails;
import com.abhinsst.trading_api.model.User;
import com.abhinsst.trading_api.repository.PaymentDetailsRepository;

@Service
public class PaymentDetailsServiceImpl implements PaymentDetailsService {

  @Autowired
  private PaymentDetailsRepository paymentDetailsRepository;

  @Override
  public PaymentDetails addPaymentDetails(String accountNumber, String acountHolder, String ifsc, String backName,
      User user) {
        PaymentDetails paymentDetails = new PaymentDetails();
        paymentDetails.setAccountHolerName(acountHolder);
        paymentDetails.setAccountNumber(accountNumber);
        paymentDetails.setBackName(backName);
        paymentDetails.setUser(user);
        paymentDetails.setIfsc(ifsc);

        return paymentDetailsRepository.save(paymentDetails);
  }

  @Override
  public PaymentDetails getUserPaymentDetails(User user) {
    return paymentDetailsRepository.findByUserId(user.getId());
  }

}
