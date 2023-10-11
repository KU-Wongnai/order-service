package ku.cs.kuwongnai.restaurant;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
}
