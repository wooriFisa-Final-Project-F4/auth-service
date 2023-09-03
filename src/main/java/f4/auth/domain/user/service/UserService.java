package f4.auth.domain.user.service;

import f4.auth.domain.user.dto.request.SignupRequestDto;
import f4.auth.domain.user.dto.response.MailingResponseDto;
import f4.auth.domain.user.dto.response.UserResponseDto;
import java.util.List;

public interface UserService {

  void save(SignupRequestDto signupRequestDto);

  UserResponseDto findByUserId(Long userId);

  MailingResponseDto findByUserIdForMailing(Long userId);

  List<UserResponseDto> findAll(int pageNo, String criteria);

}
