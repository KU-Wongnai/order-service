package ku.cs.kuwongnai.delivery;

import java.util.UUID;

import jakarta.annotation.Nullable;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import ku.cs.kuwongnai.order.PurchaseOrder;
import lombok.Data;

@Entity
@Data
public class Delivery {

  @Id
  @GeneratedValue
  private UUID id;

  /**
   * The rider who delivers food to users.
   */
  @Nullable
  private Long riderId = null;

  private String deliveryAddress;
  private String contactInfo;
  private Double deliveryFee = 40.0; // 40 baht

  @Enumerated(EnumType.STRING)
  private DeliveryStatus status = DeliveryStatus.PENDING;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "order_id", referencedColumnName = "id")
  private PurchaseOrder order;

}
