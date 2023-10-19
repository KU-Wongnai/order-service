package ku.cs.kuwongnai.cart;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import jakarta.annotation.PostConstruct;
import ku.cs.kuwongnai.restaurant.Menu;
import ku.cs.kuwongnai.restaurant.MenuOption;
import ku.cs.kuwongnai.restaurant.MenuOptionRepository;
import ku.cs.kuwongnai.restaurant.MenuRepository;

@Service
public class CartService {
  private static final String CART_KEY_PREFIX = "user:cart:";

  @Autowired
  private RedisTemplate<String, Object> redisTemplate;

  private HashOperations<String, Long, CartItem> hashOperations;

  @Autowired
  private MenuRepository menuRepository;

  @Autowired
  private MenuOptionRepository menuOptionRepository;

  @PostConstruct
  private void init() {
    this.hashOperations = redisTemplate.opsForHash();
  }

  public void addToCart(String userId, CartItem cartItem) {
    Menu menu = menuRepository.findById(cartItem.getMenuId()).orElse(null);
    if (menu == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Menu was not exist");
    }

    String cartKey = getCartKey(userId);
    hashOperations.put(cartKey, cartItem.getMenuId(), cartItem);
  }

  public List<CartResponse> getCartItems(String userId) {
    String cartKey = getCartKey(userId);

    List<CartItem> cartItems = hashOperations.values(cartKey);
    List<CartResponse> cartResponses = new ArrayList<CartResponse>();

    for (CartItem cartItem : cartItems) {
      System.out.println(cartItem);
      Menu menu = menuRepository.findById(cartItem.getMenuId()).orElse(null);

      if (menu == null) {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Menu was not exist");
      }

      CartResponse cartResponse = new CartResponse();
      cartResponse.setMenu(menu);
      cartResponse.setQuantity(cartItem.getQuantity());

      for (Long optionId : cartItem.getOptionIds()) {
        MenuOption menuOption = menuOptionRepository.findById(optionId).orElse(null);
        if (menuOption == null) {
          throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Menu option was not exist");
        }
        cartResponse.getOptions().add(menuOption);
      }

      cartResponses.add(cartResponse);
    }

    return cartResponses;
  }

  public List<CartItem> getCartItemsRaw(String userId) {
    String cartKey = getCartKey(userId);

    List<CartItem> cartItems = hashOperations.values(cartKey);

    return cartItems;
  }

  public void removeFromCart(String userId, Long menuId) {
    String cartKey = getCartKey(userId);
    hashOperations.delete(cartKey, menuId);
  }

  public void clearCart(String userId) {
    String cartKey = getCartKey(userId);
    redisTemplate.delete(cartKey);
  }

  private String getCartKey(String userId) {
    return CART_KEY_PREFIX + userId;
  }

}
