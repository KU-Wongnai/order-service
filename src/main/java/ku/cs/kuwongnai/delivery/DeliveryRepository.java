package ku.cs.kuwongnai.delivery;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryRepository extends JpaRepository<Delivery, UUID> {

  public List<Delivery> findByStatus(DeliveryStatus deliveryStatus);

  public List<Delivery> findByRiderId(Long riderId);

  public List<Delivery> findByRiderIdAndStatus(Long riderId, DeliveryStatus deliveryStatus);
}
