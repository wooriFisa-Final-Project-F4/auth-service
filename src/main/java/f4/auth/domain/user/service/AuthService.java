package f4.auth.domain.user.service;

import f4.auth.domain.user.dto.request.LoginRequestDto;
import f4.auth.domain.user.dto.response.TokenResponseDto;

public interface AuthService {

  TokenResponseDto login(LoginRequestDto loginDto);

  TokenResponseDto reIssue(String rtk);
}
