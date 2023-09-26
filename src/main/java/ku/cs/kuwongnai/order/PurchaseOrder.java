package ku.cs.kuwongnai.order;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import ku.cs.kuwongnai.delivery.Delivery;
import lombok.Data;

@Entity
@Data
public class PurchaseOrder {

  @Id
  @GeneratedValue
  private UUID id;

  // TODO: Change to `User` and `Restaurant` entity
  private Long userId;
  private Long restaurantId;

  @OneToMany(mappedBy = "order")
  private List<OrderItem> orderItems;

  @Enumerated(EnumType.STRING)
  private OrderStatus status = OrderStatus.PENDING;

  @OneToOne
  @JoinColumn(name = "delivery_id", referencedColumnName = "id")
  private Delivery delivery;

  @ManyToOne
  @JoinColumn(name = "receipt_id")
  private Receipt receipt;

}
