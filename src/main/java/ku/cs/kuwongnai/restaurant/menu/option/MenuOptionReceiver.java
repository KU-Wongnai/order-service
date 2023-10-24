package ku.cs.kuwongnai.restaurant.menu.option;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MenuOptionReceiver {
  @Autowired
  private MenuOptionService menuOptionService;

  @RabbitListener(queues = "#{menuOptionCreatedQueue.name}")
  public void handleMenuOptionCreatedMessage(MenuOption menuOption) {
    // Handle restaurant created
    System.out.println(menuOption);
    menuOptionService.createMenuOption(menuOption);
    System.out.println("From Restaurant Service : MenuOption has been created");
  }

  @RabbitListener(queues = "#{menuOptionUpdatedQueue.name}")
  public void handleMenuOptionUpdatedMessage(MenuOption menuOption) {
    // Handle restaurant updated message
    menuOptionService.update(menuOption);
    System.out.println("From Restaurant Service : MenuOption has been updated");
  }

  @RabbitListener(queues = "#{menuOptionDeletedQueue.name}")
  public void handleMenuOptionDeletedMessage(Long id) {
    // Handle restaurant deleted message
    menuOptionService.delete(id);
    System.out.println("From Restaurant Service : MenuOption has been deleted");
  }
}
