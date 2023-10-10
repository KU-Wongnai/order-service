package ku.cs.kuwongnai.order;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;

import ku.cs.kuwongnai.cart.CartItem;
import ku.cs.kuwongnai.cart.CartService;
import ku.cs.kuwongnai.delivery.Delivery;
import ku.cs.kuwongnai.delivery.DeliveryRepository;
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

  @Autowired
  private DeliveryRepository deliveryRepository;

  @Autowired
  private WebClient webClient;

  public Bill placeOrder(String userId, String deliveryAddress) {

    // Retrieve items from a cart
    List<CartItem> cartItems = cartService.getCartItemsRaw(userId);

    if (cartItems.size() == 0) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cart is empty");
    }

    Map<Restaurant, PurchaseOrder> restaurantOrder = new HashMap<>();
    Bill bill = new Bill();

    bill.setUserId(Long.parseLong(userId));
    bill.setDeliveryAddress(deliveryAddress);
    // Create a receipt for this orders
    bill = billRepository.save(bill);

    double totalPrice = 0;

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

      totalPrice += menu.getPrice() * cartItem.getQuantity();

      PurchaseOrder order = restaurantOrder.get(restaurant);

      // Group items from the same restaurant to become one order
      if (order == null) {

        order = new PurchaseOrder();
        order.setRestaurantId(restaurant.getId());
        order.setBill(bill);
        bill.getOrders().add(order);

        restaurantOrder.put(restaurant, order);

        System.out.println("Create new order for restaurant id = " + restaurant.getId());
      }

      item.setOrder(order);
      order.getOrderItems().add(item);
    }

    bill.setTotalPrice(totalPrice);
    Bill record = billRepository.save(bill);

    return record;
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

  public void chargeCard(Jwt jwt, Bill bill, PaymentRequest paymentRequest) {
    paymentRequest.setBillId(bill.getId());
    paymentRequest.setAmount(bill.getTotalPrice());

    // Make a POST request to http://localhost:8095/api/checkout
    webClient.post().uri("/checkout")
        .header("Authorization", "Bearer " + jwt.getTokenValue())
        .bodyValue(paymentRequest)
        .retrieve()
        .bodyToMono(String.class)
        .block();

    System.out.println("Charge card");
  }

  public void confirmOrder(String userId, Bill bill) {
    bill.setStatus(UserPurchaseStatus.PAID);
    cartService.clearCart(userId);

    billRepository.save(bill);

    Bill record = billRepository.findById(bill.getId()).orElse(null);
    System.out.println(record);

    for (PurchaseOrder order : record.getOrders()) {
      System.out.println(order);
      order.setStatus(OrderStatus.PREPARING);
      purchaseOrderRepository.save(order);

      Delivery delivery = new Delivery();
      delivery.setOrder(order);
      delivery.setDeliveryAddress(bill.getDeliveryAddress());

      deliveryRepository.save(delivery);
    }
  }

  public void cancelOrder(Bill bill) {
    bill.setStatus(UserPurchaseStatus.CANCELLED);
    billRepository.save(bill);
  }

}
