package f4.auth.global.security.jwt;


import f4.auth.domain.user.dto.request.CreateTokenDto;
import f4.auth.global.constant.CustomErrorCode;
import f4.auth.global.redis.RedisService;
import f4.auth.global.security.jwt.exception.ExpiredTokenException;
import f4.auth.global.security.jwt.exception.InvalidTokenException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final RedisService redisService;

    @Value("${jwt.secret}")
    private String SECRET_KEY;

    @Value("${jwt.token.access-expiration-time}")
    private Long atkDuration;

    @Value("${jwt.token.refresh-expiration-time}")
    private Long rtkDuration;

    @Value("${jwt.header}")
    private String header;

    @Value("${jwt.prefix}")
    private String prefix;

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
        claims.put("username", createTokenDto.getUsername());
        claims.put("email", createTokenDto.getEmail());

        Date now = new Date();

        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + time))
                .signWith(getSigningKey(SECRET_KEY), SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims extractAllClaims(String token) {
//        if (redisService.hasBlackList(token)) {
//            throw AlreadyLogoutException.EXCEPTION;
//        }
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
        }
    }

    public String resolveToken(HttpServletRequest request) {
        String bearer = request.getHeader(header);
        return parseToken(bearer);
    }

    public String parseToken(String bearerToken) {
        if (bearerToken != null && bearerToken.startsWith(prefix)) {
            return bearerToken.replace(prefix, "");
        }
        return null;
    }

    public Date getExpiredTime(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey(SECRET_KEY))
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
    }
}