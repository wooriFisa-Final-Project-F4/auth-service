package f4.auth.global.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CustomErrorCode {

  // Bad Request 400
  CAN_NOT_ENCRYPT(400, "비밀번호를 암호화 할 수 없습니다."),
  NOT_VALID_PASSWORD(400, "비밀번호가 틀렸습니다."),
  ALREADY_REGISTERED_MEMBER(400, "이미 회원 가입한 계정이 존재합니다."),
  ALREADY_LOGOUT_USER(400, "이미 로그아웃된 유저입니다."),
  NOT_EXIST_ROLE(400, "해당 역할은 존재하지 않습니다."),
  NOT_VALID_ROLE(400, "해당 API를 호출하기에 적합하지 않은 역할입니다."),
  INCORRECT_CERTIFICATION_NUMBER(400, "인증번호가 틀렸습니다."),
  TIMEOUT_CERTIFICATION_NUMBER(400, "인증번호 입력 시간이 초과했습니다."),

  // Unathorized 401
  INVALID_ACCESS_TOKEN(401, "잘못된 토큰 입니다"),
  INVALID_REFRESH_TOKEN(401, "잘못된 토큰 입니다"),
  UNSUPPORTED_TOKEN(401, "지원하지 않는 토큰입니다"),
  EXPIRED_ACCESS_TOKEN(401, "만료된 엑세스토큰 입니다"),
  EXPIRED_REFRESH_TOKEN(401, "만료된 리프레시토큰 입니다"),
  EMPTY_ACCESS_TOKEN(400, "토큰이 비어있습니다"),

  // Forbidden 402

  // Not Found 404
  NOT_FOUND_USER(404, "해당 회원을 찾을 수 없습니다.");

  // Server Error

  private final int code;
  private final String message;
}
