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
import ku.cs.kuwongnai.restaurant.Restaurant;
import ku.cs.kuwongnai.restaurant.RestaurantRepository;
import ku.cs.kuwongnai.restaurant.menu.Menu;
import ku.cs.kuwongnai.restaurant.menu.MenuRepository;
import ku.cs.kuwongnai.restaurant.menu.option.MenuOption;
import ku.cs.kuwongnai.restaurant.menu.option.MenuOptionRepository;
import ku.cs.kuwongnai.user.User;
import ku.cs.kuwongnai.user.UserRepository;

@Service
public class OrderService {

  @Autowired
  private CartService cartService;

  @Autowired
  private BillRepository billRepository;

  @Autowired
  private RestaurantRepository restaurantRepository;

  @Autowired
  private MenuRepository menuRepository;

  @Autowired
  private MenuOptionRepository menuOptionRepository;

  @Autowired
  private DeliveryRepository deliveryRepository;

  @Autowired
  private PurchaseOrderRepository orderRepository;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private WebClient webClient;

  public Bill placeOrder(String userId, PaymentRequest paymentRequest) {

    // Retrieve items from a cart
    List<CartItem> cartItems = cartService.getCartItemsRaw(userId);

    if (cartItems.size() == 0) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cart is empty");
    }

    Map<Restaurant, PurchaseOrder> restaurantOrder = new HashMap<>();

    User user = userRepository.findById(Long.parseLong(userId)).orElseThrow();
    final Bill bill = createBill(user, paymentRequest);

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
      item.setMenu(menu);
      item.setQuantity(cartItem.getQuantity());
      item.setPrice(menu.getPrice());

      for (Long optionId : cartItem.getOptionIds()) {
        MenuOption option = menuOptionRepository.findById(optionId).orElseThrow();

        OrderItemOption orderItemOption = new OrderItemOption();
        orderItemOption.setCategory(option.getCategory());
        orderItemOption.setName(option.getName());
        orderItemOption.setPrice(option.getPrice());

        orderItemOption.setOrderItem(item);
      }

      item.calculateTotalPrice();

      PurchaseOrder order = restaurantOrder.computeIfAbsent(restaurant, r -> {
        PurchaseOrder o = new PurchaseOrder();
        o.setRestaurant(r);
        o.setBill(bill);
        o.setUser(user);
        return o;
      });

      item.setOrder(order);
    }

    bill.calculateTotalPrice();
    Bill record = billRepository.save(bill);

    return record;
  }

  private Bill createBill(User user, PaymentRequest paymentRequest) {
    Bill bill = new Bill();
    bill.setUser(user);
    bill.setDeliveryAddress(paymentRequest.getDeliveryAddress());
    bill.setContactInfo(paymentRequest.getPhoneNumber());
    return billRepository.save(bill);
  }

  public List<Bill> getAllMyBills(Long userId) {
    return billRepository.findByUserId(userId);
  }

  public Bill getMyBill(Long userId, UUID receiptId) {

    Bill bill = billRepository.findById(receiptId).orElse(null);

    if (bill == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Receipt with the given id not found");
    }

    if (bill.getUser().getId() != userId) {
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
    Bill record = billRepository.findById(bill.getId()).orElseThrow();
    record.setStatus(UserPurchaseStatus.PAID);
    record = billRepository.save(record);

    for (PurchaseOrder order : record.getOrders()) {

      Delivery delivery = new Delivery();
      delivery.setOrder(order);
      delivery.setDeliveryAddress(record.getDeliveryAddress());
      delivery.setContactInfo(record.getContactInfo());

      deliveryRepository.save(delivery);
    }

    cartService.clearCart(userId);

  }

  public void cancelOrder(Bill bill) {
    bill.setStatus(UserPurchaseStatus.CANCELLED);
    billRepository.save(bill);
  }

  public PurchaseOrder updateOrderStatus(UUID orderId, OrderStatus status) {
    PurchaseOrder order = orderRepository.findById(orderId).orElseThrow();

    // Status cannot go back to previous status
    // (pending -> preparing -> received -> completed)
    if (order.getStatus() == OrderStatus.COMPLETED) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Order is already completed. Cannot change back.");
    }

    if (order.getStatus() == OrderStatus.PREPARING
        && (status == OrderStatus.PENDING || status == OrderStatus.RECEIVED)) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Order is already preparing. Cannot change back.");
    }

    if (order.getStatus() == OrderStatus.RECEIVED && status == OrderStatus.PENDING) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Order is already received. Cannot change back.");
    }

    order.setStatus(status);
    return orderRepository.save(order);
  }

  public List<PurchaseOrder> getRestaurantOrders(Long restaurantID) {
    return orderRepository.findByRestaurantId(restaurantID);
  }

  public List<PurchaseOrder> getRestaurantOrders(Long restaurantID, OrderStatus status) {
    return orderRepository.findByRestaurantIdAndStatus(restaurantID, status);
  }

  public PurchaseOrder getOrder(UUID orderID) {
    PurchaseOrder order = orderRepository.findById(orderID).orElseThrow();
    Delivery delivery = deliveryRepository.findByOrderId(orderID);

    delivery.setOrder(null);
    order.setDelivery(delivery);

    return order;
  }

  public List<PurchaseOrder> getAllOrders() {
    return orderRepository.findAll();
  }

  public List<PurchaseOrder> getMyOrders(Long userId) {
    return orderRepository.findByUserId(userId);
  }

  public List<PurchaseOrder> getMyOrders(Long userId, OrderStatus status) {
    return orderRepository.findByUserIdAndStatus(userId, status);
  }
}
