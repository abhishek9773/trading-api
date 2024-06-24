package com.abhinsst.trading_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abhinsst.trading_api.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

  public User findByEmail(String email);

  // select u
  // form User
  // u where u.email = :email

}
