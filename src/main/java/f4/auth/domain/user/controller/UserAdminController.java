package f4.auth.domain.user.controller;

import static f4.auth.domain.user.constant.Role.ADMIN;
import static f4.auth.domain.user.constant.Role.USER;

import f4.auth.domain.user.constant.Role;
import f4.auth.domain.user.service.UserService;
import f4.auth.global.constant.CustomErrorCode;
import f4.auth.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/v1")
public class UserAdminController {

  private final UserService userService;

  /*
   * @date : 2023.08.24
   * @author : yuki
   * @param : int page, value = [column]
   * @description : 유저 전체 조회 - 페이징
   */
  @GetMapping("/findAll")
  public ResponseEntity<?> getUsers(
      @RequestParam(required = false, defaultValue = "1", value = "page") int pageNo,
      @RequestParam(required = false, defaultValue = "username", value = "criteria") String criteria,
      @RequestHeader("role") String role
  ) {
    log.info("회원 정보 전체 조회 수행 pageNo : {}, criteria : {}, role : {}", pageNo, criteria, role);

    if (Role.of(role) != ADMIN) {
      throw new CustomException(CustomErrorCode.NOT_VALID_ROLE);
    }

    return ResponseEntity.ok(userService.getUsers(pageNo, criteria));
  }
}
