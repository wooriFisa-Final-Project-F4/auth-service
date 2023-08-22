package f4.auth.global.security.jwt;

import f4.auth.global.constant.CustomErrorCode;
import f4.auth.global.exception.CustomException;
import f4.auth.global.redis.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtValidateService {

    private final JwtTokenProvider jwtTokenProvider;
    private final RedisService redisService;


    public String getEmail(String token) {
        return jwtTokenProvider.extractAllClaims(token)
                .get("email", String.class);
    }

    public void validateRefreshToken(String token) {
        if (redisService.getData(getEmail(token)) == null) {
            throw new CustomException(CustomErrorCode.INVALID_ACCESS_TOKEN);
        }
    }
}
