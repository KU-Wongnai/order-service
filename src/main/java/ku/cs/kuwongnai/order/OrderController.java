package ku.cs.kuwongnai.order;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

  @GetMapping("/bills")
  public List<Bill> getAllMyBill(@AuthenticationPrincipal Jwt jwt) {
    String userId = (String) jwt.getClaims().get("sub");
    return orderService.getAllMyBills(Long.parseLong(userId));
  }

  @GetMapping("/bills/{billId}")
  public Bill getMyReceipt(@AuthenticationPrincipal Jwt jwt, @PathVariable UUID billId) {
    String userId = (String) jwt.getClaims().get("sub");
    return orderService.getMyBill(Long.parseLong(userId), billId);
  }

  // TODO: Add webhook to receive payment status after the purchased

}
