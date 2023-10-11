package ku.cs.kuwongnai.restaurant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RestaurantService {

  @Autowired
  private RestaurantRepository restaurantRepository;

  public Restaurant getRestaurant(Long id) {
    return restaurantRepository.findById(id).orElse(null);
  }

  public Restaurant createRestaurant(Restaurant restaurant) {
    return restaurantRepository.save(restaurant);
  }

  public Restaurant updateRestaurant(Restaurant restaurant) {
    return restaurantRepository.save(restaurant);
  }

  public void deleteRestaurant(Long id) {
    restaurantRepository.deleteById(id);
  }
}
