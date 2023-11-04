package ku.cs.kuwongnai.notification;
import com.rabbitmq.client.*;

public class RabbitMQPublisher {
    private Connection connection;
    private Channel channel;

    public RabbitMQPublisher() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setPort(5672);
        factory.setUsername("user");
        factory.setPassword("password");
        factory.setVirtualHost("/");

        connection = factory.newConnection();
        channel = connection.createChannel();
    }

    public void declareExchange(String exchangeName, String exchangeType) throws Exception {
        channel.exchangeDeclare(exchangeName, exchangeType, true);
    }

    public void publish(String message, String exchange, String routingKey) throws Exception {
        channel.basicPublish(exchange, routingKey, null, message.getBytes());
    }

    public void close() throws Exception {
        channel.close();
        connection.close();
    }
}
