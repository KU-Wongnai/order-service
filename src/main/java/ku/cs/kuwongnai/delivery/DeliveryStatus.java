package ku.cs.kuwongnai.delivery;

public enum DeliveryStatus {
  /**
   * The delivery is pending and has not yet been assigned to a rider.
   */
  PENDING,
  /**
   * The delivery has been assigned to a rider and is in progress.
   */
  ASSIGNED,
  /**
   * The delivery has been successfully completed and the order has been
   * delivered.
   */
  DELIVERED
}
