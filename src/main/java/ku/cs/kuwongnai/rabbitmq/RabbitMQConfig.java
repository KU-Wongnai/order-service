package ku.cs.kuwongnai.rabbitmq;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;

@Configuration
public class RabbitMQConfig {

  private final String USER_CREATED_QUEUE = "order.user.created.queue";
  private final String USER_UPDATED_QUEUE = "order.user.updated.queue";
  private final String USER_CREATED_ROUTING_KEY = "user.created";
  private final String USER_UPDATED_ROUTING_KEY = "user.updated";
  private final String USER_TOPIC_EXCHANGE = "events.user";

  private final String RESTAURANT_TOPIC_EXCHANGE = "events.restaurant";
  private final String RESTAURANT_CREATED_QUEUE = "order.restaurant.created.queue";
  private final String RESTAURANT_UPDATED_QUEUE = "order.restaurant.updated.queue";
  private final String RESTAURANT_DELETED_QUEUE = "order.restaurant.deleted.queue";
  private final String RESTAURANT_CREATED_ROUTING_KEY = "restaurant.created";
  private final String RESTAURANT_UPDATED_ROUTING_KEY = "restaurant.updated";
  private final String RESTAURANT_DELETED_ROUTING_KEY = "restaurant.deleted";

  private final String MENU_TOPIC_EXCHANGE = "events.menu";
  private final String MENU_CREATED_QUEUE = "menu.created.queue";
  private final String MENU_UPDATED_QUEUE = "menu.updated.queue";
  private final String MENU_DELETED_QUEUE = "menu.deleted.queue";
  private final String MENU_CREATED_ROUTING_KEY = "menu.created";
  private final String MENU_UPDATED_ROUTING_KEY = "menu.updated";
  private final String MENU_DELETED_ROUTING_KEY = "menu.deleted";

  private final String MENU_OPTION_TOPIC_EXCHANGE = "events.menuOption";
  private final String MENU_OPTION_CREATED_QUEUE = "menuOption.created.queue";
  private final String MENU_OPTION_UPDATED_QUEUE = "menuOption.updated.queue";
  private final String MENU_OPTION_DELETED_QUEUE = "menuOption.deleted.queue";
  private final String MENU_OPTION_CREATED_ROUTING_KEY = "menuOption.created";
  private final String MENU_OPTION_UPDATED_ROUTING_KEY = "menuOption.updated";
  private final String MENU_OPTION_DELETED_ROUTING_KEY = "menuOption.deleted";

  @Bean
  public TopicExchange userTopic() {
    return new TopicExchange(USER_TOPIC_EXCHANGE);
  }

  @Bean
  public TopicExchange restaurantTopic() {
    return new TopicExchange(RESTAURANT_TOPIC_EXCHANGE);
  }

  @Bean
  public TopicExchange menuTopic() {
    return new TopicExchange(MENU_TOPIC_EXCHANGE);
  }

  @Bean
  public TopicExchange menuOptionTopic() {
    return new TopicExchange(MENU_OPTION_TOPIC_EXCHANGE);
  }

  @Bean
  public Queue userCreatedQueue() {
    return new Queue(USER_CREATED_QUEUE);
  }

  @Bean
  public Queue userUpdatedQueue() {
    return new Queue(USER_UPDATED_QUEUE);
  }

  @Bean
  public Queue restaurantCreatedQueue() {
    return new Queue(RESTAURANT_CREATED_QUEUE);
  }

  @Bean
  public Queue restaurantUpdatedQueue() {
    return new Queue(RESTAURANT_UPDATED_QUEUE);
  }

  @Bean
  public Queue restaurantDeletedQueue() {
    return new Queue(RESTAURANT_DELETED_QUEUE);
  }

  @Bean
  public Queue menuCreatedQueue() {
    return new Queue(MENU_CREATED_QUEUE);
  }

  @Bean
  public Queue menuUpdatedQueue() {
    return new Queue(MENU_UPDATED_QUEUE);
  }

  @Bean
  public Queue menuDeletedQueue() {
    return new Queue(MENU_DELETED_QUEUE);
  }

  @Bean
  public Queue menuOptionCreatedQueue() {
    return new Queue(MENU_OPTION_CREATED_QUEUE);
  }

  @Bean
  public Queue menuOptionUpdatedQueue() {
    return new Queue(MENU_OPTION_UPDATED_QUEUE);
  }

  @Bean
  public Queue menuOptionDeletedQueue() {
    return new Queue(MENU_OPTION_DELETED_QUEUE);
  }

  @Bean
  public Binding userCreatedBinding(Queue userCreatedQueue, TopicExchange userTopic) {
    return BindingBuilder.bind(userCreatedQueue).to(userTopic).with(USER_CREATED_ROUTING_KEY);
  }

  @Bean
  public Binding userUpdatedBinding(Queue userUpdatedQueue, TopicExchange userTopic) {
    return BindingBuilder.bind(userUpdatedQueue).to(userTopic).with(USER_UPDATED_ROUTING_KEY);
  }

  @Bean
  public Binding restaurantCreatedBinding(Queue restaurantCreatedQueue, TopicExchange restaurantTopic) {
    return BindingBuilder.bind(restaurantCreatedQueue).to(restaurantTopic).with(RESTAURANT_CREATED_ROUTING_KEY);
  }

  @Bean
  public Binding restaurantUpdatedBinding(Queue restaurantUpdatedQueue, TopicExchange restaurantTopic) {
    return BindingBuilder.bind(restaurantUpdatedQueue).to(restaurantTopic).with(RESTAURANT_UPDATED_ROUTING_KEY);
  }

  @Bean
  public Binding restaurantDeletedBinding(Queue restaurantDeletedQueue, TopicExchange restaurantTopic) {
    return BindingBuilder.bind(restaurantDeletedQueue).to(restaurantTopic).with(RESTAURANT_DELETED_ROUTING_KEY);
  }

  @Bean
  public Binding menuCreatedBinding(Queue menuCreatedQueue, TopicExchange menuTopic) {
    return BindingBuilder.bind(menuCreatedQueue).to(menuTopic).with(MENU_CREATED_ROUTING_KEY);
  }

  @Bean
  public Binding menuUpdatedBinding(Queue menuUpdatedQueue, TopicExchange menuTopic) {
    return BindingBuilder.bind(menuUpdatedQueue).to(menuTopic).with(MENU_UPDATED_ROUTING_KEY);
  }

  @Bean
  public Binding menuDeletedBinding(Queue menuDeletedQueue, TopicExchange menuTopic) {
    return BindingBuilder.bind(menuDeletedQueue).to(menuTopic).with(MENU_DELETED_ROUTING_KEY);
  }

  @Bean
  public Binding menuOptionCreatedBinding(Queue menuOptionCreatedQueue, TopicExchange menuOptionTopic) {
    return BindingBuilder.bind(menuOptionCreatedQueue).to(menuOptionTopic).with(MENU_OPTION_CREATED_ROUTING_KEY);
  }

  @Bean
  public Binding menuOptionUpdatedBinding(Queue menuOptionUpdatedQueue, TopicExchange menuOptionTopic) {
    return BindingBuilder.bind(menuOptionUpdatedQueue).to(menuOptionTopic).with(MENU_OPTION_UPDATED_ROUTING_KEY);
  }

  @Bean
  public Binding menuOptionDeletedBinding(Queue menuOptionDeletedQueue, TopicExchange menuOptionTopic) {
    return BindingBuilder.bind(menuOptionDeletedQueue).to(menuOptionTopic).with(MENU_OPTION_DELETED_ROUTING_KEY);
  }

  @Bean
  public MessageConverter converter() {
    return new Jackson2JsonMessageConverter();
  }

  @Bean
  public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory) {
    RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
    rabbitTemplate.setMessageConverter(converter());
    return rabbitTemplate;
  }

}
