package com.abhinsst.trading_api.config;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.crypto.SecretKey;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

public class JwtProvider {
  private static SecretKey key = Keys.hmacShaKeyFor(JwtConstant.SECRETE_KEY.getBytes());

  public static String generateToken(Authentication auth) {
    Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();

    String roles = popularAuthorities(authorities);

    String jwt = Jwts.builder().issuedAt(new Date())
        .expiration(new Date(new Date().getTime() + 86400000))
        .claim("email", auth.getName())
        .claim("authorities", roles)
        .signWith(key)
        .compact();
    return jwt;
  }

  public static String getEmailFromToken(String token){
    token = token.substring(7);
    Claims claims = Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
    String email  = String.valueOf(claims.valueof(claims.get("email"));
    return email;
    
  }

  private static String popularAuthorities(Collection<? extends GrantedAuthority> authoritiees) {
    Set<String> auth = new HashSet<>();
    for (GrantedAuthority ga : authoritiees) {
      auth.add(ga.getAuthority());

    }
    return String.join(",", auth);
  }

}
