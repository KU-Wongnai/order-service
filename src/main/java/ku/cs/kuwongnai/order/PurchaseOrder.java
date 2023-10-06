package ku.cs.kuwongnai.order;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

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
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode
public class PurchaseOrder {

  @Id
  @GeneratedValue
  private UUID id;

  // TODO: Change to `Restaurant` entity
  private Long restaurantId;

  @JsonManagedReference
  @OneToMany(mappedBy = "order")
  private List<OrderItem> orderItems = new ArrayList<>();

  @Enumerated(EnumType.STRING)
  private OrderStatus status = OrderStatus.PENDING;

  @OneToOne
  @JoinColumn(name = "delivery_id", referencedColumnName = "id")
  private Delivery delivery;

  @JsonBackReference
  @ManyToOne
  @JoinColumn(name = "bill_id", referencedColumnName = "id")
  private Bill bill;

}