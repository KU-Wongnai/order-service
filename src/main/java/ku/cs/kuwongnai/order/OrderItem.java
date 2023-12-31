package ku.cs.kuwongnai.order;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import ku.cs.kuwongnai.restaurant.menu.Menu;
import lombok.Data;

@Entity
@Data
public class OrderItem {

  @Id
  @GeneratedValue
  private UUID id;

  // @JsonBackReference
  @ManyToOne
  @JoinColumn(name = "menu_id", referencedColumnName = "id")
  private Menu menu;

  private int quantity;

  @OneToMany(mappedBy = "orderItem", cascade = CascadeType.ALL)
  private List<OrderItemOption> orderItemOption = new ArrayList<>();

  /**
   * Current price of the food at the bought time.
   */
  private double price;

  /**
   * Total price of the food plus options.
   */
  private double totalPrice = 0;

  @JsonBackReference
  @ManyToOne
  @JoinColumn(name = "order_id")
  private PurchaseOrder order;

  public void calculateTotalPrice() {
    totalPrice = price * quantity;
    for (OrderItemOption option : orderItemOption) {
      totalPrice += option.getPrice();
    }
  }

  public void setOrder(PurchaseOrder order) {
    this.order = order;
    if (!order.getOrderItems().contains(this)) {
      order.getOrderItems().add(this);
    }
  }

}
