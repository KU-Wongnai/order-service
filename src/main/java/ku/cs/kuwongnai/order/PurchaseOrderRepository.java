package ku.cs.kuwongnai.order;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, UUID> {

    List<PurchaseOrder> findByRestaurantId(Long restaurantID);

    List<PurchaseOrder> findByRestaurantIdAndStatus(Long restaurantID, OrderStatus status);

    List<PurchaseOrder> findByUserId(Long userId);

    List<PurchaseOrder> findByUserIdAndStatus(Long userId, OrderStatus status);
}
