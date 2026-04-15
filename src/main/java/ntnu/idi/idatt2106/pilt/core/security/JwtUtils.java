package ntnu.idi.idatt2106.pilt.core.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.SecurityException;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
@Slf4j
public class JwtUtils {

  @Value("${jwt.secret:MDEyMzQ1Njc4OTAxMjM0NTY3ODkwMTIzNDU2Nzg5MDE=}")
  private String jwtSecret;

  @Value("${jwt.expirationMs:86400000}")
  private int jwtExpirationMs;

  @Value("${jwt.issuer:pilt-api}")
  private String jwtIssuer;

  @Value("${jwt.audience:pilt-client}")
  private String jwtAudience;

  public String generateJwtToken(Authentication authentication) {
    if (authentication == null || !(authentication.getPrincipal() instanceof UserPrincipal userPrincipal)) {
      throw new IllegalArgumentException("Authentication principal must be a UserPrincipal");
    }

    return Jwts.builder()
        .setSubject(userPrincipal.getUserId().toString())
        .setIssuer(jwtIssuer)
        .setAudience(jwtAudience)
        .claim("userId", userPrincipal.getUserId())
        .claim("role", userPrincipal.getAuthorities().iterator().next().getAuthority())
        .setIssuedAt(new Date())
        .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
        .signWith(key(), SignatureAlgorithm.HS256)
        .compact();
  }

  public Long getUserIdFromJwtToken(String token) {
    Claims claims = parseClaims(token).getBody();
    Long userId = claims.get("userId", Long.class);
    if (userId != null) {
      return userId;
    }

    String subject = claims.getSubject();
    if (subject == null || subject.isBlank()) {
      throw new IllegalArgumentException("JWT token does not contain a user id");
    }

    try {
      return Long.parseLong(subject);
    } catch (NumberFormatException ex) {
      throw new IllegalArgumentException("JWT subject is not a valid user id", ex);
    }
  }

  public boolean validateJwtToken(String authToken) {
    if (authToken == null || authToken.isBlank()) {
      return false;
    }

    try {
      Claims claims = parseClaims(authToken).getBody();
      validateContextClaims(claims);
      return true;
    } catch (SecurityException | MalformedJwtException e) {
      log.debug("Invalid JWT signature or format");
    } catch (ExpiredJwtException e) {
      log.debug("JWT token is expired");
    } catch (UnsupportedJwtException e) {
      log.debug("JWT token is unsupported");
    } catch (IllegalArgumentException e) {
      log.debug("JWT claims string is empty");
    } catch (JwtException e) {
      log.debug("JWT validation failed");
    }

    return false;
  }

  private void validateContextClaims(Claims claims) {
    if (!jwtIssuer.equals(claims.getIssuer())) {
      throw new IllegalArgumentException("JWT issuer is invalid");
    }

    if (!jwtAudience.equals(claims.getAudience())) {
      throw new IllegalArgumentException("JWT audience is invalid");
    }
  }

  private Jws<Claims> parseClaims(String token) {
    return Jwts.parserBuilder()
        .setSigningKey(key())
        .build()
        .parseClaimsJws(token);
  }

  private Key key() {
    return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
  }
}
