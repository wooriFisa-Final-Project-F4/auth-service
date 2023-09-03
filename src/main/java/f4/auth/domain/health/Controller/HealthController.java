package f4.auth.domain.health.Controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/health")
@RequiredArgsConstructor
public class HealthController {

  private final Environment env;

  @GetMapping
  public String getHealth() {

    log.info("Health-Check 정상 작동중 입니다.");
    return  "It's Working in user-service";
  }
}
