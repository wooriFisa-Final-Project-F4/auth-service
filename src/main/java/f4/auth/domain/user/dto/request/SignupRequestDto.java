package f4.auth.domain.user.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequestDto {

  @NotBlank(message = "회원 이름은 필수 입력 항목입니다.")
  private String username;

  @NotBlank(message = "성별은 필수 입력 항목입니다.")
  @Pattern(regexp = "[MW]")
  private String gender;

  @NotBlank(message = "생년월일은 필수 입력 항목입니다.")
  @Pattern(
      regexp = "^\\d{4}\\-(0[1-9]|1[012])\\-(0[1-9]|[12][0-9]|3[01])$",
      message = "잘못된 생년월일 양식입니다.")
  private String birth;

  @NotBlank(message = "주소는 필수 입력 항목입니다.")
  private String address;

  @NotBlank(message = "email은 필수 입력 항목입니다.")
  @Email
  private String email;

  @NotBlank(message = "패스워드는 필수 입력 항목입니다.")
  @Length(max = 30, message = "패스워드는 최대 30자리까지 입력하실 수 있습니다.")
  private String password;

  @NotBlank(message = "핸드폰 번호는 필수 입력 항목입니다.")
  @Pattern(regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}$", message = "핸드폰 번호의 양식과 맞지 않습니다. 01x-xxx(x)-xxxx")
  private String phoneNumber;
}
