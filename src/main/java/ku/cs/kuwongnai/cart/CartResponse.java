package ku.cs.kuwongnai.cart;

import java.io.Serializable;

import ku.cs.kuwongnai.restaurant.Menu;
import lombok.Data;

@Data
public class CartResponse implements Serializable {

  private Menu menu;

  private double quantity;

}
