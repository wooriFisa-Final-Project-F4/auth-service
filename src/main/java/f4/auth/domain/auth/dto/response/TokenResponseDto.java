package f4.auth.domain.auth.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TokenResponseDto {

  @Builder.Default
  private String grantType = "Bearer ";
  private String accessToken;
  private String refreshToken;
}