package com.abhinsst.trading_api.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.abhinsst.trading_api.domain.VerificationType;
import com.abhinsst.trading_api.model.ForgotPasswordToken;
import com.abhinsst.trading_api.model.User;
import com.abhinsst.trading_api.model.VerificationCode;
import com.abhinsst.trading_api.request.ForgotPasswordTokenRequest;
import com.abhinsst.trading_api.request.ResetPasswordRequest;
import com.abhinsst.trading_api.response.ApiResponse;
import com.abhinsst.trading_api.response.AuthResponse;
import com.abhinsst.trading_api.service.EmailService;
import com.abhinsst.trading_api.service.ForgotPasswordService;
import com.abhinsst.trading_api.service.UserService;
import com.abhinsst.trading_api.service.VerificationCodeService;
import com.abhinsst.trading_api.util.OtpUtils;

@RestController
public class UserController {
  @Autowired
  private UserService userService;

  @Autowired
  private EmailService emailService;

  @Autowired
  private VerificationCodeService verificationCodeService;

  @Autowired
  private ForgotPasswordService forgotPasswordService;

  @GetMapping("/api/users/profile")
  public ResponseEntity<User> getUserProfile(@RequestHeader("Authorization") String jwt) throws Exception {
    User user = userService.findUserProfileByJwt(jwt);
    return new ResponseEntity<User>(user, HttpStatus.OK);
  }

  @PostMapping("/api/users/verification/{verificationType}/send-otp")
  public ResponseEntity<String> sendVerificationOtp(@RequestHeader("Authorization") String jwt,
      @PathVariable VerificationType verificationType) throws Exception {
    User user = userService.findUserProfileByJwt(jwt);

    VerificationCode verificationCode = verificationCodeService.getVerificationCodeByUser(user.getId());

    if (verificationCode == null) {
      verificationCode = verificationCodeService.sendVerificationCode(user, verificationType);
    }

    if (verificationType.equals(VerificationType.EMAIL)) {
      emailService.sendVerificationOptEmail(user.getEmail(), verificationCode.getOtp());
    }

    return new ResponseEntity<>("verfication otp send successfully", HttpStatus.OK);
  }

  @PatchMapping("/api/users/enable-two-factor/verify-opt/{opt}")
  public ResponseEntity<User> enableTwoFactorAuthenticaiton(@PathVariable String otp,
      @RequestHeader("Authorization") String jwt)
      throws Exception {
    User user = userService.findUserProfileByJwt(jwt);

    VerificationCode verificationCode = verificationCodeService.getVerificationCodeByUser(user.getId());

    String sendTo = verificationCode.getVerificationType().equals(VerificationType.EMAIL) ? verificationCode.getEmail()
        : verificationCode.getMobile();

    boolean isVerfied = verificationCode.getOtp().equals(otp);
    if (isVerfied) {
      User updatedUser = userService.enableTwoFactorAuthentication(verificationCode.getVerificationType(), sendTo,
          user);
      verificationCodeService.deleteVerificationCodeById(verificationCode);
      return new ResponseEntity<>(updatedUser, HttpStatus.OK);

    }

    throw new Exception("wrong otp! try-again");

  }

  @PostMapping("/auth/users/reset-password/send-otp")
  public ResponseEntity<AuthResponse> sendForgotPasswordOtp(
      @RequestBody ForgotPasswordTokenRequest req) throws Exception {

    User user = userService.findUserByEmail(req.getSandTo());
    String otp = OtpUtils.generateOTP();
    UUID uuid = UUID.randomUUID();
    String id = uuid.toString();

    ForgotPasswordToken token = forgotPasswordService.findByUser(user.getId());
    if (token == null) {
      token = forgotPasswordService.createToken(user, id, otp, req.getVerificationType(), req.getSandTo());

    }

    if (req.getVerificationType().equals(VerificationType.EMAIL)) {
      emailService.sendVerificationOptEmail(user.getEmail(), token.getOtp());
    }

    AuthResponse response = new AuthResponse();
    response.setSession(token.getId());
    response.setMessage("password reset otp send successfully");

    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @PatchMapping("/auth/users/reset-password/verify-otp")
  public ResponseEntity<ApiResponse> resetPassword(
      @RequestParam String id,
      @RequestBody ResetPasswordRequest req,
      @RequestHeader("Authorization") String jwt)
      throws Exception {

    ForgotPasswordToken forgotPasswordToken = forgotPasswordService.findById(id);

    boolean isVerified = forgotPasswordToken.getOtp().equals(req.getOtp());
    if (isVerified) {
      userService.updatePassword(forgotPasswordToken.getUser(), req.getPassword());
      ApiResponse res = new ApiResponse();
      res.setMessage("pasword update Successfully");
      return new ResponseEntity<>(res, HttpStatus.ACCEPTED);
    }
    throw new Exception("wrong otp");
  }

}
