package f4.auth.domain.user.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import f4.auth.domain.user.persist.entity.User;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponseDto implements Serializable {

  private Long userId;
  private String username;
  private String gender;
  private String birth;
  private String address;
  private String email;
  private String phoneNumber;
  private LocalDateTime createdAt;
  private LocalDateTime updateAt;

  public static UserResponseDto toDto(final User user) {
    return UserResponseDto.builder()
        .userId(user.getId())
        .username(user.getUsername())
        .gender(user.getGender())
        .birth(user.getBirth())
        .address(user.getAddress())
        .email(user.getEmail())
        .phoneNumber(user.getPhoneNumber())
        .createdAt(user.getCreatedAt())
        .updateAt(user.getUpdatedAt())
        .build();
  }
}
