package f4.auth.global.utils;

import javax.servlet.http.Cookie;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

@Component
public class CookieProvider {

  @Value("${jwt.token.refresh-token-duration}")
  private String refreshTokenExpiredTime;

  public ResponseCookie createRefreshTokenCookie(String refreshToken) {
    return ResponseCookie.from("refresh-token", refreshToken)
        .httpOnly(true)
        .secure(false)
        .path("/")
        .maxAge(Long.parseLong(refreshTokenExpiredTime)).build();
  }

  public ResponseCookie removeRefreshTokenCookie() {
    return ResponseCookie.from("refresh-token", null)
        .maxAge(0)
        .path("/")
        .build();
  }

  public Cookie of(ResponseCookie responseCookie) {
    Cookie cookie = new Cookie(responseCookie.getName(), responseCookie.getValue());
    cookie.setPath(responseCookie.getPath());
    cookie.setSecure(responseCookie.isSecure());
    cookie.setHttpOnly(responseCookie.isHttpOnly());
    cookie.setMaxAge((int) responseCookie.getMaxAge().getSeconds());
    return cookie;
  }
}
