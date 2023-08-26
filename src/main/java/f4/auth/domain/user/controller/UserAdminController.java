package f4.auth.domain.user.controller;

import f4.auth.domain.user.dto.response.UserResponseDto;
import f4.auth.domain.user.service.UserService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    Map<String, Object> data = new HashMap<>();
    List<UserResponseDto> users = userService.getUsers(pageNo, criteria);
    data.put("data", users);

    return ResponseEntity.ok(data);
  }
}
