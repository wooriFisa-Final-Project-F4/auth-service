package f4.auth.domain.user.dto.request;

import lombok.*;

import javax.validation.constraints.Email;

@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class LoginRequestDto {

    @Email
    private String email;
    private String password;
}
