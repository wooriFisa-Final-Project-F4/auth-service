package f4.auth.global.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ErrorDetails {

    private String path;
    private int code;
    private String message;
}