package f4.auth.global.security.jwt;

import f4.auth.domain.auth.dto.CreateTokenDto;
import f4.auth.global.constant.CustomErrorCode;
import f4.auth.global.exception.CustomException;
import f4.auth.global.security.jwt.exception.ExpiredTokenException;
import f4.auth.global.security.jwt.exception.InvalidTokenException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

  @Value("${jwt.secret}")
  private String SECRET_KEY;

  @Value("${jwt.token.access-token-duration}")
  private Long atkDuration;

  @Value("${jwt.token.refresh-token-duration}")
  private Long rtkDuration;

  private Key getSigningKey(String secretKey) {
    byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
    return Keys.hmacShaKeyFor(keyBytes);
  }

  public String createAccessToken(CreateTokenDto createTokenDto) {
    Claims claims = Jwts.claims();
    claims.put("user_id", createTokenDto.getId());
    claims.put("role", createTokenDto.getRole());

    Date now = new Date();

    return Jwts.builder()
        .setHeaderParam("typ", "JWT")
        .setClaims(claims)
        .setSubject(createTokenDto.getEmail())
        .setIssuedAt(now)
        .setExpiration(new Date(now.getTime() + atkDuration))
        .signWith(getSigningKey(SECRET_KEY), SignatureAlgorithm.HS512)
        .compact();
  }

  public String createRefreshToken(CreateTokenDto createTokenDto) {
    Claims claims = Jwts.claims();
    claims.put("value", UUID.randomUUID());

    Date now = new Date();

    return Jwts.builder()
        .setHeaderParam("typ", "rtk")
        .setClaims(claims)
        .setSubject(createTokenDto.getEmail())
        .setIssuedAt(now)
        .setExpiration(new Date(now.getTime() + rtkDuration))
        .signWith(getSigningKey(SECRET_KEY), SignatureAlgorithm.HS512)
        .compact();
  }

  public Claims extractAllClaims(String token) {
    try {
      return Jwts.parserBuilder()
          .setSigningKey(getSigningKey(SECRET_KEY))
          .build()
          .parseClaimsJws(token)
          .getBody();
    } catch (ExpiredJwtException e) {
      throw new ExpiredTokenException(CustomErrorCode.EXPIRED_ACCESS_TOKEN);
    } catch (InvalidTokenException e) {
      throw new InvalidTokenException(CustomErrorCode.INVALID_ACCESS_TOKEN);
    } catch(Exception e){
      throw new CustomException(CustomErrorCode.INVALID_REFRESH_TOKEN);
    }
  }
}