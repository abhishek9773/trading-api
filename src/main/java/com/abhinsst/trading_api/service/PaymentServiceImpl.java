package com.abhinsst.trading_api.service;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.abhinsst.trading_api.domain.PaymentMethod;
import com.abhinsst.trading_api.domain.PaymentOrderStatus;
import com.abhinsst.trading_api.model.PaymentOrder;
import com.abhinsst.trading_api.model.User;
import com.abhinsst.trading_api.repository.PaymentOrderRepository;
import com.abhinsst.trading_api.response.PaymentResponse;
import com.google.gson.JsonObject;
import com.razorpay.Payment;
import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.stripe.Stripe;
import com.stripe.model.Charge.Level3.LineItem;
import com.stripe.model.Review.Session;
import com.stripe.param.billingportal.SessionCreateParams;

public class PaymentServiceImpl implements PaymentService {

  @Autowired
  private PaymentOrderRepository paymentOrderRepository;
  @Value("&{sipe.api.key}")
  private Stripe stripeSecretKey;

  @Value("&{razorpay.api.key}")
  private String apiKey;

  @Value("${razorpay.api.secret}")
  private String apiSecretKey;
  private String apikey;

  @Override
  public PaymentOrder createOrder(User user, Long amount, PaymentMethod paymentMethod) {
    PaymentOrder paymentOrder = new PaymentOrder();
    paymentOrder.setUser(user);
    paymentOrder.setAmount(amount);
    paymentOrder.setPaymentMethod(paymentMethod);

    return paymentOrderRepository.save(paymentOrder);
  }

  @Override
  public PaymentOrder getPaymentOrderById(Long id) throws Exception {
    return paymentOrderRepository.findById(id).orElseThrow(() -> new Exception("payment order not found"));
  }

  @Override
  public Boolean ProccedPaymentOrder(PaymentOrder paymentOrder, String paymentId) throws RazorpayException {
    if (paymentOrder.getStatus().equals(PaymentOrderStatus.PANDING)) {
      if (paymentOrder.getPaymentMethod().equals(PaymentMethod.RAZORPAY)) {
        RazorpayClient razorpay = new RazorpayClient(apiKey, apiSecretKey);

        Payment payment = razorpay.payments.fetch(paymentId);

        Integer amount = payment.get("amount");
        String status = payment.get("status");
        if (status.equals("captured")) {
          paymentOrder.setStatus(PaymentOrderStatus.SUCCESS);
          return true;
        }
        paymentOrder.setStatus(PaymentOrderStatus.FAILED);
        paymentOrderRepository.save(paymentOrder);
        return false;

      }
      paymentOrder.setStatus(PaymentOrderStatus.SUCCESS);
      paymentOrderRepository.save(paymentOrder);
      return true;
    }
    return false;
  }

  @Override
  public PaymentResponse createRazorpayPaymentLink(User user, Long amount) throws RazorpayException {

    Long Amount = amount * 100;

    try {
      RazorpayClient razorpay = new RazorpayClient(apikey, apiSecretKey);
      JSONObject paymentLinkRequest = new JSONObject();
      paymentLinkRequest.put("amount", amount);
      paymentLinkRequest.put("currency", "INR");

      JSONObject customer = new JSONObject();
      customer.put("name", user.getFullName());

      customer.put("email", user.getEmail());
      paymentLinkRequest.put("cusomter", customer);

      JSONObject notify = new JSONObject();
      notify.put("email", true);
      paymentLinkRequest.put("notify", notify);

      paymentLinkRequest.put("reminder_enable", true);

      paymentLinkRequest.put("callback_url", "http://localhost:5173/wallet");
      paymentLinkRequest.put("callback_method", "get");

      PaymentLink payment = razorpay.paymentLink.create(paymentLinkRequest);

      String paymentLinkId = payment.get("id");
      String paymentLinkUrl = payment.get("short_url");

      PaymentResponse res = new PaymentResponse();
      res.setPaymentUrl(paymentLinkUrl);

      return res;
    } catch (RazorpayException e) {

      throw new RazorpayException(e.getMessage());

    }

  }

  @Override
  public PaymentResponse createStripePaymentLink(User user, Long amount, Long orderId) {
    Stripe apikey = stripeSecretKey;

    SessionCreateParams params = SessionCreateParams.builder()
    .addPaymentMethodType(SessionCreateParams.paymetntMethodType.CARD)
    .setMode(SessionCreateParams.Mode.PAYMENT)
    .setSuccessUrl("http://lcoalhost:5173/wallet?order_id =" +orderId)
    .setCancelUrl("http://localhost:5173/payment/cancel")
    .addLineItem(SessionCreateParams.LineItem.builder()
              .setQuantity(1L)
              .setPriceDate(SessionCreateParams.LineItem.PriceDate.builder()
              .currency("usd")
              .setUnitAmount(amount*100)
              SetProductDate(SessionCreateParams
                  .LineItem
                  .PriceDate
                  .productDate 
                  .builder()
                  .setName("Top up Wallet")
                  .build()
              ).build()
            ).build()
    ).build();

    Session session = Session.create(params);

    System.out.println("Session is = "+ session);

    PaymentResponse res = new PaymentResponse();

    res.setPayment_url(session.getUrl());

    return res;




  }

}
