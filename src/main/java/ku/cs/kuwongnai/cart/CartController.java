package ku.cs.kuwongnai.cart;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cart")
public class CartController {
  @Autowired
  private CartService cartService;

  @PostMapping
  public void addToCart(@RequestParam String userId, @RequestBody Cart cartItem) {
    cartService.addToCart(userId, cartItem);
    System.out.println("Add to cart: " + cartItem);
  }

  @GetMapping
  public Cart viewCart(@RequestParam String userId) {
    return cartService.getCartItems(userId);
  }

  @DeleteMapping
  public void removeFromCart(@RequestParam String userId) {
    cartService.removeFromCart(userId);
  }

}
