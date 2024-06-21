package com.abhinsst.trading_api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abhinsst.trading_api.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

  List<Order> findByUserId(Long userId);

}
