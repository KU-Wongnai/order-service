package ku.cs.kuwongnai.cart;

import java.io.Serializable;

import lombok.Data;

@Data
public class Cart implements Serializable {
  private Long menuId;
  private int quantity;
}
