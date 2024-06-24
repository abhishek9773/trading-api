package com.abhinsst.trading_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abhinsst.trading_api.exception.UserException;
import com.abhinsst.trading_api.model.PaymentOrder;
import com.abhinsst.trading_api.model.User;
import com.abhinsst.trading_api.response.PaymentResponse;
import com.abhinsst.trading_api.service.PaymentService;
import com.abhinsst.trading_api.service.UserService;
import com.razorpay.RazorpayException;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentMethod;

@RestController
@RequestMapping("/api")
public class PaymentController {

  @Autowired
  private UserService userService;

  @Autowired
  private PaymentService paymentService;

  @PostMapping("/api/payment/{paymentMethod}/amount/{amount}")
  public ResponseEntity<PaymentResponse> paymentHandler(
      @PathVariable PaymentMethod paymentMethod,
      @PathVariable Long amount,
      @RequestHeader("Authorization") String jwt) throws UserException, RazorpayException, StripeException {
    User user = userService.findUserProfileByJwt(jwt);

    PaymentResponse paymentResponse;

    PaymentOrder order = paymentService.createOrder(user, amount, paymentMethod);

    if (paymentMethod.equals(PaymentMethod.RAZORPAY)) {
      paymentResponse = paymentService.createRazorpayPaymentLink(user, amount);

    } else {
      paymentResponse = paymentService.createStripePaymentLink(user, amount, order);
    }

    return new ResponseEntity<>(paymentResponse, HttpStatus.CREATED);
  }

}
