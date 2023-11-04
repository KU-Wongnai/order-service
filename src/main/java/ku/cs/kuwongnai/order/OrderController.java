package ku.cs.kuwongnai.order;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import ku.cs.kuwongnai.notification.NotificationSender;

@RestController
@RequestMapping("/api")
public class OrderController {

  @Autowired
  private NotificationSender notificationSender;

  @Autowired
  private OrderService orderService;

  @PostMapping("checkout")
  public void checkout(@AuthenticationPrincipal Jwt jwt, @RequestBody PaymentRequest paymentRequest) {
    String userId = (String) jwt.getClaims().get("sub");

    Bill bill = orderService.placeOrder(userId, paymentRequest);

    System.out.println(paymentRequest);

    try {
      orderService.chargeCard(jwt, bill, paymentRequest);

      orderService.confirmOrder(userId, bill);
      notificationSender.sendInAppUserSuccessPayment(userId);
    } catch (Exception e) {
      orderService.cancelOrder(bill);

      e.printStackTrace();
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
    }

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

  @GetMapping("/order/{orderID}")
  public PurchaseOrder getOrder(@AuthenticationPrincipal Jwt jwt, @PathVariable UUID orderID) {
      return orderService.getOrder(orderID);
  }

  @PutMapping("/orders/{orderID}")
  public PurchaseOrder updateOrderStatus(@AuthenticationPrincipal Jwt jwt, @PathVariable UUID orderID,
      @RequestParam OrderStatus status) {
    return orderService.updateOrderStatus(orderID, status);
  }

  @GetMapping("/orders/restaurant/{RestaurantID}")
  public List<PurchaseOrder> getRestaurantOrders(@AuthenticationPrincipal Jwt jwt,
      @PathVariable Long RestaurantID) {
      return orderService.getRestaurantOrders(RestaurantID);
  }

  @GetMapping("/orders")
  public List<PurchaseOrder> getAllOrders(@AuthenticationPrincipal Jwt jwt) {
      return orderService.getAllOrders();
  }

}
