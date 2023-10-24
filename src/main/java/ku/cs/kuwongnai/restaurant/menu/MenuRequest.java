package ku.cs.kuwongnai.restaurant.menu;

import lombok.Data;

@Data
public class MenuRequest {
  private Long id;

  private String name;
  private double price;
  private String image;

  private Long restaurantId;
}
