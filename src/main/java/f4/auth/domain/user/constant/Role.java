package f4.auth.domain.user.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Role {
  USER("USER"),
  ADMIN("ADMIN");

  private final String value;
}
