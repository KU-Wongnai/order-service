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

  private final String USER_CREATED_QUEUE = "user.created.queue";
  private final String USER_UPDATED_QUEUE = "user.updated.queue";
  private final String USER_CREATED_ROUTING_KEY = "user.created";
  private final String USER_UPDATED_ROUTING_KEY = "user.updated";
  private final String USER_TOPIC_EXCHANGE = "events.user";

  private final String RESTAURANT_TOPIC_EXCHANGE = "events.restaurant";
  private final String RESTAURANT_CREATED_QUEUE = "restaurant.created.queue";
  private final String RESTAURANT_UPDATED_QUEUE = "restaurant.updated.queue";
  private final String RESTAURANT_DELETED_QUEUE = "restaurant.deleted.queue";
  private final String RESTAURANT_CREATED_ROUTING_KEY = "restaurant.created";
  private final String RESTAURANT_UPDATED_ROUTING_KEY = "restaurant.updated";
  private final String RESTAURANT_DELETED_ROUTING_KEY = "restaurant.deleted";

  @Bean
  public TopicExchange userTopic() {
    return new TopicExchange(USER_TOPIC_EXCHANGE);
  }

  @Bean
  public TopicExchange restaurantTopic() {
    return new TopicExchange(RESTAURANT_TOPIC_EXCHANGE);
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
