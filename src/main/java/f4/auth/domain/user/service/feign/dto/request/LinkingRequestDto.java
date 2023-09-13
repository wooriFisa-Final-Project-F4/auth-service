package f4.auth.domain.user.service.feign.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LinkingRequestDto {

  @NotNull
  private Long arteUserId;
  @NotBlank
  private String name;
  @NotBlank
  private String accountNumber;
  @NotBlank
  private String password;
}
