package com.abhinsst.trading_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.abhinsst.trading_api.Util.OtpUtils;
import com.abhinsst.trading_api.config.JwtProvider;
import com.abhinsst.trading_api.model.TwoFactorOTP;
import com.abhinsst.trading_api.model.User;
import com.abhinsst.trading_api.repository.UserRepository;
import com.abhinsst.trading_api.response.AuthResponse;
import com.abhinsst.trading_api.service.CustomUserDetailsService;
import com.abhinsst.trading_api.service.EmailService;
import com.abhinsst.trading_api.service.TwoFactorOptService;

@RestController
@RequestMapping("/auth")
public class AuthController {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private CustomUserDetailsService customUserDetailsService;

  @Autowired
  private TwoFactorOptService twoFactorOptService;

  @Autowired
  private EmailService emailService;

  // private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

  @PostMapping("/signup")
  public ResponseEntity<AuthResponse> register(@RequestBody User user) throws Exception {

    User isEmailExist = userRepository.findByEmail(user.getEmail());
    if (isEmailExist != null) {
      throw new Exception("Email is already used with another account");
    }

    User newUser = new User();
    newUser.setEmail(user.getEmail());
    newUser.setPassword(user.getPassword()); // Hash the password
    newUser.setFullName(user.getFullName());

    userRepository.save(newUser);

    Authentication auth = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
    SecurityContextHolder.getContext().setAuthentication(auth);

    String jwt = JwtProvider.generateToken(auth);

    AuthResponse res = new AuthResponse();
    res.setJwt(jwt);
    res.setStatus(true);
    res.setMessage("register success");

    return new ResponseEntity<>(res, HttpStatus.CREATED);
  }

  @PostMapping("/signin")
  public ResponseEntity<AuthResponse> login(@RequestBody User user) throws Exception {

    String userName = user.getEmail();
    String password = user.getPassword();

    Authentication auth = authenticate(userName, password);
    SecurityContextHolder.getContext().setAuthentication(auth);

    String jwt = JwtProvider.generateToken(auth);

    User authuser = userRepository.findByEmail(userName);
    if (user.getTowFatoreAuth().isEnable()) {
      AuthResponse res = new AuthResponse();
      res.setMessage("Two factor authentication is enabled");
      res.setTwoFactorAuthEnabled(true);
      String otp = OtpUtils.generateOTP();
      TwoFactorOTP oldTwoFactorOTP = twoFactorOptService.findByUser(authuser.getId());
      if (oldTwoFactorOTP != null) {
        twoFactorOptService.deleteTwoFactorOtp(oldTwoFactorOTP);
      }

      TwoFactorOTP newTwoFactorOTP = twoFactorOptService.createTwoFactorOptOtp(authuser, otp, jwt);

      emailService.sendVerificationOptEmail(userName, otp);

      res.setSession(newTwoFactorOTP.getId());
      return new ResponseEntity<>(res, HttpStatus.ACCEPTED);
    }
    AuthResponse res = new AuthResponse();
    res.setJwt(jwt);
    res.setStatus(true);
    res.setMessage("login success");

    return new ResponseEntity<>(res, HttpStatus.CREATED);
  }

  private Authentication authenticate(String userName, String password) {

    UserDetails userDetails = customUserDetailsService.loadUserByUsername(userName);

    if (userDetails == null) {
      throw new BadCredentialsException("Invalid username");

    }

    if (!password.equals(userDetails.getPassword())) {
      throw new BadCredentialsException("Invalid password");
    }

    return new UsernamePasswordAuthenticationToken(password, userDetails.getAuthorities());

  }

  public ResponseEntity<AuthResponse> verifySigninOpt(@PathVariable String otp, @RequestParam String id)
      throws Exception {
    TwoFactorOTP towFactorOTP = twoFactorOptService.findById(id);
    if (twoFactorOptService.verifyTwoFactorOpt(towFactorOTP, otp)) {
      AuthResponse res = new AuthResponse();
      res.setMessage("Two factor authentication verified");
      res.setTwoFactorAuthEnabled(true);
      res.setJwt(towFactorOTP.getJwt());
      return new ResponseEntity<>(res, HttpStatus.OK);

    }
    throw new Exception("invvalid otp");

  }

}
