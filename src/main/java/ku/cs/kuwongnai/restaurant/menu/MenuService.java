package ku.cs.kuwongnai.restaurant.menu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ku.cs.kuwongnai.restaurant.Restaurant;
import ku.cs.kuwongnai.restaurant.RestaurantRepository;

@Service
public class MenuService {

  @Autowired
  private MenuRepository menuRepository;

  @Autowired
  private RestaurantRepository restaurantRepository;

  public Menu createMenu(MenuRequest menu) {
    Restaurant restaurant = restaurantRepository.findById(menu.getRestaurantId()).orElseThrow();
    Menu record = new Menu();

    record.setId(menu.getId());
    record.setName(menu.getName());
    record.setImage(menu.getImage());
    record.setPrice(menu.getPrice());
    record.setRestaurant(restaurant);

    // System.out.println(record);

    return menuRepository.save(record);
  }

  public Menu updateMenu(MenuRequest menu) {
    Restaurant restaurant = restaurantRepository.findById(menu.getRestaurantId()).orElseThrow();
    Menu record = new Menu();

    record.setId(menu.getId());
    record.setName(menu.getName());
    record.setImage(menu.getImage());
    record.setPrice(menu.getPrice());
    record.setRestaurant(restaurant);

    return menuRepository.save(record);
  }

  public void deleteMenu(Long id) {
    menuRepository.deleteById(id);
  }
}
