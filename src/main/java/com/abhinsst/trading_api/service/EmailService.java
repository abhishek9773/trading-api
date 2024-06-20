package com.abhinsst.trading_api.service;

import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

public class EmailService {
  private JavaMailSender javaMailSender;

  public void sendVerificationOptEmail(String email, String otp) throws MessagingException {
    MimeMessage mimeMessage = javaMailSender.createMimeMessage();
    MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, "UTF-8");
    String subject = "Verity OTP";
    String text = "Your Verification code is " + otp;

    mimeMessageHelper.setSubject(subject);
    mimeMessageHelper.setText(text);
    mimeMessageHelper.setTo(email);

    try {
      javaMailSender.send(mimeMessage);
    } catch (MailException e) {
      throw new MailSendException(e.getMessage());
    }
  }

}
