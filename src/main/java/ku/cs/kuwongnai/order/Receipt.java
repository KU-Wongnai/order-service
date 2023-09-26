package ku.cs.kuwongnai.order;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class Receipt {

  @Id
  @GeneratedValue
  private UUID id;

  private Long userId;

  @Enumerated(EnumType.STRING)
  private UserPurchaseStatus status = UserPurchaseStatus.PROCESSING;

  /**
   * Receipt has many orders. This happened when user order
   * from different restaurants.
   */
  @OneToMany(mappedBy = "receipt")
  private List<PurchaseOrder> orders = new ArrayList<>();

  private String deliveryAddress;

}
