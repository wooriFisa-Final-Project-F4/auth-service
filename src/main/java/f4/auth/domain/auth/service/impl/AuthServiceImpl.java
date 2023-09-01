package f4.auth.domain.auth.service.impl;

import f4.auth.domain.auth.dtto.CreateTokenDto;
import f4.auth.domain.auth.dtto.request.LoginRequestDto;
import f4.auth.domain.auth.dtto.response.TokenResponseDto;
import f4.auth.domain.auth.service.AuthService;
import f4.auth.domain.user.persist.entity.User;
import f4.auth.domain.user.persist.repository.UserRepository;
import f4.auth.global.constant.CustomErrorCode;
import f4.auth.global.exception.CustomException;
import f4.auth.global.redis.RedisService;
import f4.auth.global.security.jwt.JwtTokenProvider;
import f4.auth.global.utils.Encryptor;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

  private final JwtTokenProvider jwtTokenProvider;
  private final RedisService redisService;
  private final UserRepository userRepository;
  private final Encryptor encryptor;
  private final ModelMapper modelMapper;

  @Value("${jwt.token.refresh-token-duration}")
  private Long rtkDuration;

  @Override
  @Transactional
  public TokenResponseDto login(LoginRequestDto loginRequestDto) {
    User user = userRepository.findByEmail(loginRequestDto.getEmail())
        .orElseThrow(() -> new CustomException(CustomErrorCode.NOT_FOUND_USER));

    if (!encryptor.matchers(loginRequestDto.getPassword(), user.getPassword())) {
      throw new CustomException(CustomErrorCode.NOT_VALID_LOGIN_PASSWORD);
    }

    CreateTokenDto createTokenDto = modelMapper.map(user, CreateTokenDto.class);

    final String atk = jwtTokenProvider.createAccessToken(createTokenDto);
    final String rtk = jwtTokenProvider.createRefreshToken(createTokenDto);

    redisService.setDataExpire(loginRequestDto.getEmail(), rtk, Duration.ofMillis(rtkDuration));

    return TokenResponseDto.builder()
        .accessToken(atk)
        .refreshToken(rtk)
        .build();
  }
}
