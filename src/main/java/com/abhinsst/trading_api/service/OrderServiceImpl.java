package com.abhinsst.trading_api.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import org.springframework.stereotype.Service;

import com.abhinsst.trading_api.domain.OrderStatus;
import com.abhinsst.trading_api.domain.OrderType;
import com.abhinsst.trading_api.model.Asset;
import com.abhinsst.trading_api.model.Coin;
import com.abhinsst.trading_api.model.Order;
import com.abhinsst.trading_api.model.OrderItem;
import com.abhinsst.trading_api.model.User;
import com.abhinsst.trading_api.repository.OrderItemRepository;
import com.abhinsst.trading_api.repository.OrderRepository;

@Service
public class OrderServiceImpl implements OrderService {

  @Autowired
  private OrderRepository orderRepository;

  @Autowired
  private WalletService walletService;

  @Autowired
  private OrderItemRepository orderItemRepository;

  @Autowired
  private AssetService assetService;

  @Override
  public Order createOrder(User user, OrderItem orderItem, OrderType orderType) {
    double price = orderItem.getCoin().getCurrentPrice() * orderItem.getQuantity();

    Order order = new Order();
    order.setUser(user);
    order.setOrderItem(orderItem);
    order.setOrderType(orderType);
    order.setPrice(BigDecimal.valueOf(price));
    order.setTimestamp(LocalDateTime.now());
    order.setStatus(OrderStatus.PANDING);
    return orderRepository.save(order);

  }

  @Override
  public Order getOrderById(Long orderId) throws Exception {
    return orderRepository.findById(orderId).orElseThrow(() -> new Exception("order not found"));
  }

  @Override
  public List<Order> getAllOrderOfUser(Long userId, OrderType orderType, String assetSymbol) {
    return orderRepository.findByUserId(userId);

  }

  private OrderItem createOrderItem(Coin coin, double quantity, double buyprice, double sellPrice) {
    OrderItem orderItem = new OrderItem();
    orderItem.setCoin(coin);
    orderItem.setQuantity(quantity);
    orderItem.setBuyPrice(buyprice);
    orderItem.setSellPrice(sellPrice);
    return orderItemRepository.save(orderItem);
  }

  @Transactional
  public Order buyAsset(Coin coin, double quantity, User user) throws Exception {
    if (quantity <= 0) {
      throw new Exception("quantity shoud be > 0");
    }
    double buyprice = coin.getCurrentPrice();
    OrderItem orderItem = createOrderItem(coin, quantity, buyprice, 0);

    Order order = createOrder(user, orderItem, OrderType.BUY);
    orderItem.setOrder(order);
    walletService.payOrderPayment(order, user);

    order.setStatus(OrderStatus.SUCCESS);
    order.setOrderType(OrderType.BUY);
    Order saveOrder = orderRepository.save(order);

    // Create assets
    Asset oldAsset = assetService.findAssetByUserIdAndCoinId(order.getUser().getId(),
        order.getOrderItem().getCoin().getId());

    if (oldAsset == null) {
      assetService.createAsset(user, orderItem.getCoin(), orderItem.getQuantity());
    } else {
      assetService.updateAsset(oldAsset.getId(), quantity);
    }

    return saveOrder;
  }

  @Transactional
  public Order sellAsset(Coin coin, double quantity, User user) throws Exception {
    if (quantity <= 0) {
      throw new Exception("quantity shoud be > 0");
    }
    double sellprice = coin.getCurrentPrice();

    Asset assetToSell = assetService.findAssetByUserIdAndCoinId(user.getId(), coin.getId());
    if (assetToSell != null) {

      double buyprice = assetToSell.getBuyPrice();
      OrderItem orderItem = createOrderItem(coin, quantity, buyprice, sellprice);

      Order order = createOrder(user, orderItem, OrderType.SELL);
      orderItem.setOrder(order);

      if (assetToSell.getQuantity() >= quantity) {
        order.setStatus(OrderStatus.SUCCESS);
        order.setOrderType(OrderType.SELL);
        Order saveOrder = orderRepository.save(order);

        walletService.payOrderPayment(order, user);

        Asset updatedAsset = assetService.updateAsset(assetToSell.getId(), -quantity);

        if (updatedAsset.getQuantity() * coin.getCurrentPrice() <= 1) {
          assetService.deleteAsset(updatedAsset.getId());
        }
        return saveOrder;
      }

      throw new Exception("Insufficient Quantity to sell");
    }
    throw new Exception("asset not found");
    // Create assets

  }

  @Override
  @Transactional
  public Order processOrder(Coin coin, double quantity, OrderType orderType, User user) throws Exception {
    if (orderType.equals(OrderType.BUY)) {
      return buyAsset(coin, quantity, user);
    } else if (orderType.equals(OrderType.SELL)) {
      return sellAsset(coin, quantity, user);
    }
    throw new Exception("invalid order type");

  }

}
