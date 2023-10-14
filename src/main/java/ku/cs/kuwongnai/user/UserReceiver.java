package ku.cs.kuwongnai.user;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserReceiver {

  @Autowired
  private UserService userService;

  @RabbitListener(queues = "#{userCreatedQueue.name}")
  public void handleUserCreatedMessage(User user) {
    // Handle user created
    userService.create(user);
    System.out.println("From User Service : User has been created");

  }

  @RabbitListener(queues = "#{userUpdatedQueue.name}")
  public void handleUserUpdatedMessage(User user) {
    // Handle user updated message
    userService.update(user);
    System.out.println("From User Service : User has been updated");
  }

}
