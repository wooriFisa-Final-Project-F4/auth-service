package f4.auth.global.security.jwt;

import f4.auth.domain.auth.dtto.CreateTokenDto;
import f4.auth.global.redis.RedisService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

  private final RedisService redisService;

  @Value("${jwt.secret}")
  private String SECRET_KEY;

  @Value("${jwt.token.access-token-duration}")
  private Long atkDuration;

  @Value("${jwt.token.refresh-token-duration}")
  private Long rtkDuration;

  @Value("${jwt.token.header}")
  private String header;

  private Key getSigningKey(String secretKey) {
    byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
    return Keys.hmacShaKeyFor(keyBytes);
  }

  public String createAccessToken(CreateTokenDto createTokenDto) {
    return createToken(createTokenDto, atkDuration);
  }

  public String createRefreshToken(CreateTokenDto createTokenDto) {
    return createToken(createTokenDto, rtkDuration);
  }

  private String createToken(CreateTokenDto createTokenDto, long time) {
    Claims claims = Jwts.claims();
    claims.put("userId", createTokenDto.getId());
    claims.put("Role", createTokenDto.getRole());

    Date now = new Date();

    return Jwts.builder()
        .setHeaderParam("typ", "JWT")
        .setClaims(claims)
        .setIssuedAt(now)
        .setExpiration(new Date(now.getTime() + time))
        .signWith(getSigningKey(SECRET_KEY), SignatureAlgorithm.HS512)
        .compact();
  }
}