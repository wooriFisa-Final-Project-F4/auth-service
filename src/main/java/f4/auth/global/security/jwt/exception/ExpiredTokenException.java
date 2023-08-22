package f4.auth.global.security.jwt.exception;

import f4.auth.global.constant.CustomErrorCode;
import f4.auth.global.exception.CustomException;
import lombok.Getter;

@Getter
public class ExpiredTokenException extends CustomException {

  public ExpiredTokenException(CustomErrorCode customErrorCode) {
    super(customErrorCode);
  }
}
