package f4.auth.domain.auth.controller;

import f4.auth.domain.auth.dtto.request.LoginRequestDto;
import f4.auth.domain.auth.dtto.response.TokenResponseDto;
import f4.auth.domain.auth.service.AuthService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth/v1")
@CrossOrigin({"http://localhost:4000", "http://localhost:8892"})
public class AuthController {

  private final AuthService authService;

  @PostMapping("/login")
  public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDto loginRequestDto) {
    TokenResponseDto responseDto = authService.login(loginRequestDto);

    HttpHeaders headers = new HttpHeaders();
    headers.add("Authorization", responseDto.getGrantType() + responseDto.getAccessToken());
    headers.add("refresh-token", responseDto.getRefreshToken());

    return ResponseEntity.status(HttpStatus.OK)
        .headers(headers).body("로그인에 성공하셨습니다.");
  }
}
