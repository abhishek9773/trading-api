package com.abhinsst.trading_api.request;

import com.abhinsst.trading_api.domain.OrderType;

import lombok.Data;

@Data
public class CreateOrderRequest {

  private String coinId;

  private double quantity;
  private OrderType orderType;

}
