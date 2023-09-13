package f4.auth.domain.user.constant;

import f4.auth.global.constant.CustomErrorCode;
import f4.auth.global.exception.CustomException;
import java.util.Arrays;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Role {

  USER("USER"),
  ADMIN("ADMIN");

  private final String role;

  public static Role of(String role) {
    return Arrays.stream(values())
        .filter(i -> i.getRole().equals(role))
        .findFirst()
        .orElseThrow(() -> new CustomException(CustomErrorCode.NOT_EXIST_ROLE));
  }
}
