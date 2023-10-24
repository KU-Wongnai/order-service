package ku.cs.kuwongnai.cart;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import ku.cs.kuwongnai.restaurant.menu.Menu;
import ku.cs.kuwongnai.restaurant.menu.option.MenuOption;
import lombok.Data;

@Data
public class CartResponse implements Serializable {

  private Menu menu;

  private double quantity;

  private List<MenuOption> options = new ArrayList<>();

}
