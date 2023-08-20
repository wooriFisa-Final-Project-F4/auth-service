package f4.auth.global.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CustomErrorCode {

    ALREADY_REGISTERED_USER("/user/v1/signup", 400, "이미 회원 가입한 계정이 존재합니다.");

    private final String path;
    private final int code;
    private final String message;
}