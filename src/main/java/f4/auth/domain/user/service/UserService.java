package f4.auth.domain.user.service;

import f4.auth.domain.user.dto.request.SignupRequestDto;

public interface UserService {

  void register(SignupRequestDto signupRequestDto);
}
