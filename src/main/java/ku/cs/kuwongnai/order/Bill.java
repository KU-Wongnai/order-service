package ku.cs.kuwongnai.order;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class Bill {

  @Id
  @GeneratedValue
  private UUID id;

  private Long userId;

  @Enumerated(EnumType.STRING)
  private UserPurchaseStatus status = UserPurchaseStatus.PROCESSING;

  /**
   * Bill has many orders. This happened when user order
   * from different restaurants.
   */
  @JsonManagedReference
  @OneToMany(mappedBy = "bill")
  private List<PurchaseOrder> orders = new ArrayList<>();

  private double totalPrice;

  private String deliveryAddress;

}
