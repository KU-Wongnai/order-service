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

  @GetMapping("/receipts")
  public List<Receipt> getAllMyReceipts(@AuthenticationPrincipal Jwt jwt) {
    String userId = (String) jwt.getClaims().get("sub");
    return orderService.getAllMyReceipts(Long.parseLong(userId));
  }

  @GetMapping("/receipts/{receiptId}")
  public Receipt getMyReceipt(@AuthenticationPrincipal Jwt jwt, @PathVariable UUID receiptId) {
    String userId = (String) jwt.getClaims().get("sub");
    return orderService.getMyReceipt(Long.parseLong(userId), receiptId);
  }

  // TODO: Add webhook to receive payment status after the purchased

}
