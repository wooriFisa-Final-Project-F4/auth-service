package f4.auth.domain.user.service;

import f4.auth.domain.user.dto.request.SignupRequestDto;
import f4.auth.domain.user.dto.response.MailingResponseDto;

public interface UserService {

  void register(SignupRequestDto signupRequestDto);

  MailingResponseDto getUserByUserIdForMailing(Long userId);
}
