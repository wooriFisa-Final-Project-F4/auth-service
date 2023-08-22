package f4.auth.domain.user.controller;

import f4.auth.domain.user.dto.request.LoginRequestDto;
import f4.auth.domain.user.dto.response.TokenResponseDto;
import f4.auth.domain.user.service.AuthService;
import f4.auth.global.security.jwt.JwtTokenProvider;
import f4.auth.global.security.jwt.JwtValidateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth/v1")
@RequiredArgsConstructor
public class AuthController {

  private final AuthService authService;
  private final JwtValidateService jwtValidateService;

  @PostMapping("/login")
  public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDto dto) {
    return ResponseEntity.ok(authService.login(dto));
  }

  @PutMapping("/login")
  public TokenResponseDto reIssue(@RequestHeader(value = "Refresh-Token") String rtk) {
    return authService.reIssue(rtk);
  }
}
