package ku.cs.kuwongnai.restaurant;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import ku.cs.kuwongnai.order.OrderItem;
import lombok.Data;

@Entity
@Data
public class Menu {

  @Id
  private Long id;

  private String name;
  private double price;
  private String image;

  @ManyToOne
  @JsonBackReference
  @JoinColumn(name = "restaurant_id", referencedColumnName = "id")
  private Restaurant restaurant;

  @OneToMany(mappedBy = "menu")
  @JsonIgnore
  private List<OrderItem> orderItems;

}
