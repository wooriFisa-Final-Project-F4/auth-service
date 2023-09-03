package f4.auth.domain.user.service.impl;

import static f4.auth.domain.user.constant.Role.USER;

import f4.auth.domain.user.dto.request.SignupRequestDto;
import f4.auth.domain.user.dto.response.MailingResponseDto;
import f4.auth.domain.user.dto.response.UserResponseDto;
import f4.auth.domain.user.persist.entity.User;
import f4.auth.domain.user.persist.repository.UserRepository;
import f4.auth.domain.user.service.UserService;
import f4.auth.global.constant.CustomErrorCode;
import f4.auth.global.exception.CustomException;
import f4.auth.global.utils.Encryptor;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final ModelMapper modelMapper;
  private final Encryptor crypto;
  private static final int PAGE_SIZE = 10;

  @Override
  public void save(SignupRequestDto signupRequestDto) {
    existsByUserEmail(signupRequestDto.getEmail());
    userRepository.save(getUserBuild(signupRequestDto));
  }

  private User getUserBuild(SignupRequestDto signupRequestDto) {
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

  private void existsByUserEmail(String email) {
    userRepository
        .findByEmail(email)
        .ifPresent(
            data -> {
              throw new CustomException(CustomErrorCode.ALREADY_REGISTERED_MEMBER);
            });
  }

  // userId로 유저 정보 조회
  @Override
  public UserResponseDto findByUserId(Long userId) {
    User user = getUser(userId);
    return modelMapper.map(user, UserResponseDto.class);
  }

  // mail-service 에서 필요한 정보 userId로 조회
  @Override
  public MailingResponseDto findByUserIdForMailing(Long userId) {
    User user = getUser(userId);
    return modelMapper.map(user, MailingResponseDto.class);
  }

  // admin 유저 전체 정보 조회
  private User getUser(Long userId) {
    return userRepository.findById(userId)
        .orElseThrow(() -> new CustomException(CustomErrorCode.NOT_FOUND_USER));
  }

  @Override
  public List<UserResponseDto> findAll(int pageNo, String criteria) {
    Pageable pageable = PageRequest.of(pageNo - 1, PAGE_SIZE, Sort.by(Direction.ASC, criteria));

    return userRepository.findAll(pageable)
        .getContent().stream()
        .map(UserResponseDto::toDto)
        .toList();
  }
}


