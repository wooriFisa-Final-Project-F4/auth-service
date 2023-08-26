package f4.auth.domain.user.controller;

import f4.auth.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
      @RequestParam(required = false, defaultValue = "username", value = "criteria") String criteria
  ) {
    return ResponseEntity.ok(userService.getUsers(pageNo, criteria));
  }
}
