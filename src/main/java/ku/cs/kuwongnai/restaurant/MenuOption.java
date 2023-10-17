package ku.cs.kuwongnai.restaurant;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
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
