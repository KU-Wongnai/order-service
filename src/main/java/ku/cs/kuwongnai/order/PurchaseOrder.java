package ku.cs.kuwongnai.order;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
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
import ku.cs.kuwongnai.restaurant.Restaurant;
import ku.cs.kuwongnai.user.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode
public class PurchaseOrder {

  @Id
  @GeneratedValue
  private UUID id;

  // @JsonBackReference
  @ManyToOne
  @JoinColumn(name = "restaurant_id", referencedColumnName = "id")
  private Restaurant restaurant;

  @JsonManagedReference
  @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<OrderItem> orderItems = new ArrayList<>();

  @Enumerated(EnumType.STRING)
  private OrderStatus status = OrderStatus.PENDING;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "delivery_id", referencedColumnName = "id")
  private Delivery delivery;

  @JsonBackReference
  @ManyToOne
  @JoinColumn(name = "bill_id", referencedColumnName = "id")
  private Bill bill;

  @ManyToOne
  @JoinColumn(name = "user_id", referencedColumnName = "id")
  private User user;

  @CreationTimestamp
  @Column(name = "created_at", nullable = false, insertable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
  private LocalDateTime createdAt;

  @UpdateTimestamp
  @Column(name = "updated_at", nullable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
  private LocalDateTime updatedAt;

  /**
   * Calculate total price of all items in the order.
   * 
   * @return total price of all items in the order.
   */
  public double calculateTotalPrice() {
    double totalPrice = 0;
    for (OrderItem item : orderItems) {
      totalPrice += item.getTotalPrice();
    }
    return totalPrice;
  }

  public void setBill(Bill bill) {
    this.bill = bill;
    if (!bill.getOrders().contains(this)) {
      bill.getOrders().add(this);
    }
  }
}
