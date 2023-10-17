package ku.cs.kuwongnai.cart;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class CartItem implements Serializable {

  private Long menuId;
  private int quantity;
  private List<Long> optionIds = new ArrayList<>();
}
