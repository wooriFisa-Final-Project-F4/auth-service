package f4.auth.domain.user.service.feign;

import f4.auth.domain.user.service.feign.dto.request.CheckBalanceRequestDto;
import f4.auth.domain.user.service.feign.dto.request.LinkingRequestDto;
import f4.auth.domain.user.service.feign.dto.response.ApiResponse;
import javax.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "Mock", url = "https://5ca4-14-32-118-124.ngrok-free.app")
public interface WooriMockServiceApi {

  @PostMapping("/woori/account/v1/linking")
  ApiResponse<?> linkingAccount(@Valid @RequestBody LinkingRequestDto linkingRequestDto);

  @PostMapping("woori/account/v1/check/balance")
  ApiResponse<?> checkBalance(@Valid @RequestBody CheckBalanceRequestDto checkBalanceRequestDto);
}
