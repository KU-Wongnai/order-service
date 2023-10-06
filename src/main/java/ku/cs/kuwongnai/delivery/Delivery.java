package ku.cs.kuwongnai.delivery;

import java.util.UUID;

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
  private Long riderId;

  @Enumerated(EnumType.STRING)
  private DeliveryStatus status;

  @OneToOne
  @JoinColumn(name = "order_id", referencedColumnName = "id")
  private PurchaseOrder order;

}