package com.abhinsst.trading_api.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.abhinsst.trading_api.domain.OrderType;
import com.abhinsst.trading_api.model.Coin;
import com.abhinsst.trading_api.model.Order;
import com.abhinsst.trading_api.model.OrderItem;
import com.abhinsst.trading_api.model.User;

@Service
public interface OrderService {

  Order createOrder(User user, OrderItem orderItem);

  Order getOrderById(Long orderId);

  List<Order> getAllOrderOfUser(Long userId, OrderType orderType, String assetSymbol);

  Order processOrder(Coin coin, double quantity, OrderType orderType, User user);

}
