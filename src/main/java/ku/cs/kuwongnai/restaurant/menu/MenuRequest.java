package ku.cs.kuwongnai.restaurant.menu;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MenuRequest {
  private Long id;

  private String name;
  private double price;
  private String image;

  private Long restaurantId;
}
