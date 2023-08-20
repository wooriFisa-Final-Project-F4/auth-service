package f4.auth.global.exception;

import lombok.Builder;
import lombok.Setter;

@Setter
@Builder
public class ErrorDetails {
    private String path;
    private int code;
    private String message;
}