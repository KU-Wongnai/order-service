package ku.cs.kuwongnai.restaurant;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Menu {

  @Id
  private Long id;

  private double price;

  @ManyToOne
  private Restaurant restaurant;
}
