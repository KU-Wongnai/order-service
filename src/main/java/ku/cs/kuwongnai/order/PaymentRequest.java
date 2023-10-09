package ku.cs.kuwongnai.order;

import java.util.UUID;

import io.micrometer.common.lang.Nullable;
import lombok.Data;

@Data
public class PaymentRequest {

  /**
   * The token id generate from Omise.js
   */
  @Nullable
  private String tokenId;

  /**
   * The saved card id
   */
  @Nullable
  private String cardId;

  /**
   * Save the card for future use
   */
  private Boolean save = false;

  /**
   * The amount to charge
   */
  @Nullable
  private Double amount;

  private String deliveryAddress;

  /**
   * The bill id
   */
  @Nullable
  private UUID billId;
}
