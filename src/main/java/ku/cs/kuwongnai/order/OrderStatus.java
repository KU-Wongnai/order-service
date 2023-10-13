package ku.cs.kuwongnai.order;

public enum OrderStatus {
  /**
   * Waiting for the user to finish purchasing.
   */
  PENDING,
  /**
   * The order has been received by the restaurant.
   */
  RECEIVED,

  /**
   * The restaurant is preparing the order.
   */
  PREPARING,

  /**
   * The order is prepared and ready for delivery.
   */
  COMPLETED,
}
