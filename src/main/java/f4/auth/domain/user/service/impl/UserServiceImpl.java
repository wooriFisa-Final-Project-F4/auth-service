package f4.auth.domain.user.service.impl;

import f4.auth.domain.user.dto.request.SignupRequestDto;
import f4.auth.domain.user.persist.entity.User;
import f4.auth.domain.user.persist.repository.UserRepository;
import f4.auth.domain.user.service.UserService;
import f4.auth.global.constant.CustomErrorCode;
import f4.auth.global.exception.CustomException;
import f4.auth.global.utils.Encryptor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static f4.auth.domain.user.constant.Role.USER;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final Encryptor crypto;

  @Override
  public void register(SignupRequestDto signupRequestDto) {
    loadByUserEmail(signupRequestDto);
    userRepository.save(getBuild(signupRequestDto));
  }

  private User getBuild(SignupRequestDto signupRequestDto) {
    String encryptPassword = crypto.encrypt(signupRequestDto.getPassword());
    return User.builder()
        .username(signupRequestDto.getUsername())
        .gender(signupRequestDto.getGender())
        .birth(signupRequestDto.getBirth())
        .address(signupRequestDto.getAddress())
        .email(signupRequestDto.getEmail())
        .password(encryptPassword)
        .phoneNumber(signupRequestDto.getPhoneNumber())
        .role(USER)
        .createdAt(LocalDateTime.now())
        .updatedAt(LocalDateTime.now())
        .build();
  }

  private void loadByUserEmail(SignupRequestDto signupRequestDto) {
    userRepository
        .findByEmail(signupRequestDto.getEmail())
        .ifPresent(
            data -> {
              throw new CustomException(CustomErrorCode.ALREADY_REGISTERED_MEMBER);
            });
  }
}
