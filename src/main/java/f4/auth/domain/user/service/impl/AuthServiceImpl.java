package f4.auth.domain.user.service.impl;

import f4.auth.domain.user.dto.request.CreateTokenDto;
import f4.auth.domain.user.dto.request.LoginRequestDto;
import f4.auth.domain.user.dto.response.TokenResponseDto;
import f4.auth.domain.user.persist.entity.User;
import f4.auth.domain.user.persist.repository.UserRepository;
import f4.auth.domain.user.service.AuthService;
import f4.auth.global.constant.CustomErrorCode;
import f4.auth.global.exception.CustomException;
import f4.auth.global.redis.RedisService;
import f4.auth.global.security.jwt.JwtTokenProvider;
import f4.auth.global.security.jwt.JwtValidateService;
import f4.auth.global.utils.Encryptor;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

  private final JwtTokenProvider jwtTokenProvider;
  private final RedisService redisService;
  private final JwtValidateService jwtValidateService;
  private final UserRepository userRepository;
  private final Encryptor encryptor;
  private final ModelMapper modelMapper;

  @Value("${jwt.token.access-expiration-time}")
  private Long atkDuration;

  @Value("${jwt.token.refresh-expiration-time}")
  private Long rtkDuration;

  @Override
  @Transactional
  public TokenResponseDto login(LoginRequestDto loginDto) {
    User user = getUserByEmail(loginDto.getEmail());

    if (!encryptor.matchers(loginDto.getPassword(), user.getPassword())) {
      throw new CustomException(CustomErrorCode.NOT_VALID_LOGIN_PASSWORD);
    }

    return createTokenResponse(user, loginDto.getEmail());
  }

  @Override
  @Transactional
  public TokenResponseDto reIssue(String rtk) {
    jwtValidateService.validateRefreshToken(rtk);
    User user = getUserByEmail(jwtValidateService.getEmail(rtk));

    String atk = jwtTokenProvider.createAccessToken(modelMapper.map(user, CreateTokenDto.class));
    return TokenResponseDto.builder().accessToken(atk).build();
  }

  private User getUserByEmail(String email) {
    return userRepository
        .findByEmail(email)
        .orElseThrow(() -> new CustomException(CustomErrorCode.NOT_FOUND_MEMBER));
  }

  private TokenResponseDto createTokenResponse(User user, String email) {
    CreateTokenDto createTokenDto = modelMapper.map(user, CreateTokenDto.class);
    String atk = jwtTokenProvider.createAccessToken(createTokenDto);
    String rtk = jwtTokenProvider.createRefreshToken(createTokenDto);
    redisService.setDataExpire(email, rtk, Duration.ofMillis(rtkDuration));

    return TokenResponseDto.builder().accessToken(atk).refreshToken(rtk).build();
  }
}
