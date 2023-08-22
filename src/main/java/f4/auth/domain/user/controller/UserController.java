package f4.auth.domain.user.controller;

import f4.auth.domain.user.dto.request.SignupRequestDto;
import f4.auth.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/auth/v1")
public class UserController {

  private final UserService userService;

  @GetMapping("/health-check")
  public String status() {
    return "It's Working in Auth-Service";
  }

  @PostMapping("/signup")
  public ResponseEntity<String> register(@Valid @RequestBody SignupRequestDto signupRequestDto) {
    userService.register(signupRequestDto);
    return ResponseEntity.ok("회원가입에 성공하였습니다.");
  }
}
