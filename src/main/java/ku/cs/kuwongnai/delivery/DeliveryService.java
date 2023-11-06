package ku.cs.kuwongnai.delivery;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import ku.cs.kuwongnai.order.PurchaseOrder;

@Service
public class DeliveryService {

  @Autowired
  private DeliveryRepository deliveryRepository;

  // TODO: Check if user's role is rider

  public List<Delivery> getAllUnAssignDelivery() {
    // TODO: Should filter based on the rider's location

    return deliveryRepository.findByStatus(DeliveryStatus.PENDING);
  }

  public List<Delivery> getAllDeliveryForMe(Long riderId) {
    return deliveryRepository.findByRiderId(riderId);
  }

  public List<Delivery> getAllDeliveryForMe(Long riderId, DeliveryStatus status) {
    return deliveryRepository.findByRiderIdAndStatus(riderId, status);
  }

  public List<Delivery> getAllDelivery() {
    return deliveryRepository.findAll();
  }

  public Delivery getDelivery(UUID deliveryId) {
    Delivery delivery = deliveryRepository.findById(deliveryId).orElseThrow();
    PurchaseOrder order = delivery.getOrder();
    // System.out.println("Order = " + order);
    // PurchaseOrder order = orderRepository.findById(deliveryId).orElseThrow();
    // delivery.setOrder(order);
    // System.out.println(delivery);
    return delivery;
  }

  public Delivery assignDelivery(UUID deliveryId, Long riderId) {

    Delivery delivery = deliveryRepository.findById(deliveryId).orElseThrow();

    // Check if this delivery order is already assigned or delivered
    if (delivery.getStatus() != DeliveryStatus.PENDING) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Delivery is not pending");
    }

    // Check if this rider is already assigned to another delivery order
    // List<Delivery> deliveries = deliveryRepository.findByRiderId(riderId);
    // for (Delivery d : deliveries) {
    // if (d.getStatus() != DeliveryStatus.DELIVERED) {
    // throw new ResponseStatusException(HttpStatus.CONFLICT,
    // "Rider is already assigned to another delivery. Please complete that delivery
    // first before assigning a new one.");
    // }
    // }

    // Assign the delivery to the rider
    delivery.setRiderId(riderId);
    delivery.setStatus(DeliveryStatus.ASSIGNED);

    return deliveryRepository.save(delivery);
  }

  public Delivery completeDelivery(UUID deliveryId, Long riderId) {

    Delivery delivery = deliveryRepository.findById(deliveryId).orElseThrow();

    if (delivery.getStatus() != DeliveryStatus.ASSIGNED || delivery.getStatus() == DeliveryStatus.PENDING) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Delivery is not assigned");
    }

    if (delivery.getRiderId() != riderId) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Rider is not assigned to this delivery");
    }

    delivery.setStatus(DeliveryStatus.DELIVERED);

    return deliveryRepository.save(delivery);
  }

  public Delivery cancelDelivery(UUID deliveryId, Long riderId) {

    Delivery delivery = deliveryRepository.findById(deliveryId).orElseThrow();

    if (delivery.getStatus() == DeliveryStatus.DELIVERED) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Delivery is already delivered");
    }

    if (delivery.getRiderId() != riderId) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Rider is not assigned to this delivery");
    }

    delivery.setStatus(DeliveryStatus.CANCELLED);

    return deliveryRepository.save(delivery);
  }

  public Pocket calculateTotalEarning(Long riderId) {
    List<Delivery> deliveries = deliveryRepository.findByRiderIdAndStatus(riderId, DeliveryStatus.DELIVERED);

    Double totalEarning = 0.0;

    for (Delivery delivery : deliveries) {
      totalEarning += delivery.getDeliveryFee();
    }

    Pocket pocket = new Pocket();
    pocket.setTotalIncome(totalEarning);
    pocket.setTransferable(totalEarning);

    return pocket;
  }

}
