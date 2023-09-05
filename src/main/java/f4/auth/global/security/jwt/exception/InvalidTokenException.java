package f4.auth.global.security.jwt.exception;


import f4.auth.global.constant.CustomErrorCode;
import f4.auth.global.exception.CustomException;
import lombok.Getter;

@Getter
public class InvalidTokenException extends CustomException {

  public InvalidTokenException(CustomErrorCode customErrorCode) {
    super(customErrorCode);
  }
}