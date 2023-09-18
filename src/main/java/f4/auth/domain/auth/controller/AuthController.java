package f4.auth.domain.auth.controller;

import f4.auth.domain.auth.dto.request.CertificationRequestDto;
import f4.auth.domain.auth.dto.request.LoginRequestDto;
import f4.auth.domain.auth.dto.response.TokenResponseDto;
import f4.auth.domain.auth.service.AuthService;
import f4.auth.global.utils.CookieProvider;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth/v1")
public class AuthController {

  private final AuthService authService;
  private final CookieProvider cookieProvider;

  /*
   * @date : 2023.08.29
   * @author : yuki
   * @param : LoginRequestDto( email, password)
   * @description : 로그인 정보 확인 후 토큰 발급
   */
  @PostMapping("/login")
  public ResponseEntity<?> login(
      @Valid @RequestBody LoginRequestDto loginRequestDto) {
    log.info("로그인 수행. 회원 이메일 : {}", loginRequestDto.getEmail());
    TokenResponseDto responseDto = authService.login(loginRequestDto);
    ResponseCookie responseCookie = cookieProvider.createRefreshTokenCookie(responseDto.getRefreshToken());

    return ResponseEntity.status(HttpStatus.OK)
        .header(HttpHeaders.AUTHORIZATION, responseDto.getGrantType() + responseDto.getAccessToken())
        .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
        .body("로그인에 성공하셨습니다.");
  }

  /*
   * @date : 2023.09.02
   * @author : yuki
   * @param : @RequestHeader Authorization @CookieValue refresh-token
   * @description : access-token이 만료된 경우 refresh-token 검증 후 새로운 access-token 발급
   */
  @PostMapping("/token/reissue")
  public ResponseEntity<?> reissue(
      @RequestHeader("Authorization") String accessToken,
      @CookieValue("refresh-token") String refreshToken) {
    log.info("토큰 재발급 수행. refresh-token 존재 여부 : {}", !refreshToken.isBlank());
    TokenResponseDto responseDto = authService.reissue(refreshToken);

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.add("Authorization", responseDto.getGrantType() + responseDto.getAccessToken());

    return ResponseEntity.status(HttpStatus.OK)
        .headers(headers)
        .body("토큰이 재발행 되었습니다.");
  }

  /*
   * @date : 2023,09.02
   * @author : yuki
   * @param : access-token
   * @description : access-token 추출하여 만료시간 만큼 레디스의 블랙리스트에 저장해둠
   */
  @PatchMapping("/logout")
  public ResponseEntity<?> logout(@RequestHeader("Authorization") String accessToken) {
    log.info("로그아웃 수행.");
    authService.logout(accessToken);
    log.info("로그아웃 성공하셨습니다.");
    return ResponseEntity.status(HttpStatus.OK)
        .body("로그아웃에 성공하셨습니다.");
  }

  /*
  * @date : 2023.09.04
  * @author  : yuki
  * @param : CertificationRequestDto ( email, certificationNUmber )
  * @description : 레디스의 해당 이메일 인증 번호 확인
  */
  @PostMapping("/email/certification")
  public ResponseEntity<?> emailCertification(@RequestBody CertificationRequestDto certificationRequestDto) {
    log.info("회원가입 이메일 인증 수행. email : {}", certificationRequestDto.getEmail());
    authService.emailCertification(certificationRequestDto);
    log.info("회원가입 이메일 인증 완료");
    return ResponseEntity.status(HttpStatus.OK)
        .body("이메일이 인증되었습니다.");
  }
}
