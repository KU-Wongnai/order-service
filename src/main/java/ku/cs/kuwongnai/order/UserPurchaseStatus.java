package ku.cs.kuwongnai.order;

public enum UserPurchaseStatus {
  /**
   * This status indicate that the purchase is in
   * the process of being verified or confirmed but
   * is not yet complete.
   */
  PROCESSING,

  /**
   * To differentiate between purchases that have
   * been fully processed, you can use a
   * "Completed" status.
   */
  COMPLETED,

  /**
   * This status can represent purchases that
   * were canceled by the user or the system
   * before payment was completed.
   */
  CANCELLED,

  /**
   * For situations where there's a time limit for
   * completing the payment, you could have a status
   * for purchases that have exceeded that limit
   * without being paid.
   */
  EXPIRED,
}
