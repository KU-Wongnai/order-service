package ku.cs.kuwongnai.order;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class OrderItem {

  @Id
  @GeneratedValue
  private UUID id;

  private Long menuId;

  private int quantity;

  /**
   * Current price of the food at the bought time.
   */
  private double price;

  @ManyToOne
  @JoinColumn(name = "order_id")
  private PurchaseOrder order;

}
