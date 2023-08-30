package f4.auth.domain.user.dto.response;

import f4.auth.domain.user.persist.entity.Users;
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

  private Long userId;
  private String username;
  private String gender;
  private String birth;
  private String address;
  private String email;
  private String phoneNumber;
  private LocalDateTime createdAt;
  private LocalDateTime updateAt;

  public static UserResponseDto toDto(final Users user) {
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
