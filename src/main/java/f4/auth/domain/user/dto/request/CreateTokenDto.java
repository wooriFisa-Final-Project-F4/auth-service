package f4.auth.domain.user.dto.request;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CreateTokenDto {

    private Long id;
    private String username;
    private String email;
}
