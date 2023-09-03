package f4.auth.domain.auth.controller;

import f4.auth.domain.auth.dto.request.LoginRequestDto;
import f4.auth.domain.auth.dto.response.TokenResponseDto;
import f4.auth.domain.auth.service.AuthService;
import f4.auth.global.utils.CookieProvider;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth/v1")
public class AuthController {

  private final AuthService authService;
  private final CookieProvider cookieProvider;

  @PostMapping("/login")
  public ResponseEntity<?> login(
      @Valid @RequestBody LoginRequestDto loginRequestDto) {

    TokenResponseDto responseDto = authService.login(loginRequestDto);

    ResponseCookie responseCookie = cookieProvider.createRefreshTokenCookie(
        responseDto.getRefreshToken());

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.add("Authorization", responseDto.getGrantType() + responseDto.getAccessToken());

    return ResponseEntity.status(HttpStatus.OK)
        .headers(headers)
        .header(HttpHeaders.COOKIE, responseCookie.toString())
        .body("로그인에 성공하셨습니다.");
  }

  @PostMapping("/token/reissue")
  public ResponseEntity<?> reissue(
      @RequestHeader("Authorization") String accessToken,
      @CookieValue("refresh-token") String refreshToken) {
    TokenResponseDto responseDto = authService.reissue(refreshToken);

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.add("Authorization", responseDto.getGrantType() + responseDto.getAccessToken());

    return ResponseEntity.status(HttpStatus.OK)
        .headers(headers)
        .body("토큰이 재발행 되었습니다.");
  }

  @PatchMapping("/logout")
  public ResponseEntity<?> logout(
      @RequestHeader("Authorization") String accessToken) {

    authService.logout(accessToken);
    return ResponseEntity.status(HttpStatus.OK).body("로그아웃에 성공하셨습니다.");
  }
}
