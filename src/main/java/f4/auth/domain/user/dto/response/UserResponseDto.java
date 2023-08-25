package f4.auth.domain.user.dto.response;

import f4.auth.domain.user.persist.entity.User;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDto {

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
