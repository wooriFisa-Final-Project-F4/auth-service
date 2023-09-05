package f4.auth.domain.user.service;

import f4.auth.domain.user.dto.request.LinkRequestDto;
import f4.auth.domain.user.dto.request.SignupRequestDto;
import f4.auth.domain.user.dto.response.LinkingResponseDto;
import f4.auth.domain.user.service.feign.dto.response.UserCheckResponseDto;
import f4.auth.domain.user.dto.response.ProductResponseDto;
import f4.auth.domain.user.dto.response.UserResponseDto;
import f4.auth.domain.user.service.feign.dto.response.CheckBalanceResponseDto;
import java.util.List;

public interface UserService {

  void save(SignupRequestDto signupRequestDto);

  UserResponseDto findByUserId(Long userId);

  UserCheckResponseDto findByUserIdForOtherService(Long userId);

  List<UserResponseDto> findAll(int pageNo, String criteria);

  ProductResponseDto existsByUserId(Long userId);

  LinkingResponseDto linkingAccount(Long userId, LinkRequestDto linkRequestDto);

  CheckBalanceResponseDto checkBalance(Long userId);
}
