package ku.cs.kuwongnai.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class OrderController {

  @Autowired
  private OrderService orderService;

  @PostMapping("checkout")
  public void checkout(@AuthenticationPrincipal Jwt jwt) {
    String userId = (String) jwt.getClaims().get("sub");

    orderService.checkout(userId); // TODO: return an url to the payment page
  }

}
