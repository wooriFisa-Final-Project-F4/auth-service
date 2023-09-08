package f4.auth.domain.user.controller;

import f4.auth.domain.user.dto.request.LinkRequestDto;
import f4.auth.domain.user.dto.request.SignupRequestDto;
import f4.auth.domain.user.service.feign.dto.response.UserCheckResponseDto;
import f4.auth.domain.user.dto.response.ProductResponseDto;
import f4.auth.domain.user.service.UserService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/v1")
public class UserController {

  private final UserService userService;

  /*
   * @date : 2023.08.22
   * @author : yuki
   * @param : signupRequestDto(username, gender, birth, address, email, password, phoneNumber)
   * @description : 회원 등록
   */
  @PostMapping("/signup")
  public ResponseEntity<?> save(@Valid @RequestBody SignupRequestDto signupRequestDto) {
    log.info("회원가입 수행. 회원 이름 : {}, 이메일 : {}", signupRequestDto.getUsername(),
        signupRequestDto.getEmail());
    userService.save(signupRequestDto);
    return ResponseEntity.ok("회원가입에 성공하였습니다.");
  }

  /*
   * @date : 2023.08.26
   * @author : yuki
   * @param : userId
   * @description : 회원 상세 정보
   */
  @GetMapping("/detail/{userId}")
  public ResponseEntity<?> findByUserId(@PathVariable("userId") Long userId) {
    log.info("회원 상세 정보 조회. 회원 아이디 : {}", userId);
    return ResponseEntity.ok(userService.findByUserId(userId));
  }

  /*
   * @date : 2023.08.24
   * @author : yuki
   * @param : userId
   * @description : email-service 해당 id 유저의 이메일 정보 조회
   * */
  @GetMapping("/feign/{userId}")
  public UserCheckResponseDto findByUserIdForOtherService(@PathVariable("userId") Long userId) {
    log.info("email-service 유저 정보 조회. 회원 아이디 : {}", userId);
    return userService.findByUserIdForOtherService(userId);
  }

  /*
   * @date : 2023.09.03
   * @author : yuki
   * @param : userId
   * @description : product-service 해당 id 유저 존재 여부 조회
   */
  @GetMapping("/product/{userId}")
  public ProductResponseDto existsByUserId(@PathVariable("userId") Long userId) {
    log.info("product-service 유저 존재 여부 조회. 회원 아이디 : {}", userId);
    return userService.existsByUserId(userId);
  }

  /*
   * @date : 2023.09.05
   * @author : yuki
   * @param : @RequestHeader(userId), LinkRequestDto(name, accountNumber, password)
   * @description :  계좌를 연결하기 위해, Feign 통신하기 위한 LinkingRequestDto로 객체를 만들어 Mock-Api 서버에 요청한다.
   * */
  @PostMapping("/woori-mock/api/linking")
  public ResponseEntity<?> linkingAccount(
      @RequestHeader("userId") Long userId,
      @Valid @RequestBody LinkRequestDto linkRequestDto) {
    log.info(
        "Feign 통신을 통해 Aretemoderni Account 연동 수행. "
            + "userId : {}, name : {}", userId, linkRequestDto.getName());
    return ResponseEntity.ok(userService.linkingAccount(userId, linkRequestDto));
  }

  /*
   * @date : 2023.09.05
   * @author : yuki
   * @param : RequestHeader(userId)
   * @description : 계좌 잔액 조회를 하기 위해, Feign 통신하기 위한 CheckBalanceRequestDto 객체를 만들어 Mock-Api 서버에 요청한다.
   */
  @PostMapping("/woori-mock/api/check/balance")
  public ResponseEntity<?> checkBalance(
      @RequestHeader("userId") Long userId
  ) {
    log.info("Feign 통신을 통해 계좌 잔액 조회. ");
    return ResponseEntity.ok(userService.checkBalance(userId));
  }
}
