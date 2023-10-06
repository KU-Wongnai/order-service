package ku.cs.kuwongnai.order;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import ku.cs.kuwongnai.cart.CartItem;
import ku.cs.kuwongnai.cart.CartService;
import ku.cs.kuwongnai.restaurant.Menu;
import ku.cs.kuwongnai.restaurant.MenuRepository;
import ku.cs.kuwongnai.restaurant.Restaurant;
import ku.cs.kuwongnai.restaurant.RestaurantRepository;

@Service
public class OrderService {

  @Autowired
  private CartService cartService;

  @Autowired
  private BillRepository billRepository;

  @Autowired
  private RestaurantRepository restaurantRepository;

  @Autowired
  private PurchaseOrderRepository purchaseOrderRepository;

  @Autowired
  private OrderItemRepository orderItemRepository;

  @Autowired
  private MenuRepository menuRepository;

  public void checkout(String userId) {

    // Retrieve items from a cart
    List<CartItem> cartItems = cartService.getCartItems(userId);

    Map<Restaurant, PurchaseOrder> restaurantOrder = new HashMap<>();
    Bill bill = new Bill();

    bill.setUserId(Long.parseLong(userId));
    // Create a receipt for this orders
    bill = billRepository.save(bill);

    // Create orders with order items base on the item in cart, for every
    // restaurants user order
    for (CartItem cartItem : cartItems) {

      // Get menu details to store to order item
      Menu menu = menuRepository.findById(cartItem.getMenuId()).orElse(null);
      // Get resturant from menu id
      Restaurant restaurant = restaurantRepository.findById(menu.getRestaurant().getId()).orElse(null);

      if (menu == null || restaurant == null) {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,
            "Restaurant or Menu you are trying to order doesn't exist");
      }

      OrderItem item = new OrderItem();

      item.setMenuId(cartItem.getMenuId());
      item.setQuantity(cartItem.getQuantity());
      item.setPrice(menu.getPrice());

      PurchaseOrder order = restaurantOrder.get(restaurant);

      // Group items from the same restaurant to become one order
      if (order == null) {

        order = new PurchaseOrder();
        order.setRestaurantId(restaurant.getId());
        order.setBill(bill);
        purchaseOrderRepository.save(order);

        restaurantOrder.put(restaurant, order);

        System.out.println("Create new order for restaurant id = " + restaurant.getId());
      }

      item.setOrder(order);
      orderItemRepository.save(item);
    }

    // Clear all items inside the cart
    cartService.clearCart(userId);

    // Make a payment by return an url that redirect to the payment page
    System.out.println("Make a payment");
  }

  public List<Bill> getAllMyBills(Long userId) {
    return billRepository.findByUserId(userId);
  }

  public Bill getMyBill(Long userId, UUID receiptId) {

    Bill bill = billRepository.findById(receiptId).orElse(null);

    if (bill == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Receipt with the given id not found");
    }

    if (bill.getUserId() != userId) {
      throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You don't have permission to view this content");
    }

    return bill;
  }
}
