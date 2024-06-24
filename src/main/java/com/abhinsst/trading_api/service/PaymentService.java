package com.abhinsst.trading_api.service;

import com.abhinsst.trading_api.domain.PaymentMethod;
import com.abhinsst.trading_api.model.PaymentOrder;
import com.abhinsst.trading_api.model.User;
import com.abhinsst.trading_api.response.PaymentResponse;
import com.razorpay.RazorpayException;

public interface PaymentService {

  PaymentOrder createOrder(User user, Long amount, PaymentMethod paymentMethod);

  PaymentOrder getPaymentOrderById(Long id) throws Exception;

  Boolean ProccedPaymentOrder(PaymentOrder paymentOrder, String paymentId) throws RazorpayException;

  PaymentResponse createRazorpayPaymentLink(User user, Long amount) throws RazorpayException;

  PaymentResponse createStripePaymentLink(User user, Long amount, Long orderId);

}
