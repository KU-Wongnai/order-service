package ku.cs.kuwongnai.cart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ku.cs.kuwongnai.redis.RedisService;

@Service
public class CartService {
  private static final String CART_KEY_PREFIX = "user:cart:";

  @Autowired
  private RedisService<String, Cart> redisService;

  public void addToCart(String userId, Cart cartItem) {
    String cartKey = getCartKey(userId);
    redisService.saveData(cartKey, cartItem);
  }

  public Cart getCartItems(String userId) {
    String cartKey = getCartKey(userId);
    return redisService.getData(cartKey);
  }

  public void removeFromCart(String userId) {
    String cartKey = getCartKey(userId);
    redisService.removeData(cartKey);
  }

  private String getCartKey(String userId) {
    return CART_KEY_PREFIX + userId;
  }

}
