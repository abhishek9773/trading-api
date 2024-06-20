package com.abhinsst.trading_api.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abhinsst.trading_api.config.JwtProvider;
import com.abhinsst.trading_api.domain.VerificationType;
import com.abhinsst.trading_api.model.TwoFactorAuth;
import com.abhinsst.trading_api.model.User;
import com.abhinsst.trading_api.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
  @Autowired
  UserRepository userRepository;

  @Override
  public User findUserProfileByJwt(String jwt) throws Exception {
    String email = JwtProvider.getEmailFromToken(jwt);
    User user = userRepository.findByEmail(email);
    if (user == null) {
      throw new Exception("user not found");
    }
    return user;
  }

  @Override
  public User findUserByEmail(String email) throws Exception {
    User user = userRepository.findByEmail(email);
    if (user == null) {
      throw new Exception("user not found!");
    }
    return null;
  }

  @Override
  public User findUserById(Long userId) throws Exception {
    Optional<User> user = userRepository.findById(userId);
    if (!user.isPresent()) {
      throw new Exception("user not found!");
    }
    return user.get();
  }

  @Override
  public User updatePassword(User user, String newPassword) {
    user.setPassword(newPassword);
    return userRepository.save(user);
  }

  @Override
  public User enableTwoFactorAuthentication(VerificationType verificationType, String sendTo, User user) {
    TwoFactorAuth twoFactorAuth = new TwoFactorAuth();
    twoFactorAuth.setEnable(true);
    twoFactorAuth.setSendTo(verificationType);
    user.setTowFatoreAuth(twoFactorAuth);

    return userRepository.save(user);
  }

}
