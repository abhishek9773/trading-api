// package com.abhinsst.trading_api.config;

// import java.util.Date;
// import java.util.function.Function;

// import javax.crypto.SecretKey;

// import org.springframework.cglib.core.ClassNameReader;

// import io.jsonwebtoken.Claims;
// import io.jsonwebtoken.Jwts;
// import io.jsonwebtoken.security.Keys;

// public class JwtHelper {

// private String getUserNameFromToken(String token) {
// return getClaimFromToken(token, Claims::getSubject);
// }

// private Date GetExpirationDateFromToken(String token) {
// return getClaimFromToken(token, Claims::getExpiration);

// }

// private <T> T getClaimFromToken(String token, Function<Claims, T>
// claimsResolver) {

// final Claims claims = getAllClaimsFromTokens(token);
// return claimsResolver.apply(claims);

// }

// private Claims getAllClaimsFromToken(String token) {
// }

// }
