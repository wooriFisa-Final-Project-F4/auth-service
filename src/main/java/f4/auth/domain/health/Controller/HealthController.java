package f4.auth.domain.health.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

  @GetMapping
  public String getHealth() {

    return  "It's Working in user-service";
  }
}
