package ku.cs.kuwongnai.restaurant.menu.option;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MenuOptionService {

  @Autowired
  private MenuOptionRepository menuOptionRepository;

  public MenuOption createMenuOption(MenuOption menuOption) {
    return menuOptionRepository.save(menuOption);
  }

  public MenuOption update(MenuOption menuOption) {
    return menuOptionRepository.save(menuOption);
  }

  public void delete(Long id) {
    menuOptionRepository.deleteById(id);
  }
}
