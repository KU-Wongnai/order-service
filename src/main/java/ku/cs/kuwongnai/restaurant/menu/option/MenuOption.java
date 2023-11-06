package ku.cs.kuwongnai.restaurant.menu.option;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MenuOption {
  @Id
  private Long id;
  private String name;
  private double price;
  private String category;

  private Long menuId;

}
