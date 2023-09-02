package f4.auth.domain.auth.service;

import f4.auth.domain.auth.dto.request.LoginRequestDto;
import f4.auth.domain.auth.dto.response.TokenResponseDto;

public interface AuthService {

  TokenResponseDto login(LoginRequestDto loginRequestDto);
  TokenResponseDto reissue(String refreshToken);
}
