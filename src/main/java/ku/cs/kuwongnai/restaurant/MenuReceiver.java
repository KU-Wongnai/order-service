package ku.cs.kuwongnai.restaurant;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MenuReceiver {
  @Autowired
  private MenuService menuService;

  @RabbitListener(queues = "#{menuCreatedQueue.name}")
  public void handleMenuCreatedMessage(MenuRequest menu) {
    // Handle restaurant created
    System.out.println(menu);
    menuService.createMenu(menu);
    System.out.println("From Restaurant Service : Menu has been created");
  }

  @RabbitListener(queues = "#{menuUpdatedQueue.name}")
  public void handleMenuUpdatedMessage(MenuRequest menu) {
    // Handle restaurant updated message
    menuService.updateMenu(menu);
    System.out.println("From Restaurant Service : Menu has been updated");
  }

  @RabbitListener(queues = "#{menuDeletedQueue.name}")
  public void handleMenuDeletedMessage(Long id) {
    // Handle restaurant deleted message
    menuService.deleteMenu(id);
    System.out.println("From Restaurant Service : Menu has been deleted");
  }
}
