package ku.cs.kuwongnai.restaurant;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class Restaurant {

  @Id
  private Long id;

  @OneToMany(mappedBy = "restaurant")
  private List<Menu> menu;
}
