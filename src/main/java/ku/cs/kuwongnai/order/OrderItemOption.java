package ku.cs.kuwongnai.order;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class OrderItemOption {
  @Id
  @GeneratedValue
  private UUID id;
  private String name;
  private double price;
  private String category;

  @ManyToOne
  @JoinColumn(name = "order_item_id")
  @JsonIgnore
  private OrderItem orderItem;
}
