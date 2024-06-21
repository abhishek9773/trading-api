package com.abhinsst.trading_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abhinsst.trading_api.model.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}
