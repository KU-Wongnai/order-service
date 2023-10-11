package ku.cs.kuwongnai.restaurant;

import lombok.Data;

@Data
public class MenuRequest {
  private Long id;

  private String name;
  private double price;
  private String image;

  private Long restaurantId;
}
