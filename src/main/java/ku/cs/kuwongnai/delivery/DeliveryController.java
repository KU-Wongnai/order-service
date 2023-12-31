package ku.cs.kuwongnai.delivery;

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

import ku.cs.kuwongnai.notification.NotificationSender;

@RestController
@RequestMapping("/api/deliveries")
public class DeliveryController {

  @Autowired
  private NotificationSender notificationSender;

  @Autowired
  private DeliveryService deliveryService;

  @GetMapping
  public List<Delivery> getAllDelivery() {
    return deliveryService.getAllDelivery();
  }

  @GetMapping("/unassign")
  public List<Delivery> getAllUnAssignDelivery() {
    return deliveryService.getAllUnAssignDelivery();
  }

  @GetMapping("/me")
  public List<Delivery> getAllDeliveryForMe(@AuthenticationPrincipal Jwt jwt) {
    Long riderId = Long.parseLong((String) jwt.getClaim("sub"));
    return deliveryService.getAllDeliveryForMe(riderId);
  }

  @GetMapping("/{deliveryId}")
  public Delivery getDelivery(@PathVariable UUID deliveryId) {
    return deliveryService.getDelivery(deliveryId);
  }

  @GetMapping("/me/{status}")
  public List<Delivery> getAllDeliveryForMe(@AuthenticationPrincipal Jwt jwt, @PathVariable DeliveryStatus status) {
    Long riderId = Long.parseLong((String) jwt.getClaim("sub"));
    return deliveryService.getAllDeliveryForMe(riderId, status);
  }

  @PostMapping("/{deliveryId}/assign")
  public Delivery assignDelivery(@AuthenticationPrincipal Jwt jwt, @PathVariable UUID deliveryId) {
    Long riderId = Long.parseLong((String) jwt.getClaim("sub"));
    try {
      notificationSender.sendInAppRiderNewOrder(riderId.toString());
      notificationSender
          .sendInAppUserDeliveryOrder(deliveryService.getDelivery(deliveryId).getOrder().getUser().getId().toString());
    } catch (Exception e) {
      e.printStackTrace();
    }
    return deliveryService.assignDelivery(deliveryId, riderId);
  }

  @PostMapping("/{deliveryId}/complete")
  public Delivery completeDelivery(@AuthenticationPrincipal Jwt jwt, @PathVariable UUID deliveryId) {
    Long riderId = Long.parseLong((String) jwt.getClaim("sub"));
    try {
      notificationSender.sendInAppRiderOrderFinished(riderId.toString());
      notificationSender.sendInAppUserDeliveryFinished(
          deliveryService.getDelivery(deliveryId).getOrder().getUser().getId().toString());
    } catch (Exception e) {
      e.printStackTrace();
    }
    return deliveryService.completeDelivery(deliveryId, riderId);
  }

  @PostMapping("/{deliveryId}/cancel")
  public Delivery cancelDelivery(@AuthenticationPrincipal Jwt jwt, @PathVariable UUID deliveryId) {
    Long riderId = Long.parseLong((String) jwt.getClaim("sub"));
    try {
      notificationSender.sendInAppRiderOrderCanceled(riderId.toString());
      notificationSender
          .sendInAppUserOrderCanceled(deliveryService.getDelivery(deliveryId).getOrder().getUser().getId().toString());
    } catch (Exception e) {
      e.printStackTrace();
    }
    return deliveryService.cancelDelivery(deliveryId, riderId);
  }

  @GetMapping("/me/earning")
  public Pocket getEarning(@AuthenticationPrincipal Jwt jwt) {
    Long riderId = Long.parseLong((String) jwt.getClaim("sub"));
    return deliveryService.calculateTotalEarning(riderId);
  }

}
