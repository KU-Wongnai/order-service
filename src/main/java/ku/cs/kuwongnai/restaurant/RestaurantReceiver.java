package ku.cs.kuwongnai.restaurant;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RestaurantReceiver {

  @Autowired
  private RestaurantService restaurantService;

  @RabbitListener(queues = "#{restaurantCreatedQueue.name}")
  public void handleRestaurantCreatedMessage(Restaurant restaurant) {
    // Handle restaurant created
    System.out.println(restaurant);
    restaurantService.createRestaurant(restaurant);
    System.out.println("From Restaurant Service : Restaurant has been created");
  }

  @RabbitListener(queues = "#{restaurantUpdatedQueue.name}")
  public void handleRestaurantUpdatedMessage(Restaurant restaurant) {
    // Handle restaurant updated message
    restaurantService.updateRestaurant(restaurant);
    System.out.println("From Restaurant Service : Restaurant has been updated");
  }

  @RabbitListener(queues = "#{restaurantDeletedQueue.name}")
  public void handleRestaurantDeletedMessage(Long id) {
    // Handle restaurant deleted message
    restaurantService.deleteRestaurant(id);
    System.out.println("From Restaurant Service : Restaurant has been deleted");
  }
}
