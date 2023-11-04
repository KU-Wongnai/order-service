package ku.cs.kuwongnai.notification;

import org.springframework.stereotype.Component;

@Component
public class NotificationSender {

    RabbitMQPublisher publisher;

    String exchangeName = "events.notification"; 
    String routingKey = "email.welcome"; // specific to routing key at receiver

    // used in this service
    // user part

    public void sendInAppUserDeliveryOrder(String userId) throws Exception{
        String type = "noti.UserDeliveryOrder";
        
        String message = "{\"to\":\"" + userId + "\", \"type\":\"" + type + "\"}";
        publisher = new RabbitMQPublisher();
        publisher.declareExchange(exchangeName, "topic");
        publisher.publish(message, exchangeName, routingKey);
        publisher.close();
    }


    public void sendInAppUserSuccessPayment(String userId) throws Exception{
        String type = "noti.UserSuccessPayment";
        
        // Json
        String message = "{\"to\":\"" + userId + "\", \"type\":\"" + type + "\"}";
        publisher = new RabbitMQPublisher();
        publisher.declareExchange(exchangeName, "topic");
        publisher.publish(message, exchangeName, routingKey);
        publisher.close();
    }

    public void sendInAppUserDeliveryFinished(String userId) throws Exception{
        String type = "noti.UserDeliveryFinished";
        
        String message = "{\"to\":\"" + userId + "\", \"type\":\"" + type + "\"}";
        publisher = new RabbitMQPublisher();
        publisher.declareExchange(exchangeName, "topic");
        publisher.publish(message, exchangeName, routingKey);
        publisher.close();
    }

    public void sendInAppUserOrderCanceled(String userId) throws Exception{
        String type = "noti.UserOrderCanceled";
        
        String message = "{\"to\":\"" + userId + "\", \"type\":\"" + type + "\"}";
        publisher = new RabbitMQPublisher();
        publisher.declareExchange(exchangeName, "topic");
        publisher.publish(message, exchangeName, routingKey);
        publisher.close();
    }


    // rider part
    public void sendInAppRiderOrderFinished(String userId) throws Exception{
        String type = "noti.RiderOrderFinished";
        
        String message = "{\"to\":\"" + userId + "\", \"type\":\"" + type + "\"}";
        publisher = new RabbitMQPublisher();
        publisher.declareExchange(exchangeName, "topic");
        publisher.publish(message, exchangeName, routingKey);
        publisher.close();
    }

    
    public void sendInAppRiderNewOrder(String userId) throws Exception{
        String type = "noti.RiderNewOrder";
        
        String message = "{\"to\":\"" + userId + "\", \"type\":\"" + type + "\"}";
        publisher = new RabbitMQPublisher();
        publisher.declareExchange(exchangeName, "topic");
        publisher.publish(message, exchangeName, routingKey);
        publisher.close();
    }

    public void sendInAppRiderOrderCanceled(String userId) throws Exception{
        String type = "noti.RiderOrderCanceled";
        
        String message = "{\"to\":\"" + userId + "\", \"type\":\"" + type + "\"}";
        publisher = new RabbitMQPublisher();
        publisher.declareExchange(exchangeName, "topic");
        publisher.publish(message, exchangeName, routingKey);
        publisher.close();
    }
}
