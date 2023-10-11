package ku.cs.kuwongnai.restaurant;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class Restaurant {

  @Id
  private Long id;

  private String name;
  private String location;
  private String contactInfo;
  private String image;

  @OneToMany(mappedBy = "restaurant")
  @JsonManagedReference
  private List<Menu> menu;
}
