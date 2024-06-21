package com.abhinsst.trading_api.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.abhinsst.trading_api.domain.OrderStatus;
import com.abhinsst.trading_api.domain.OrderType;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Data
@Entity
public class Order {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  @ManyToOne
  private User user;

  @Column(nullable = false)
  private OrderType orderType;

  @Column(nullable = false)
  private BigDecimal price;

  private LocalDateTime timestamp = LocalDateTime.now();

  private OrderStatus status;
  @OneToOne(mappedBy = "order", cascade = CascadeType.ALL)
  private OrderItem orderItem;
}