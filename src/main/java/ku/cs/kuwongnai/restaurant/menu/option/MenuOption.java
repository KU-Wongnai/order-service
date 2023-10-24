package ku.cs.kuwongnai.restaurant.menu.option;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class MenuOption {
  @Id
  private Long id;
  private String name;
  private double price;
  private String category;

  private Long menuId;

}
