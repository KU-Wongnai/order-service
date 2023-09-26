package ku.cs.kuwongnai.cart;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
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
  public void addToCart(@AuthenticationPrincipal Jwt jwt, @RequestBody CartItem cartItem) {
    String userId = (String) jwt.getClaims().get("sub");
    cartService.addToCart(userId, cartItem);
    System.out.println("Add to cart: " + cartItem + " From user id = " + userId);
  }

  @GetMapping
  public List<CartItem> viewCart(@AuthenticationPrincipal Jwt jwt) {
    String userId = (String) jwt.getClaims().get("sub");
    return cartService.getCartItems(userId);
  }

  @DeleteMapping
  public void removeFromCart(@AuthenticationPrincipal Jwt jwt, @RequestParam Long menuId) {
    String userId = (String) jwt.getClaims().get("sub");
    cartService.removeFromCart(userId, menuId);
  }

}
