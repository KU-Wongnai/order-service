package ku.cs.kuwongnai.cart;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;

@Service
public class CartService {
  private static final String CART_KEY_PREFIX = "user:cart:";

  @Autowired
  private RedisTemplate<String, Object> redisTemplate;

  private HashOperations<String, Long, CartItem> hashOperations;

  @PostConstruct
  private void init() {
    this.hashOperations = redisTemplate.opsForHash();
  }

  public void addToCart(String userId, CartItem cartItem) {
    String cartKey = getCartKey(userId);
    hashOperations.put(cartKey, cartItem.getMenuId(), cartItem);
  }

  public List<CartItem> getCartItems(String userId) {
    String cartKey = getCartKey(userId);
    return new ArrayList<CartItem>(hashOperations.entries(cartKey).values());
  }

  public void removeFromCart(String userId, Long menuId) {
    String cartKey = getCartKey(userId);
    hashOperations.delete(cartKey, menuId);
  }

  private String getCartKey(String userId) {
    return CART_KEY_PREFIX + userId;
  }

}
