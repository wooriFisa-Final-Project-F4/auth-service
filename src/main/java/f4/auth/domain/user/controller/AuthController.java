package f4.auth.domain.user.controller;

import f4.auth.domain.user.dto.request.LoginRequestDto;
import f4.auth.domain.user.dto.response.TokenResponseDto;
import f4.auth.domain.user.persist.entity.Member;
import f4.auth.domain.user.persist.repository.MemberRepository;
import f4.auth.domain.user.service.AuthService;
import f4.auth.domain.user.service.MemberService;
import f4.auth.global.constant.CustomErrorCode;
import f4.auth.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth/v1")
@RequiredArgsConstructor
public class AuthController {

  private final AuthService authService;
  private final MemberRepository memberRepository;

  @Value("${jwt.header}")
  private String authorization;

  @PostMapping("/login")
  public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDto dto) {

    TokenResponseDto response = authService.login(dto);
    return ResponseEntity.ok(response);
  }

  @PutMapping("/login")
  public TokenResponseDto reIssue(@RequestHeader(value = "RTK") String rtk) {
    return authService.reIssue(rtk);
  }

  @DeleteMapping("/logout")
  public void logout(@RequestHeader("Authorization") String accessToken) {
    authService.logout(accessToken);
  }
}
