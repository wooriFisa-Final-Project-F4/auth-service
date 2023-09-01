package f4.auth.domain.auth.service;

import f4.auth.domain.auth.dtto.request.LoginRequestDto;
import f4.auth.domain.auth.dtto.response.TokenResponseDto;

public interface AuthService {

  TokenResponseDto login(LoginRequestDto loginRequestDto);
}
